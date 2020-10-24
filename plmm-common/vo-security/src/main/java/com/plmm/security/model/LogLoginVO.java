package com.plmm.security.model;

import java.io.Serializable;
import java.util.Date;

public class LogLoginVO implements Serializable {
	private static final long serialVersionUID = 2025381965387584711L;
	private String id;
	private String fPartCity;
	private int fPartMm;
	private String personid;
	private String logintype;
	private String ipaddress;
	private Date logintime;
	private Date logouttime;
	private String location;
	private String macaddress;
	private String dnsname;

	public LogLoginVO() {
	}

	public LogLoginVO(String id, String fPartCity, int fPartMm, String personid, String logintype, String ipaddress,
			Date logintime, Date logouttime, String location, String macaddress, String dnsname) {
		this.id = id;
		this.fPartCity = fPartCity;
		this.fPartMm = fPartMm;
		this.personid = personid;
		this.logintype = logintype;
		this.ipaddress = ipaddress;
		this.logintime = logintime;
		this.logouttime = logouttime;
		this.location = location;
		this.macaddress = macaddress;
		this.dnsname = dnsname;
	}

	public LogLoginVO(String id, int fPartMm, String personid, String logintype, String ipaddress, Date logintime,
			String macaddress, String dnsname) {
		this.id = id;
		this.fPartMm = fPartMm;
		this.personid = personid;
		this.logintype = logintype;
		this.ipaddress = ipaddress;
		this.logintime = logintime;
		this.macaddress = macaddress;
		this.dnsname = dnsname;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getfPartCity() {
		return this.fPartCity;
	}

	public void setfPartCity(String fPartCity) {
		this.fPartCity = fPartCity;
	}

	public int getfPartMm() {
		return this.fPartMm;
	}

	public void setfPartMm(int fPartMm) {
		this.fPartMm = fPartMm;
	}

	public String getPersonid() {
		return this.personid;
	}

	public void setPersonid(String personid) {
		this.personid = personid;
	}

	public String getLogintype() {
		return this.logintype;
	}

	public void setLogintype(String logintype) {
		this.logintype = logintype;
	}

	public String getIpaddress() {
		return this.ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public Date getLogintime() {
		return this.logintime;
	}

	public void setLogintime(Date logintime) {
		this.logintime = logintime;
	}

	public Date getLogouttime() {
		return this.logouttime;
	}

	public void setLogouttime(Date logouttime) {
		this.logouttime = logouttime;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMacaddress() {
		return this.macaddress;
	}

	public void setMacaddress(String macaddress) {
		this.macaddress = macaddress;
	}

	public String getDnsname() {
		return this.dnsname;
	}

	public void setDnsname(String dnsname) {
		this.dnsname = dnsname;
	}
}
