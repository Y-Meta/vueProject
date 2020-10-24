package com.plmm.security.auth.event;

import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationEvent;

import com.plmm.security.auth.plmmUsernamePasswordAuthenticationToken;
import com.plmm.security.auth.UsernamePasswordAuthenticationToken;
import com.plmm.security.core.userdetails.UserDetails;

public class TokenSessionDestroyedEvent extends ApplicationEvent {
	private static final long serialVersionUID = -7453930850382131313L;
	private String token;
	private String staffNo;
	private String loginIp;
	private String userName;
	private String userCode;

	public TokenSessionDestroyedEvent(Object source) {
		super(source);
		if ((source != null) && ((source instanceof HttpSession))) {
			HttpSession session = (HttpSession) source;
			UsernamePasswordAuthenticationToken upat = (UsernamePasswordAuthenticationToken) session
					.getAttribute("authentication");
			if ((upat != null) && (upat.getPrincipal() != null) && ((upat.getPrincipal() instanceof UserDetails))) {
				UserDetails user = (UserDetails) upat.getPrincipal();
				this.staffNo = user.getLoginName();
				this.userCode = user.getUsercode();
				this.userName = user.getUserName();
			}
			if(upat!=null && upat instanceof plmmUsernamePasswordAuthenticationToken){
				this.loginIp = (String)((plmmUsernamePasswordAuthenticationToken)upat).getLoginIp();
			}
		}
	}

	public TokenSessionDestroyedEvent(Object source, String token) {
		super(source);
		this.token = token;
		if ((source != null) && ((source instanceof HttpSession))) {
			HttpSession session = (HttpSession) source;
			UsernamePasswordAuthenticationToken upat = (UsernamePasswordAuthenticationToken) session
					.getAttribute("authentication");
			if ((upat != null) && (upat.getPrincipal() != null) && ((upat.getPrincipal() instanceof UserDetails))) {
				UserDetails user = (UserDetails) upat.getPrincipal();
				this.staffNo = user.getLoginName();
				this.userCode = user.getUsercode();
				this.userName = user.getUserName();
			}
			if(upat!=null && upat instanceof plmmUsernamePasswordAuthenticationToken){
				this.loginIp = (String)((plmmUsernamePasswordAuthenticationToken)upat).getLoginIp();
			}
		}
	}

	public String getToken() {
		return this.token;
	}

	public String getStaffNo() {
		return this.staffNo;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
