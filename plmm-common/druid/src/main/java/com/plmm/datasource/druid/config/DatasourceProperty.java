package com.plmm.datasource.druid.config;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

@SuppressWarnings("serial")
@ConfigurationProperties(prefix = "datasource-config")
@EnableApolloConfig("tp_micro_service.ds_mysql_config")
@RefreshScope
@Component("datasourceProperty")
public class DatasourceProperty implements Serializable {
	/**
	 * 单一数据源
	 */
	public static final String DATASOURCE_TYPE_SINGLE = "single";
	/**
	 * 路由数据源
	 */
	public static final String DATASOURCE_TYPE_ROUTING = "routing";
	/**
	 * 路由数据源+读写分离
	 */
	public static final String DATASOURCE_TYPE_ROUTING_READWRITE = "routingreadwrite";
	
	/**
	 * 数据源类型（single,routing,routing&readwrite)三种
	 */
	private String type;
	/**
	 * 需要获取的数据源配置，如有多个请用逗号(,)分隔
	 */
	private String name;
	/**
	 * 如果数据源类型为routingreadwrite,则此属性会有效，以此前缀来区分service操作是否为写操作
	 * 多个前缀请用逗号(,)分隔
	 */
	private String prefix;
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public String getPrefix() {
		return prefix;
	}
    
    public void setType(String type) {
		this.type = type;
	}
    
    public String getType() {
		return type;
	}
    
    public void setName(String name) {
		this.name = name;
	}
    
    public String getName() {
		return name;
	}
}
