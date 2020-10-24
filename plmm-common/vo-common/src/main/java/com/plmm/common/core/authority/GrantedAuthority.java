package com.plmm.common.core.authority;

import java.io.Serializable;

public abstract interface GrantedAuthority extends Serializable {
	public abstract String getAuthority();
}
