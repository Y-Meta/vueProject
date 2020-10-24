package com.plmm.security.core.userdetails;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class WebAuthenticationDetails implements Serializable {
	private static final long serialVersionUID = 5420007228053872457L;
	private final String remoteAddress;
	private final String sessionId;
	private final String localAddress;

	public WebAuthenticationDetails(HttpServletRequest request) {
		String strIp = request.getHeader("x-forwarded-for");
		if ((strIp == null) || (strIp.length() == 0) || ("unknown".equalsIgnoreCase(strIp))) {
			strIp = request.getHeader("Proxy-Client-IP");
		}
		if ((strIp == null) || (strIp.length() == 0) || ("unknown".equalsIgnoreCase(strIp))) {
			strIp = request.getHeader("WL-Proxy-Client-IP");
		}
		if ((strIp == null) || (strIp.length() == 0) || ("unknown".equalsIgnoreCase(strIp))) {
			strIp = request.getRemoteAddr();
		}
		this.remoteAddress = strIp;

		HttpSession session = request.getSession(false);
		this.sessionId = (session != null ? session.getId() : null);
		this.localAddress = (request.getLocalAddr() + ":" + request.getLocalPort());
	}

	public String getLocalAddress() {
		return this.localAddress;
	}

	public boolean equals(Object obj) {
		if ((obj instanceof WebAuthenticationDetails)) {
			WebAuthenticationDetails rhs = (WebAuthenticationDetails) obj;

			if ((this.remoteAddress == null) && (rhs.getRemoteAddress() != null)) {
				return false;
			}

			if ((this.remoteAddress != null) && (rhs.getRemoteAddress() == null)) {
				return false;
			}

			if ((this.remoteAddress != null) && (!this.remoteAddress.equals(rhs.getRemoteAddress()))) {
				return false;
			}

			if ((this.sessionId == null) && (rhs.getSessionId() != null)) {
				return false;
			}

			if ((this.sessionId != null) && (rhs.getSessionId() == null)) {
				return false;
			}

			return (this.sessionId == null) || (this.sessionId.equals(rhs.getSessionId()));
		}

		return false;
	}

	public String getRemoteAddress() {
		return this.remoteAddress;
	}

	public String getSessionId() {
		return this.sessionId;
	}

	public int hashCode() {
		int code = 7654;

		if (this.remoteAddress != null) {
			code *= this.remoteAddress.hashCode() % 7;
		}

		if (this.sessionId != null) {
			code *= this.sessionId.hashCode() % 7;
		}

		return code;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append(": ");
		sb.append("RemoteIpAddress: ").append(getRemoteAddress()).append("; ");
		sb.append("SessionId: ").append(getSessionId());

		return sb.toString();
	}
}