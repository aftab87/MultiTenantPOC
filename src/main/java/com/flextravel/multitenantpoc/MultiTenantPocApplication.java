package com.flextravel.multitenantpoc;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class MultiTenantPocApplication
{

	public static void main(String[] args)
	{
		new SpringApplicationBuilder(MultiTenantPocApplication.class)
				.properties("spring.config.name:application,tenants")
				.build()
				.run(args);
	}

}
