package com.plmm.security.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.plmm.security.core.Authentication;
import com.plmm.security.core.context.SecurityContextHolder;

public class SimpleUrlAuthenticationSuccessHandler extends AbstractAuthenticationTargetUrlRequestHandler
		implements AuthenticationSuccessHandler {
	public SimpleUrlAuthenticationSuccessHandler() {
	}

	public SimpleUrlAuthenticationSuccessHandler(String defaultTargetUrl) {
		setDefaultTargetUrl(defaultTargetUrl);
	}

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		handle(request, response, authentication);
		clearAuthenticationAttributes(request);
	}

	protected final void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		SecurityContextHolder.clearContext();
		session.removeAttribute("SPRING_SECURITY_LAST_EXCEPTION");
	}
}
