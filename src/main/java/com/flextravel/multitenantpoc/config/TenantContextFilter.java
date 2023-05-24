package com.flextravel.multitenantpoc.config;

import com.flextravel.multitenantpoc.service.TenantContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class TenantContextFilter implements WebFilter
{
	private final TenantContext tenantContext;

	public TenantContextFilter(TenantContext tenantContext) {
		super();
		this.tenantContext = tenantContext;
	}
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain)
	{
		String tenantId = exchange.getRequest().getHeaders().getFirst("X-TenantID");
		tenantContext.setCurrentTenant(tenantId);

		return chain.filter(exchange);
	}
}