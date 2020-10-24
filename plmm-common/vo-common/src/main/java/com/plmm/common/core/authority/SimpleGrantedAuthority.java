package com.plmm.common.core.authority;

public class SimpleGrantedAuthority implements GrantedAuthority {

	private final String role;

	public SimpleGrantedAuthority(String role) {
		this.role = role;
	}

	public String getAuthority() {
		return this.role;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if ((obj instanceof SimpleGrantedAuthority)) {
			return this.role.equals(((SimpleGrantedAuthority) obj).role);
		}

		return false;
	}

	public int hashCode() {
		return this.role.hashCode();
	}

	public String toString() {
		return this.role;
	}
}
