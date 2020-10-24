package com.plmm.security.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.plmm.security.core.Authentication;

public interface AuthenticationSuccessHandler {
	public void onAuthenticationSuccess(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse, Authentication paramAuthentication)
			throws IOException, ServletException;
}
