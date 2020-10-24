package com.plmm.security.model;

import java.io.Serializable;

public class UserCreditials implements Serializable {
	private static final long serialVersionUID = 6230591178610576942L;
	//用户名,非登录名
	private String userName;
	private String password;
	private String userCode;
	private String flag;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public String getUserCode() {
		return userCode;
	}

	public void setUsercode(String userCode) {
		this.userCode = userCode;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}
}