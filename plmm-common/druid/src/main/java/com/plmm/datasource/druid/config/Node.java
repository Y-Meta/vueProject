package com.plmm.datasource.druid.config;

import java.io.Serializable;

/**
 * 数据库的节点配置 
 * 包括驱动类、数据库名、数据库用户名、数据库密码以及从库信息
 * 数据库密码除开发环境为明文，其它环境都为密文
 */
@SuppressWarnings("serial")
public class Node implements Serializable{
	private String driverClassName;
	private String name;
    private String url;
    private String username;
    private String password;
    private Node slave;
    
    public void setSlave(Node slave) {
		this.slave = slave;
	}
    
    public Node getSlave() {
		return slave;
	}
    
    public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
    
    public String getDriverClassName() {
		return driverClassName;
	}
    
    public void setName(String name) {
		this.name = name;
	}
    
    public String getName() {
		return name;
	}
    
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    
}
