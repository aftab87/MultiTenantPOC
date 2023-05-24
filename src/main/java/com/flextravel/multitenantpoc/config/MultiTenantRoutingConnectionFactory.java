package com.flextravel.multitenantpoc.config;

import com.flextravel.multitenantpoc.service.TenantContext;
import org.springframework.r2dbc.connection.lookup.AbstractRoutingConnectionFactory;
import reactor.core.publisher.Mono;

public class MultiTenantRoutingConnectionFactory extends AbstractRoutingConnectionFactory
{

	private final TenantContext tenantContext;

	public MultiTenantRoutingConnectionFactory(TenantContext tenantContext) {
		this.tenantContext = tenantContext;
	}

	@Override
	protected Mono<Object> determineCurrentLookupKey() {
		return Mono.just(tenantContext.getCurrentTenant());
	}
}