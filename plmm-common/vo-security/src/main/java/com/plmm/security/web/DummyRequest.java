package com.plmm.security.web;
//package com.plmm.security.web;
//
//import java.lang.reflect.Proxy;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletRequestWrapper;
//
//public class DummyRequest extends HttpServletRequestWrapper {
//	private static final HttpServletRequest UNSUPPORTED_REQUEST = (HttpServletRequest) Proxy.newProxyInstance(
//			DummyRequest.class.getClassLoader(), new Class[] { HttpServletRequest.class },
//			new UnsupportedOperationExceptionInvocationHandler());
//	private String requestURI;
//	private String contextPath = "";
//	private String servletPath;
//	private String pathInfo;
//	private String queryString;
//	private String method;
//
//	public DummyRequest() {
//		super(UNSUPPORTED_REQUEST);
//	}
//
//	public void setRequestURI(String requestURI) {
//		this.requestURI = requestURI;
//	}
//
//	public void setPathInfo(String pathInfo) {
//		this.pathInfo = pathInfo;
//	}
//
//	public String getRequestURI() {
//		return this.requestURI;
//	}
//
//	public void setContextPath(String contextPath) {
//		this.contextPath = contextPath;
//	}
//
//	public String getContextPath() {
//		return this.contextPath;
//	}
//
//	public void setServletPath(String servletPath) {
//		this.servletPath = servletPath;
//	}
//
//	public String getServletPath() {
//		return this.servletPath;
//	}
//
//	public void setMethod(String method) {
//		this.method = method;
//	}
//
//	public String getMethod() {
//		return this.method;
//	}
//
//	public String getPathInfo() {
//		return this.pathInfo;
//	}
//
//	public String getQueryString() {
//		return this.queryString;
//	}
//
//	public void setQueryString(String queryString) {
//		this.queryString = queryString;
//	}
//}