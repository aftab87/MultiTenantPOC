package com.flextravel.multitenantpoc.config;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;

import java.util.HashMap;
import java.util.Map;

import static io.r2dbc.spi.ConnectionFactoryOptions.DRIVER;
import static io.r2dbc.spi.ConnectionFactoryOptions.HOST;
import static io.r2dbc.spi.ConnectionFactoryOptions.USER;
import static io.r2dbc.spi.ConnectionFactoryOptions.PASSWORD;
import static io.r2dbc.spi.ConnectionFactoryOptions.DATABASE;
import static io.r2dbc.spi.ConnectionFactoryOptions.PORT;

@Configuration
public class MultiTenantConfiguration
{
	private final Environment env;

	public MultiTenantConfiguration(Environment env) {
		super();
		this.env = env;
	}

	@Bean
	public Map<String, ConnectionFactory> connectionFactoryMap() {
		Map<String, ConnectionFactory> connectionFactoryMap = new HashMap<>();

		ConnectionFactory acvConnectionFactory = ConnectionFactories.get(ConnectionFactoryOptions.builder()
				.option(DRIVER, "mariadb")
				.option(HOST, "localhost")
				.option(PORT, 3306)
				.option(USER, env.getProperty("tenants.acv.username"))
				.option(PASSWORD, env.getProperty("tenants.acv.password"))
				.option(DATABASE, "acv")
				.build());

		ConnectionFactory porterConnectionFactory = ConnectionFactories.get(ConnectionFactoryOptions.builder()
				.option(DRIVER, "mariadb")
				.option(HOST, "localhost")
				.option(PORT, 3306)
				.option(USER, env.getProperty("tenants.porter.username"))
				.option(PASSWORD, env.getProperty("tenants.porter.password"))
				.option(DATABASE, "porter")
				.build());

		connectionFactoryMap.put("acv", acvConnectionFactory);
		connectionFactoryMap.put("porter", porterConnectionFactory);

		return connectionFactoryMap;
	}

	@Bean
	public DatabaseClient databaseClient(@Autowired MultiTenantRoutingConnectionFactory connectionFactory) {
		return DatabaseClient.builder()
				.connectionFactory(connectionFactory)
				.build();
	}

	@Bean
	public R2dbcEntityTemplate entityTemplate(@Autowired DatabaseClient databaseClient) {
		return new R2dbcEntityTemplate(databaseClient);
	}
}
