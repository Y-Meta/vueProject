package com.plmm.security.auth;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.plmm.common.core.authority.GrantedAuthority;
import com.plmm.security.core.Authentication;
import com.plmm.security.core.AuthorityUtils;
import com.plmm.security.core.userdetails.UserDetails;

public class AbstractAuthenticationToken implements Authentication {
	private Object details;
	private final Collection<GrantedAuthority> authorities;
	private boolean authenticated = false;

	public AbstractAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
		if (authorities == null) {
			this.authorities = AuthorityUtils.NO_AUTHORITIES;
			return;
		}

		for (GrantedAuthority a : authorities) {
			if (a == null) {
				throw new IllegalArgumentException("Authorities collection cannot contain any null elements");
			}
		}
		ArrayList temp = new ArrayList(authorities.size());
		temp.addAll(authorities);
		this.authorities = Collections.unmodifiableList(temp);
	}

	public Collection<GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	public String getName() {
		if ((getPrincipal() instanceof UserDetails)) {
			return ((UserDetails) getPrincipal()).getLoginName();
		}

		if ((getPrincipal() instanceof Principal)) {
			return ((Principal) getPrincipal()).getName();
		}

		return getPrincipal() == null ? "" : getPrincipal().toString();
	}

	public boolean isAuthenticated() {
		return this.authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	public Object getDetails() {
		return this.details;
	}

	public void setDetails(Object details) {
		this.details = details;
	}

	public void eraseCredentials() {
		eraseSecret(getCredentials());
		eraseSecret(getPrincipal());
		eraseSecret(this.details);
	}

	private void eraseSecret(Object secret) {
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof AbstractAuthenticationToken)) {
			return false;
		}

		AbstractAuthenticationToken test = (AbstractAuthenticationToken) obj;

		if (!this.authorities.equals(test.authorities)) {
			return false;
		}

		if ((this.details == null) && (test.getDetails() != null)) {
			return false;
		}

		if ((this.details != null) && (test.getDetails() == null)) {
			return false;
		}

		if ((this.details != null) && (!this.details.equals(test.getDetails()))) {
			return false;
		}

		if ((getCredentials() == null) && (test.getCredentials() != null)) {
			return false;
		}

		if ((getCredentials() != null) && (!getCredentials().equals(test.getCredentials()))) {
			return false;
		}

		if ((getPrincipal() == null) && (test.getPrincipal() != null)) {
			return false;
		}

		if ((getPrincipal() != null) && (!getPrincipal().equals(test.getPrincipal()))) {
			return false;
		}

		return isAuthenticated() == test.isAuthenticated();
	}

	public int hashCode() {
		int code = 31;

		for (GrantedAuthority authority : this.authorities) {
			code ^= authority.hashCode();
		}

		if (getPrincipal() != null) {
			code ^= getPrincipal().hashCode();
		}

		if (getCredentials() != null) {
			code ^= getCredentials().hashCode();
		}

		if (getDetails() != null) {
			code ^= getDetails().hashCode();
		}

		if (isAuthenticated()) {
			code ^= -37;
		}

		return code;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append(": ");
		sb.append("Principal: ").append(getPrincipal()).append("; ");
		sb.append("Credentials: [PROTECTED]; ");
		sb.append("Authenticated: ").append(isAuthenticated()).append("; ");
		sb.append("Details: ").append(getDetails()).append("; ");
		int i;
		if (!this.authorities.isEmpty()) {
			sb.append("Granted Authorities: ");

			i = 0;
			for (GrantedAuthority authority : this.authorities) {
				if (i++ > 0) {
					sb.append(", ");
				}

				sb.append(authority);
			}
		} else {
			sb.append("Not granted any authorities");
		}

		return sb.toString();
	}

	public Object getCredentials() {
		return null;
	}

	public Object getPrincipal() {
		return null;
	}
}