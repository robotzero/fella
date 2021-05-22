package com.queen.application.service;

import com.queen.adapters.persistance.FieldTypeMapper;
import com.queen.adapters.persistance.FieldsMapper;
import com.queen.adapters.persistance.MonitorTypeMapper;
import com.queen.adapters.web.dto.FieldsDTO;
import com.queen.adapters.web.dto.MonitorTypeDTO;
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
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

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
	public Flux<MonitorType> load(final String userId, final Pageable pageable) {
		final var allMonitorTypes = this.loadAllMonitorTypes.loadAllMonitorTypes(userId, pageable);
		return allMonitorTypes.flatMap((monitorType -> {
			final var monitorTypeDomain = monitorTypeMapper.mapToDomain(monitorType);
			return loadAllFieldTypesPort.loadFieldTypesByMonitorType(monitorType.getId()).collectList().zipWith(Mono.just(monitorTypeDomain), (fieldTypes, monitorTypeD) -> {
				return new MonitorType(monitorTypeD.id(), monitorTypeD.name(), fieldTypes.stream().map(fieldTypeMapper::toDomain).toList());
			});
		}));
	}

	@Override
	public void publishCreateUserTemplateEvent(List<MonitorTypeDTO> monitorTypeDTOs) {
		String monitorTypeIdPeriod = UUID.randomUUID().toString();
		String monitorTypeIdStomach = UUID.randomUUID().toString();

		FieldsDTO fieldType1 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdPeriod, "1");
		FieldsDTO fieldType2 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdPeriod, "2");
		FieldsDTO fieldType3 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdPeriod, "3");
		FieldsDTO fieldType4 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdPeriod, "4");
		FieldsDTO fieldType5 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdPeriod, "5");

		FieldsDTO fieldType6 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdStomach, "1");
		FieldsDTO fieldType7 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdStomach, "2");
		FieldsDTO fieldType8 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdStomach, "4");
		FieldsDTO fieldType9 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdStomach, "5");

		MonitorTypeDTO monitorTypePeriod = new MonitorTypeDTO(monitorTypeIdPeriod, "Period", List.of(fieldType1, fieldType2, fieldType3, fieldType4, fieldType5), List.of());
		MonitorTypeDTO monitorTypeStomach = new MonitorTypeDTO(monitorTypeIdStomach, "Stomach", List.of(fieldType6, fieldType7, fieldType8, fieldType9), List.of());

		this.applicationEventPublisher.publishEvent(new CreateUserTemplateCommand(new CreateUserTemplateDTO(List.of(monitorTypePeriod, monitorTypeStomach))));
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
