package com.queen.configuration;

import com.queen.infrastructure.auth.repository.PeriodReadConverter;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;

import java.util.List;

@Configuration
public class DatabaseConfiguration extends AbstractR2dbcConfiguration {

	//@@TODO - Change the connection string to the correct one per profile or environment
	@Override
	public ConnectionFactory connectionFactory() {
		return ConnectionFactories.get("r2dbc:postgresql://agnieszka@192.168.0.64:5432/fella");
	}

	@Override
	protected List<Object> getCustomConverters() {
		return List.of(new PeriodReadConverter());
	}
}
