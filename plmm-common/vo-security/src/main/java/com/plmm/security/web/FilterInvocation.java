package com.plmm.security.web;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FilterInvocation {
	static final FilterChain DUMMY_CHAIN = new FilterChain() {
		public void doFilter(ServletRequest req, ServletResponse res) throws IOException, ServletException {
			throw new UnsupportedOperationException("Dummy filter chain");
		}
	};
	private FilterChain chain;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public FilterInvocation(ServletRequest request, ServletResponse response, FilterChain chain) {
		if ((request == null) || (response == null) || (chain == null)) {
			throw new IllegalArgumentException("Cannot pass null values to constructor");
		}

		this.request = ((HttpServletRequest) request);
		this.response = ((HttpServletResponse) response);
		this.chain = chain;
	}

//	public FilterInvocation(String servletPath, String method) {
//		this(null, servletPath, method);
//	}
//
//	public FilterInvocation(String contextPath, String servletPath, String method) {
//		this(contextPath, servletPath, null, null, method);
//	}
//
//	public FilterInvocation(String contextPath, String servletPath, String pathInfo, String query, String method) {
//		DummyRequest request = new DummyRequest();
//		if (contextPath == null) {
//			contextPath = "/cp";
//		}
//		request.setContextPath(contextPath);
//		request.setServletPath(servletPath);
//		request.setRequestURI(new StringBuilder().append(contextPath).append(servletPath)
//				.append(pathInfo == null ? "" : pathInfo).toString());
//
//		request.setPathInfo(pathInfo);
//		request.setQueryString(query);
//		request.setMethod(method);
//		this.request = request;
//	}

	public FilterChain getChain() {
		return this.chain;
	}

	public String getFullRequestUrl() {
		return UrlUtils.buildFullRequestUrl(this.request);
	}

	public HttpServletRequest getHttpRequest() {
		return this.request;
	}

	public HttpServletResponse getHttpResponse() {
		return this.response;
	}

	public String getRequestUrl() {
		return UrlUtils.buildRequestUrl(this.request);
	}

	public HttpServletRequest getRequest() {
		return getHttpRequest();
	}

	public HttpServletResponse getResponse() {
		return getHttpResponse();
	}

	public String toString() {
		return new StringBuilder().append("FilterInvocation: URL: ").append(getRequestUrl()).toString();
	}
}