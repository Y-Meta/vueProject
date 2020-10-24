package com.plmm.security.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.plmm.security.core.Authentication;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SavedRequestAwareAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	protected final Log logger = LogFactory.getLog(getClass());

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws ServletException, IOException {
		super.onAuthenticationSuccess(request, response, authentication);
		return;
	}
}