package com.queen.application.service;

import com.queen.adapters.persistance.FieldTypeMapper;
import com.queen.adapters.persistance.FieldsMapper;
import com.queen.adapters.persistance.MonitorTypeMapper;
import com.queen.adapters.web.MonitorTypeDTO;
import com.queen.application.ports.in.AllMonitorTypesQuery;
import com.queen.application.ports.in.CreateMonitorTypeCommand;
import com.queen.application.ports.in.CreateMonitorTypeUseCase;
import com.queen.application.ports.in.CreateUserTemplateCommand;
import com.queen.application.ports.in.CreateUserTemplateDTO;
import com.queen.application.ports.in.CreateUserTemplateEvent;
import com.queen.application.ports.out.CreateFieldsPort;
import com.queen.application.ports.out.CreateManyMonitorTypesPort;
import com.queen.application.ports.out.CreateMonitorTypePort;
import com.queen.application.ports.out.LoadFieldTypesPort;
import com.queen.application.ports.out.LoadAllMonitorTypesPort;
import com.queen.domain.monitortype.MonitorType;
import org.springframework.context.ApplicationEventPublisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.util.List;

public class MonitorTypeService implements AllMonitorTypesQuery, CreateUserTemplateEvent, CreateMonitorTypeUseCase {
	private final LoadAllMonitorTypesPort loadAllMonitorTypes;
	private final ApplicationEventPublisher applicationEventPublisher;
	private final CreateMonitorTypePort createMonitorTypePort;
	private final CreateManyMonitorTypesPort createManyMonitorTypesPort;
	private final CreateFieldsPort createFieldsPort;
	private final LoadFieldTypesPort loadAllFieldTypesPort;
	private final MonitorTypeMapper monitorTypeMapper;
	private final FieldsMapper fieldsMapper;
	private final FieldTypeMapper fieldTypeMapper;

	public MonitorTypeService(
			final LoadAllMonitorTypesPort loadAllMonitorTypes,
			final ApplicationEventPublisher applicationEventPublisher,
			final CreateMonitorTypePort createMonitorTypePort,
			final CreateManyMonitorTypesPort createManyMonitorTypesPort,
			final CreateFieldsPort createFieldsPort,
			final LoadFieldTypesPort loadAllFieldTypesPort,
			final MonitorTypeMapper monitorTypeMapper,
			final FieldsMapper fieldsMapper,
			final FieldTypeMapper fieldTypeMapper
	) {
		this.loadAllMonitorTypes = loadAllMonitorTypes;
		this.applicationEventPublisher = applicationEventPublisher;
		this.createMonitorTypePort = createMonitorTypePort;
		this.createManyMonitorTypesPort = createManyMonitorTypesPort;
		this.createFieldsPort = createFieldsPort;
		this.monitorTypeMapper = monitorTypeMapper;
		this.loadAllFieldTypesPort = loadAllFieldTypesPort;
		this.fieldsMapper = fieldsMapper;
		this.fieldTypeMapper = fieldTypeMapper;
	}

	@Override
	public Flux<MonitorType> load(final @NotNull String userId) {
		final var allMonitorTypes = this.loadAllMonitorTypes.loadAllMonitorTypes(userId);
		return allMonitorTypes.flatMap((monitorType -> {
			final var monitorTypeDomain = monitorTypeMapper.mapToDomain(monitorType);
			return loadAllFieldTypesPort.loadFieldTypesByMonitorType(monitorType.getId()).collectList().zipWith(Mono.just(monitorTypeDomain), (fieldTypes, monitorTypeD) -> {
				return new MonitorType(monitorTypeD.id(), monitorTypeD.name(), monitorTypeD.userId(), fieldTypes.stream().map(fieldTypeMapper::toDomain).toList());
			});
		}));
	}

	@Override
	public void publishCreateUserTemplateEvent(List<MonitorTypeDTO> monitorTypeDTOs) {
		this.applicationEventPublisher.publishEvent(new CreateUserTemplateCommand(new CreateUserTemplateDTO(monitorTypeDTOs)));
	}

	@Override
	public void createManyMonitorTypes(CreateMonitorTypeCommand createMonitorTypeCommand) {
		createManyMonitorTypesPort.createMonitorTypes(createMonitorTypeCommand.monitorTypeDTOs()
				.stream()
				.map(monitorTypeDTO -> monitorTypeMapper.mapToPersistence(monitorTypeDTO).setAsNew())
				.toList());

		createFieldsPort.createFields(createMonitorTypeCommand.monitorTypeDTOs().stream().flatMap(monitorTypeDTO -> {
			return monitorTypeDTO.fieldDTOs().stream();
		}).map(fieldsDTO -> fieldsMapper.mapToPersistence(fieldsDTO).setAsNew()).toList());
	}
}
