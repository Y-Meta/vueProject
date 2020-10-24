package com.plmm.security.core;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;

import com.plmm.common.core.authority.GrantedAuthority;

public abstract interface Authentication extends Principal, Serializable {
	public abstract Collection<? extends GrantedAuthority> getAuthorities();

	public abstract Object getCredentials();

	public abstract Object getDetails();

	public abstract Object getPrincipal();

	public abstract boolean isAuthenticated();

	public abstract void setAuthenticated(boolean paramBoolean) throws IllegalArgumentException;
}