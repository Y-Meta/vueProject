package com.plmm.security.core.userdetails;

import com.plmm.common.core.authority.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

public  interface UserDetails extends Serializable {
	Collection<? extends GrantedAuthority> getAuthorities();

	Collection<? extends GrantedAuthority> getSysList();

	String getPassword();

	String getUserName();

	String getLoginName();
	
	String getUsercode();

	boolean isAccountNonExpired();

	boolean isAccountNonLocked();

	boolean isCredentialsNonExpired();

	boolean isEnabled();

	boolean isAdmin();
}
