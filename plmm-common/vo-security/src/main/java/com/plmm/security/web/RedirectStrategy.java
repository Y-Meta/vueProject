package com.plmm.security.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RedirectStrategy {
	public abstract void sendRedirect(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse, String paramString) throws IOException;
}