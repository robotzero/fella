package com.queen.application.service;

import com.queen.adapters.persistance.FieldTypeMapper;
import com.queen.adapters.persistance.FieldsMapper;
import com.queen.adapters.persistance.MonitorTypeMapper;
import com.queen.application.ports.in.AllMonitorTypesQuery;
import com.queen.application.ports.in.CreateMonitorTypeCommand;
import com.queen.application.ports.in.CreateMonitorTypeUseCase;
import com.queen.application.ports.out.CreateFieldsPort;
import com.queen.application.ports.out.CreateManyMonitorTypesPort;
import com.queen.application.ports.out.LoadMonitorTypesPort;
import com.queen.application.ports.out.LoadFieldTypesPort;
import com.queen.application.service.exception.MonitorTypeException;
import com.queen.domain.monitortype.MonitorType;
import com.queen.domain.validation.ItemExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

public class MonitorTypeService implements AllMonitorTypesQuery, CreateMonitorTypeUseCase {
	private final LoadMonitorTypesPort loadMonitorTypesPort;
	private final CreateManyMonitorTypesPort createManyMonitorTypesPort;
	private final CreateFieldsPort createFieldsPort;
	private final LoadFieldTypesPort loadAllFieldTypesPort;
	private final MonitorTypeMapper monitorTypeMapper;
	private final FieldsMapper fieldsMapper;
	private final FieldTypeMapper fieldTypeMapper;
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public MonitorTypeService(
			final LoadMonitorTypesPort loadMonitorTypes,
			final CreateManyMonitorTypesPort createManyMonitorTypesPort,
			final CreateFieldsPort createFieldsPort,
			final LoadFieldTypesPort loadAllFieldTypesPort,
			final MonitorTypeMapper monitorTypeMapper,
			final FieldsMapper fieldsMapper,
			final FieldTypeMapper fieldTypeMapper
	) {
		this.loadMonitorTypesPort = loadMonitorTypes;
		this.createManyMonitorTypesPort = createManyMonitorTypesPort;
		this.createFieldsPort = createFieldsPort;
		this.monitorTypeMapper = monitorTypeMapper;
		this.loadAllFieldTypesPort = loadAllFieldTypesPort;
		this.fieldsMapper = fieldsMapper;
		this.fieldTypeMapper = fieldTypeMapper;
	}

	@Override
	public Flux<MonitorType> load(final String userId, final Pageable pageable) {
		final var allMonitorTypes = this.loadMonitorTypesPort.loadAllMonitorTypes(userId, pageable);
		return allMonitorTypes.flatMap((monitorType -> {
			final var monitorTypeDomain = monitorTypeMapper.mapToDomain(monitorType);
			return loadAllFieldTypesPort.loadFieldTypesByMonitorType(monitorType.getId()).collectList().zipWith(Mono.just(monitorTypeDomain), (fieldTypes, monitorTypeD) -> {
				return new MonitorType(monitorTypeD.id(), monitorTypeD.name(), fieldTypes.stream().map(fieldTypeMapper::toDomain).toList());
			});
		}));
	}

	@Override
	public Flux<MonitorType> createManyMonitorTypes(CreateMonitorTypeCommand createMonitorTypeCommand) {
		final var monitorTypesFlux = this.loadMonitorTypesPort.findByUserIdAndNames(createMonitorTypeCommand.monitorTypeDTOs().stream()
				.map(dto -> dto.name())
				.collect(Collectors.toList()), createMonitorTypeCommand.userId()
		).flatMap(__ -> Flux.error(new ItemExistsException("Monitor Type with that name already exist."))
		).switchIfEmpty(Flux.defer(() -> {
			return createManyMonitorTypesPort.createMonitorTypes(createMonitorTypeCommand.monitorTypeDTOs()
							.stream()
							.map(monitorTypeDTO -> {
								return monitorTypeMapper.mapToPersistence(monitorTypeDTO).setAsNew();
							}).toList())
					.doOnError(error -> {
						log.error("Failed to save many monitor types", error);
						throw new MonitorTypeException("Failed to save monitor types", error);
					})
					.doOnComplete(() -> log.info("Done saving monitor types")
			);
		})).cast(com.queen.infrastructure.persitence.MonitorType.class);

		final var fieldsFlux = createFieldsPort.createFields(createMonitorTypeCommand.monitorTypeDTOs().stream().flatMap(monitorTypeDTO -> {
			return monitorTypeDTO.fieldDTOs().stream();
		}).map(fieldsDTO -> fieldsMapper.mapToPersistence(fieldsDTO).setAsNew()).toList())
				.doOnError(error -> {
					log.error("Failed to save many field types", error);
					throw new MonitorTypeException("Failed to save fields", error);
				})
				.doOnComplete(() -> log.info("Done saving field types"));

		return fieldsFlux.thenMany(monitorTypesFlux).map(monitorTypePersistance -> {
			return monitorTypeMapper.mapToDomain(monitorTypePersistance);
		});
	}
}
