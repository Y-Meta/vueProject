package com.plmm.security.auth;

import com.plmm.security.core.Authentication;
import com.plmm.security.core.AuthenticationException;

public interface AuthenticationManager {
	public abstract Authentication authenticate(Authentication paramAuthentication) throws AuthenticationException;
}