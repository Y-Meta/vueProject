package com.plmm.security.core.context;

public abstract interface SecurityContextHolderStrategy {
	public abstract void clearContext();

	public abstract SecurityContext getContext();

	public abstract void setContext(SecurityContext paramSecurityContext);

	public abstract SecurityContext createEmptyContext();
}