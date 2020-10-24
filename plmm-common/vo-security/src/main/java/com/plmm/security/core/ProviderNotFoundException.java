package com.plmm.security.core;

public class ProviderNotFoundException extends AuthenticationException {
	public ProviderNotFoundException(String msg) {
		super(msg);
	}
}
