package com.plmm.redis;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Redis implements Serializable{
	private String host;
	private int port;
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public String getHost() {
		return host;
	}
	
	public int getPort() {
		return port;
	}
}
