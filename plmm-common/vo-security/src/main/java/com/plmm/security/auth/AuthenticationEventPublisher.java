package com.plmm.security.auth;

import com.plmm.security.core.Authentication;
import com.plmm.security.core.AuthenticationException;

public interface AuthenticationEventPublisher {
	public abstract void publishAuthenticationSuccess(Authentication paramAuthentication);

	public abstract void publishAuthenticationFailure(AuthenticationException paramAuthenticationException,
                                                      Authentication paramAuthentication);
}