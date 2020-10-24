package com.plmm.security.auth.event;

import org.springframework.context.ApplicationEvent;

import com.plmm.security.core.Authentication;

public abstract class AbstractAuthenticationEvent extends ApplicationEvent {
	public AbstractAuthenticationEvent(Authentication authentication) {
		super(authentication);
	}

	public Authentication getAuthentication() {
		return (Authentication) super.getSource();
	}
}