package com.queen.configuration;

import com.queen.adapters.persistance.LoadMonitorTypes;
import com.queen.adapters.persistance.MonitorTypeMapper;
import com.queen.adapters.web.MonitorTypeToDTO;
import com.queen.application.ports.out.LoadAllMonitorTypes;
import com.queen.application.service.MonitorTypeService;
import com.queen.infrastructure.persitence.MonitorTypeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FellaConfiguration {
	@Bean
	MonitorTypeService monitorTypeService(final MonitorTypeMapper monitorTypeMapper, final LoadAllMonitorTypes loadAllMonitorTypes) {
		return new MonitorTypeService(loadAllMonitorTypes, monitorTypeMapper);
	}

	@Bean
	MonitorTypeMapper monitorTypeMapper() {
		return new MonitorTypeMapper();
	}

	@Bean
	LoadAllMonitorTypes loadAllMonitorTypes(MonitorTypeRepository monitorTypeRepository) {
		return new LoadMonitorTypes(monitorTypeRepository);
	}

	@Bean
	MonitorTypeToDTO monitorTypeToDTO() {
		return new MonitorTypeToDTO();
	}
}
