package com.queen.application.service;

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
import com.queen.application.ports.out.LoadAllMonitorTypesPort;
import com.queen.domain.monitortype.MonitorType;
import org.springframework.context.ApplicationEventPublisher;
import reactor.core.publisher.Flux;

import javax.validation.constraints.NotNull;
import java.util.List;

public class MonitorTypeService implements AllMonitorTypesQuery, CreateUserTemplateEvent, CreateMonitorTypeUseCase {
	private final LoadAllMonitorTypesPort loadAllMonitorTypes;
	private final ApplicationEventPublisher applicationEventPublisher;
	private final CreateMonitorTypePort createMonitorTypePort;
	private final CreateManyMonitorTypesPort createManyMonitorTypesPort;
	private final CreateFieldsPort createFieldsPort;
	private final MonitorTypeMapper monitorTypeMapper;
	private final FieldsMapper fieldsMapper;

	public MonitorTypeService(
			final LoadAllMonitorTypesPort loadAllMonitorTypes,
			final ApplicationEventPublisher applicationEventPublisher,
			final CreateMonitorTypePort createMonitorTypePort,
			final CreateManyMonitorTypesPort createManyMonitorTypesPort,
			final CreateFieldsPort createFieldsPort,
			final MonitorTypeMapper monitorTypeMapper,
			final FieldsMapper fieldsMapper
	) {
		this.loadAllMonitorTypes = loadAllMonitorTypes;
		this.applicationEventPublisher = applicationEventPublisher;
		this.createMonitorTypePort = createMonitorTypePort;
		this.createManyMonitorTypesPort = createManyMonitorTypesPort;
		this.createFieldsPort = createFieldsPort;
		this.monitorTypeMapper = monitorTypeMapper;
		this.fieldsMapper = fieldsMapper;
	}

	@Override
	public Flux<MonitorType> load(final @NotNull String userId) {
		final var allMonitorTypes = this.loadAllMonitorTypes.loadAllMonitorTypes(userId);
		return allMonitorTypes.map(monitorType -> {
			return monitorTypeMapper.mapToDomain(monitorType);
		});
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
			return monitorTypeDTO.fieldsDTOs().stream();
		}).map(fieldsDTO -> fieldsMapper.mapToPersistence(fieldsDTO).setAsNew()).toList());
	}
}
