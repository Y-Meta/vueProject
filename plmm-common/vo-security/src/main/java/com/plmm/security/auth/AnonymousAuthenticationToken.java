package com.plmm.security.auth;

import java.util.Collection;

import com.plmm.common.core.authority.GrantedAuthority;

public class AnonymousAuthenticationToken extends AbstractAuthenticationToken {
	private static final long serialVersionUID = 1L;
	private final Object principal;
	private final int keyHash;

	public AnonymousAuthenticationToken(String key, Object principal,
			Collection<? extends GrantedAuthority> authorities) {
		super(authorities);

		if ((key == null) || ("".equals(key)) || (principal == null) || ("".equals(principal)) || (authorities == null)
				|| (authorities.isEmpty())) {
			throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
		}

		this.keyHash = key.hashCode();
		this.principal = principal;
		setAuthenticated(true);
	}

	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}

		if ((obj instanceof AnonymousAuthenticationToken)) {
			AnonymousAuthenticationToken test = (AnonymousAuthenticationToken) obj;

			return getKeyHash() == test.getKeyHash();
		}

		return false;
	}

	public Object getCredentials() {
		return "";
	}

	public int getKeyHash() {
		return this.keyHash;
	}

	public Object getPrincipal() {
		return this.principal;
	}
}