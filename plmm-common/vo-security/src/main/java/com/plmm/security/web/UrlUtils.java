package com.plmm.security.web;

import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

public class UrlUtils {
	public static String buildFullRequestUrl(HttpServletRequest r) {
		return buildFullRequestUrl(r.getScheme(), r.getServerName(), r.getServerPort(), r.getRequestURI(),
				r.getQueryString());
	}

	public static String buildFullRequestUrl(String scheme, String serverName, int serverPort, String requestURI,
			String queryString) {
		scheme = scheme.toLowerCase();

		StringBuilder url = new StringBuilder();
		url.append(scheme).append("://").append(serverName);

		if ("http".equals(scheme)) {
			if (serverPort != 80) {
				url.append(":").append(serverPort);
			}
		} else if (("https".equals(scheme)) && (serverPort != 443)) {
			url.append(":").append(serverPort);
		}

		url.append(requestURI);

		if (queryString != null) {
			url.append("?").append(queryString);
		}

		return url.toString();
	}

	public static String buildRequestUrl(HttpServletRequest r) {
		return buildRequestUrl(r.getServletPath(), r.getRequestURI(), r.getContextPath(), r.getPathInfo(),
				r.getQueryString());
	}

	private static String buildRequestUrl(String servletPath, String requestURI, String contextPath, String pathInfo,
			String queryString) {
		StringBuilder url = new StringBuilder();

		if (servletPath != null) {
			url.append(servletPath);
			if (pathInfo != null)
				url.append(pathInfo);
		} else {
			url.append(requestURI.substring(contextPath.length()));
		}

		if (queryString != null) {
			url.append("?").append(queryString);
		}

		return url.toString();
	}

	public static boolean isValidRedirectUrl(String url) {
		return ((url != null) && (url.startsWith("/"))) || (isAbsoluteUrl(url));
	}

	public static boolean isAbsoluteUrl(String url) {
		Pattern ABSOLUTE_URL = Pattern.compile("\\A[a-z0-9.+-]+://.*", 2);

		return ABSOLUTE_URL.matcher(url).matches();
	}
}
