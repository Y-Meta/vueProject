package com.plmm.security.auth;

import java.util.Collection;

import com.plmm.common.core.authority.GrantedAuthority;

public class PlmmUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {
	private static final long serialVersionUID = 1L;
	
	private Object loginIp = null;
	
	private Object codeKey = null;
	
	private Object checkCode = null;

	public PlmmUsernamePasswordAuthenticationToken(Object principal, Object credentials) {
		super(principal, credentials);
	}
	
	public PlmmUsernamePasswordAuthenticationToken(Object principal, Object credentials,Object loginIp, Object codeKey, Object checkCode) {
		super(principal, credentials);
		this.loginIp = loginIp;
		this.codeKey = codeKey;
		this.checkCode = checkCode;
	}

	public PlmmUsernamePasswordAuthenticationToken(UsernamePasswordAuthenticationToken token) {
		this(token.getPrincipal(), token.getCredentials());
	}

	public PlmmUsernamePasswordAuthenticationToken(Object principal, Object credentials,Object loginIp,
			Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
		this.loginIp = loginIp;
	}
	
	public Object getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(Object loginIp) {
		this.loginIp = loginIp;
	}

	public Object getCodeKey() {
		return codeKey;
	}

	public void setCodeKey(Object codeKey) {
		this.codeKey = codeKey;
	}

	public Object getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(Object checkCode) {
		this.checkCode = checkCode;
	}
}
