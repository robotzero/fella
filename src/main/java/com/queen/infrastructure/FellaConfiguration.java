package com.queen.infrastructure;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
@EnableR2dbcRepositories(basePackages = "com.queen.infrastructure")
public class FellaConfiguration {
  @Bean
  public SecurityWebFilterChain securityWebFilterChain(
      ServerHttpSecurity http) {
//    return http.authorizeExchange().pathMatchers("/actuator/**").permitAll().anyExchange().permitAll().and().build();
	  return http.authorizeExchange().pathMatchers("/messages/**").hasAuthority("SCOPE_message.read").anyExchange().authenticated().and().oauth2ResourceServer().jwt(withDefaults()).and().build();
  }

	@Bean
	ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
		ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
		initializer.setConnectionFactory(connectionFactory);
		initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));

		return initializer;
	}
}
