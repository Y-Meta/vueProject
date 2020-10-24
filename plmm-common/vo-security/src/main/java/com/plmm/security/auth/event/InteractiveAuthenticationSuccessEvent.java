package com.plmm.security.auth.event;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;

import com.plmm.security.core.Authentication;

public class InteractiveAuthenticationSuccessEvent extends AbstractAuthenticationEvent {
	private final Class<?> generatedBy;
	private HttpServletRequest request;

	public InteractiveAuthenticationSuccessEvent(Authentication authentication, Class<?> generatedBy) {
		super(authentication);
		Assert.notNull(generatedBy, "generatedBy cannot be null");
		this.generatedBy = generatedBy;
	}

	public InteractiveAuthenticationSuccessEvent(Authentication authentication, Class<?> generatedBy,
			HttpServletRequest request) {
		super(authentication);
		Assert.notNull(generatedBy, "generatedBy cannot be null");
		this.generatedBy = generatedBy;
		this.request = request;
	}

	public Class<?> getGeneratedBy() {
		return this.generatedBy;
	}

	public HttpServletRequest getRequest() {
		return this.request;
	}
}
