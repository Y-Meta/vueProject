package com.plmm.security.auth.event;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationEvent;

import com.plmm.security.core.AuthenticationException;
import com.plmm.security.web.IpUtils;

public class InteractiveAuthenticationFailedEvent extends ApplicationEvent {
	private static final long serialVersionUID = -8148478430414675382L;
	private AuthenticationException exception;
	private String loginName;
	private String loginIp;
	
	public InteractiveAuthenticationFailedEvent(Object source) {
		super(source);
	}

	public InteractiveAuthenticationFailedEvent(Object source,AuthenticationException exception) {
		super(source);
		if ((source != null) && ((source instanceof HttpServletRequest))) {
			HttpServletRequest request = (HttpServletRequest) source;
			String loginName = request.getParameter("username");
			String loginIp = IpUtils.getIpAddr(request);
			this.loginName = loginName;
			this.loginIp = loginIp;
			this.exception = exception;
		}
	}

	public AuthenticationException getException() {
		return exception;
	}

	public String getLoginName() {
		return loginName;
	}

	public String getLoginIp() {
		return loginIp;
	}
}
