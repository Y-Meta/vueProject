package com.plmm.security.core.userdetails;

import java.io.Serializable;
import java.util.*;

import org.springframework.util.Assert;

import com.plmm.common.core.authority.GrantedAuthority;

public class User implements UserDetails {
	private static final long serialVersionUID = -5512450212587045922L;
	private String password;
	private final String loginName;
	private final String userName;
	private final String userCode;
	private final Set<GrantedAuthority> authorities;
	private final List<GrantedAuthority> sysList;
	private final boolean accountNonExpired;
	private final boolean accountNonLocked;
	private final boolean credentialsNonExpired;
	private final boolean enabled;
	private final boolean admin;

	public User(String loginName,String userName, String userCode,String password, Collection<? extends GrantedAuthority> authorities, List<GrantedAuthority> sysList, boolean admin) {
		this(loginName,userName,password, userCode,true, true, true, true, authorities, sysList, admin);
	}

	public User(String loginName,String userName, String password,String userCode, boolean enabled, boolean accountNonExpired,
				boolean credentialsNonExpired, boolean accountNonLocked,
				Collection<? extends GrantedAuthority> authorities, List<GrantedAuthority> sysList, boolean admin) {
		this.sysList = sysList;
		this.admin = admin;
		if ((loginName == null) || ("".equals(loginName)) || (password == null)) {
			throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
		}

		this.userName = userName;
		this.loginName = loginName;
		this.userCode = userCode;
		this.password = password;
		this.enabled = enabled;
		this.accountNonExpired = accountNonExpired;
		this.credentialsNonExpired = credentialsNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
	}

	public Collection<GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getSysList() {
		return this.sysList;
	}

	public String getPassword() {
		return this.password;
	}

	public String getUserName() {
		return userName;
	}

	public String getLoginName() {
		return loginName;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	@Override
	public boolean isAdmin() {
		return this.admin;
	}

	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

//	public void eraseCredentials() {
//		this.password = null;
//	}

	private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
		Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");

		SortedSet sortedAuthorities = new TreeSet(new AuthorityComparator(null));

		for (GrantedAuthority grantedAuthority : authorities) {
			Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");

			sortedAuthorities.add(grantedAuthority);
		}

		return sortedAuthorities;
	}

	public boolean equals(Object rhs) {
		if ((rhs instanceof User)) {
			return this.loginName.equals(((User) rhs).loginName);
		}
		return false;
	}

	public int hashCode() {
		return this.loginName.hashCode();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append(": ");
		sb.append("LoginName: ").append(this.loginName).append("; ");
		sb.append("Password: [PROTECTED]; ");
		sb.append("Enabled: ").append(this.enabled).append("; ");
		sb.append("AccountNonExpired: ").append(this.accountNonExpired).append("; ");
		sb.append("credentialsNonExpired: ").append(this.credentialsNonExpired).append("; ");

		sb.append("AccountNonLocked: ").append(this.accountNonLocked).append("; ");
		boolean first;
		if (!this.authorities.isEmpty()) {
			sb.append("Granted Authorities: ");

			first = true;
			for (GrantedAuthority auth : this.authorities) {
				if (!first) {
					sb.append(",");
				}
				first = false;

				sb.append(auth);
			}
		} else {
			sb.append("Not granted any authorities");
		}

		return sb.toString();
	}

	private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
		private static final long serialVersionUID = 5895704810470230256L;

		public AuthorityComparator(Object object) {
		}

		public int compare(GrantedAuthority g1, GrantedAuthority g2) {
			if (g2.getAuthority() == null) {
				return -1;
			}

			if (g1.getAuthority() == null) {
				return 1;
			}

			return g1.getAuthority().compareTo(g2.getAuthority());
		}
	}

	@Override
	public String getUsercode() {
		return this.userCode;
	}
}
