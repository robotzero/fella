package com.queen.application.service;

import com.queen.adapters.web.dto.PeriodDTO;
import com.queen.domain.Period;
import com.queen.domain.PeriodMapperPort;
import com.queen.domain.PeriodPersistencePort;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

public class PeriodService {
	private final PeriodPersistencePort periodPersistencePort;
	private final PeriodMapperPort periodMapper;

	public PeriodService(final PeriodPersistencePort periodPersistencePort, final PeriodMapperPort periodMapper) {
		this.periodPersistencePort = periodPersistencePort;
		this.periodMapper = periodMapper;
	}

	@Transactional
	public Mono<PeriodDTO> createPeriod(final Period period) {
		return periodPersistencePort.createPeriod(periodMapper.mapToPersistence(period)).map(periodMapper::mapToDTO);
	}
}
