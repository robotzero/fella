package com.queen.application.service;

import com.queen.adapters.persistance.FieldTypeMapper;
import com.queen.adapters.persistance.FieldsMapper;
import com.queen.adapters.persistance.MonitorTypeMapper;
import com.queen.application.ports.in.AllMonitorTypesQuery;
import com.queen.application.ports.in.CreateMonitorTypeCommand;
import com.queen.application.ports.in.CreateMonitorTypeUseCase;
import com.queen.application.ports.out.CreateFieldsPort;
import com.queen.application.ports.out.CreateManyMonitorTypesPort;
import com.queen.application.ports.out.LoadFieldTypesPort;
import com.queen.application.ports.out.LoadMonitorTypesPort;
import com.queen.application.service.exception.MonitorTypeException;
import com.queen.domain.monitortype.MonitorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

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
	@Transactional
	public Flux<MonitorType> createManyMonitorTypes(CreateMonitorTypeCommand createMonitorTypeCommand) {
		final var monitorTypesToCreate = createMonitorTypeCommand.monitorTypeDTOs()
				.stream()
				.map(monitorTypeDTO -> {
					return monitorTypeMapper.mapToPersistence(monitorTypeDTO).setAsNew();
				}).toList();


		final var monitorTypesFlux = createManyMonitorTypesPort.createMonitorTypes(monitorTypesToCreate)
				.doOnError(error -> {
					log.error("Failed to save many monitor types", error);
					switch (error) {
						case DataIntegrityViolationException e -> throw new MonitorTypeException("Failed to save monitor types, name already exist", e);
						default -> throw new MonitorTypeException("Failed to save monitor types", error);
					}
				})
				.doOnComplete(() -> log.info("Done saving monitor types")
		).cast(com.queen.infrastructure.persitence.MonitorType.class);

		final var fieldsByMonitorId = createMonitorTypeCommand.monitorTypeDTOs()
				.stream()
				.flatMap(monitorTypeDTO -> monitorTypeDTO.fieldDTOs().stream())
				.collect(
						groupingBy(
								fieldDTO -> fieldDTO.monitorTypeId(),
								HashMap::new,
								mapping(fieldDTO -> fieldTypeMapper.toDomain(fieldsMapper.mapToDomain(fieldDTO)) , toList())
						)
				);

		final var fieldsToCreate = createMonitorTypeCommand.monitorTypeDTOs().stream().flatMap(monitorTypeDTO -> {
			return monitorTypeDTO.fieldDTOs().stream();
		}).map(fieldsDTO -> fieldsMapper.mapToPersistence(fieldsDTO).setAsNew()).toList();

		final var fieldsFlux = createFieldsPort.createFields(fieldsToCreate)
				.doOnError(error -> {
					log.error("Failed to save many field types", error);
					throw new MonitorTypeException("Failed to save fields", error);
				})
				.doOnComplete(() -> log.info("Done saving field types"));

		return fieldsFlux.thenMany(monitorTypesFlux).map(monitorTypePersistance -> {
			return monitorTypeMapper.mapToDomain(monitorTypePersistance, fieldsByMonitorId.get(monitorTypePersistance.getId()));
		});
	}
}
