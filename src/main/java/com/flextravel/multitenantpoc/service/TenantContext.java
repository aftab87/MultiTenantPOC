package com.flextravel.multitenantpoc.service;

import org.springframework.stereotype.Service;

@Service
public class TenantContext {

	private ThreadLocal<String> currentTenant = new ThreadLocal<>();

	public void setCurrentTenant(String tenantId) {
		currentTenant.set(tenantId);
	}

	public String getCurrentTenant() {
		return currentTenant.get();
	}
}