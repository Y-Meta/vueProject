package com.plmm.security.auth;

import java.util.Collections;
import java.util.List;

import com.plmm.security.core.Authentication;
import com.plmm.security.core.AuthenticationException;
import com.plmm.security.core.ProviderNotFoundException;
import com.plmm.security.core.SpringSecurityMessageSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.util.Assert;

public class ProviderManager implements AuthenticationManager, InitializingBean {
	private static final Log logger = LogFactory.getLog(ProviderManager.class);

	private AuthenticationEventPublisher eventPublisher = new NullEventPublisher();
	private List<AuthenticationProvider> providers = Collections.emptyList();
	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
	private AuthenticationManager parent;
	private boolean eraseCredentialsAfterAuthentication = true;

	public ProviderManager(List<AuthenticationProvider> providers) {
		this(providers, null);
	}

	public ProviderManager(List<AuthenticationProvider> providers, AuthenticationManager parent) {
		Assert.notNull(providers, "providers list cannot be null");
		this.providers = providers;
		this.parent = parent;
		checkState();
	}

	public void afterPropertiesSet() throws Exception {
		checkState();
	}

	private void checkState() {
		if ((this.parent == null) && (this.providers.isEmpty()))
			throw new IllegalArgumentException(
					"A parent AuthenticationManager or a list of AuthenticationProviders is required");
	}

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Class toTest = authentication.getClass();
		AuthenticationException lastException = null;
		Authentication result = null;
		boolean debug = logger.isDebugEnabled();

		for (AuthenticationProvider provider : getProviders()) {
			if (!provider.supports(toTest)) {
				continue;
			}
			if (debug) {
				logger.debug("Authentication attempt using " + provider.getClass().getName());
			}
			try {
				result = provider.authenticate(authentication);

				if (result != null) {
					copyDetails(authentication, result);
					break;
				}
			} catch (AuthenticationException e) {
				lastException = e;
			}
		}

		if ((result == null) && (this.parent != null)) {
			try {
				result = this.parent.authenticate(authentication);
			} catch (ProviderNotFoundException localProviderNotFoundException1) {
			} catch (AuthenticationException e) {
				lastException = e;
			}
		}

		if (result != null) {
			if (this.eraseCredentialsAfterAuthentication)
				;
			this.eventPublisher.publishAuthenticationSuccess(result);
			return result;
		}

		if (lastException == null) {
			lastException = new ProviderNotFoundException(this.messages.getMessage("ProviderManager.providerNotFound",
					new Object[] { toTest.getName() }, "No AuthenticationProvider found for {0}"));
		}

		prepareException(lastException, authentication);

		throw lastException;
	}

	private void prepareException(AuthenticationException ex, Authentication auth) {
		this.eventPublisher.publishAuthenticationFailure(ex, auth);
	}

	private void copyDetails(Authentication source, Authentication dest) {
		if (((dest instanceof AbstractAuthenticationToken)) && (dest.getDetails() == null)) {
			AbstractAuthenticationToken token = (AbstractAuthenticationToken) dest;

			token.setDetails(source.getDetails());
		}
	}

	public List<AuthenticationProvider> getProviders() {
		return this.providers;
	}

	public void setAuthenticationEventPublisher(AuthenticationEventPublisher eventPublisher) {
		Assert.notNull(eventPublisher, "AuthenticationEventPublisher cannot be null");
		this.eventPublisher = eventPublisher;
	}

	public void setEraseCredentialsAfterAuthentication(boolean eraseSecretData) {
		this.eraseCredentialsAfterAuthentication = eraseSecretData;
	}

	public boolean isEraseCredentialsAfterAuthentication() {
		return this.eraseCredentialsAfterAuthentication;
	}

	private static final class NullEventPublisher implements AuthenticationEventPublisher {

		public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		}

		public void publishAuthenticationSuccess(Authentication authentication) {
		}
	}
}