//package com.queen.configuration;
//
//import io.r2dbc.spi.ConnectionFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.domain.ReactiveAuditorAware;
//import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
//import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
//import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
//import org.springframework.security.core.context.ReactiveSecurityContextHolder;
//import org.springframework.security.core.context.SecurityContext;
//
//import java.util.UUID;
//
//@Configuration
//@EnableR2dbcRepositories(basePackages = "com.queen.infrastructure.persistence")
//@EnableR2dbcAuditing(auditorAwareRef = "reactiveAuditorAware")
//public class InfrastructureConfiguration {
//	@Bean
//	ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
//		ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
//		initializer.setConnectionFactory(connectionFactory);
//
//		return initializer;
//	}
//
//	@Bean
//	public ReactiveAuditorAware<UUID> reactiveAuditorAware() {
//		return () -> ReactiveSecurityContextHolder.getContext().map(SecurityContext::getAuthentication)
//				.map(authentication -> ((FellaJwtAuthenticationToken) authentication).getUserId());
//	}
//}
