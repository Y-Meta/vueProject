package com.plmm.security.core;

public class BadCredentialsException extends AuthenticationException {
	public BadCredentialsException(String msg) {
		super(msg);
	}

	public BadCredentialsException(String msg, Throwable t) {
		super(msg, t);
	}
}