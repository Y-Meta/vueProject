package com.plmm.security.core;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;

public class SpringSecurityMessageSource extends ResourceBundleMessageSource {
	
	public SpringSecurityMessageSource() {
		setBasename("messages");
	}

	public static MessageSourceAccessor getAccessor() {
		return new MessageSourceAccessor(new SpringSecurityMessageSource());
	}
}
