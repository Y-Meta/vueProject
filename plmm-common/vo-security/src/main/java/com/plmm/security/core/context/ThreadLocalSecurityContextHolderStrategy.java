package com.plmm.security.core.context;

import org.springframework.util.Assert;

public class ThreadLocalSecurityContextHolderStrategy implements SecurityContextHolderStrategy {
	private static final ThreadLocal<SecurityContext> contextHolder = new ThreadLocal();

	public void clearContext() {
		contextHolder.remove();
	}

	public SecurityContext getContext() {
		SecurityContext ctx = (SecurityContext) contextHolder.get();

		if (ctx == null) {
			ctx = createEmptyContext();
			contextHolder.set(ctx);
		}

		return ctx;
	}

	public void setContext(SecurityContext context) {
		Assert.notNull(context, "Only non-null SecurityContext instances are permitted");
		contextHolder.set(context);
	}

	public SecurityContext createEmptyContext() {
		return new SecurityContextImpl();
	}
}