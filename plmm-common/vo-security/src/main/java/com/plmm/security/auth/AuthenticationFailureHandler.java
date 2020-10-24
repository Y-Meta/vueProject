package com.plmm.security.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.plmm.security.core.AuthenticationException;

public interface AuthenticationFailureHandler {
	public void onAuthenticationFailure(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse, AuthenticationException paramAuthenticationException)
			throws IOException, ServletException;
}
