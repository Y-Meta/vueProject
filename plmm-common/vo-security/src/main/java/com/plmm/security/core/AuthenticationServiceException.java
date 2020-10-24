package com.plmm.security.core;

public class AuthenticationServiceException extends AuthenticationException {
	public AuthenticationServiceException(String msg) {
		super(msg);
	}

	public AuthenticationServiceException(String msg, Throwable t) {
		super(msg, t);
	}
}