package com.plmm.security.auth;

import com.plmm.security.core.Authentication;
import com.plmm.security.core.AuthenticationException;

public interface AuthenticationProvider {
	public Authentication authenticate(Authentication paramAuthentication) throws AuthenticationException;

	public boolean supports(Class<?> paramClass);
}
