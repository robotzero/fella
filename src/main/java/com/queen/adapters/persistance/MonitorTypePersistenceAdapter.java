package com.queen.adapters.persistance;

import com.queen.application.ports.out.CreateManyMonitorTypesPort;
import com.queen.application.ports.out.CreateMonitorTypePort;
import com.queen.application.ports.out.LoadAllMonitorTypesPort;
import com.queen.infrastructure.persitence.MonitorType;
import com.queen.infrastructure.persitence.MonitorTypeRepository;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public class MonitorTypePersistenceAdapter implements LoadAllMonitorTypesPort, CreateMonitorTypePort, CreateManyMonitorTypesPort {
	private final MonitorTypeRepository monitorTypeRepository;

	public MonitorTypePersistenceAdapter(final MonitorTypeRepository monitorTypeRepository) {
		this.monitorTypeRepository = monitorTypeRepository;
	}

	@Override
	public Flux<MonitorType> loadAllMonitorTypes(final String userId, final Pageable pageable) {
		return this.monitorTypeRepository.findByUserId(userId, pageable);
	}

	@Override
	//@TODO ask how to get rid of this subscribe from internal
	public void createMonitorType(final @NotNull MonitorType monitorType) {
		final var disposable = this.monitorTypeRepository.save(monitorType).subscribe();
		Optional.of(disposable.isDisposed()).ifPresentOrElse(d -> {}, disposable::dispose);
	}

	@Override
	public void createMonitorTypes(final @NotNull List<MonitorType> monitorTypes) {
		final var disposable = this.monitorTypeRepository.saveAll(monitorTypes).subscribe();
		Optional.of(disposable.isDisposed()).ifPresentOrElse(d -> {}, disposable::dispose);
	}
}
