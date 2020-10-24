package com.plmm.datasource.druid.config;

import java.io.Serializable;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
/**
 * 数据库连接池配置信息类
 */
@SuppressWarnings("serial")
@ConfigurationProperties(prefix="datasource")
@EnableApolloConfig("tp_micro_service.ds_mysql_config")// 指定namespace
@Component("datasourceProperties")
@RefreshScope
public class DatasourceProperties implements Serializable{
    /**
     * 初始化连接数
     */
    private int initialSize;
    /**
     * 最小连接数
     */
    private int minIdle;
    /**
     * 最大连接数
     */
    private int maxActive;
    /**
     * 连接连接时最大等待时间
     */
    private int maxWait;
    /**
     * 连接在池内空闲时，检查周期时间，单位毫秒
     */
    private int timeBetweenEvictionRunsMillis;
    /**
     * 连接在池中空闲时最小生存的时间，单位是毫秒
     */
    private int minEvictableIdleTimeMillis;
    /**
     * 检查连接是否有效的SQL语句
     */
    private String validationQuery;
    /**
     * 连接空闲时是否检查连接是否可用
     */
    private boolean testWhileIdle;
    /**
     * 获取连接内是否检查连接是否可用
     */
    private boolean testOnBorrow;
    /**
     * 归还连接时是否检查连接是否可用
     */
    private boolean testOnReturn;
    /**
     * 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭
     */
    private boolean poolPreparedStatements;
    /**
     * preparedStatement最大缓存个数
     */
    private int maxPoolPreparedStatementPerConnectionSize;
    /**
     * 属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有： 
     *   stat : 监控统计用的filter:
     *   log4j :日志用的filter
     *   wall: 防御sql注入的filter
     */
    private String filters;
    /**
     * 物理连接初始化的时候执行的sql
     */
    private String connectionProperties;
    /**
     * 数据库节点配置集合
     */
    private List<Node> nodes;
    /**
     * 是否开启获取连接后不归还主动关闭
     */
    private boolean removeAbandoned;
    /**
     * 连接租期最长时间
     */
    private Long removeAbandonedTimeoutMillis;
    
    
    public boolean isRemoveAbandoned() {
        return removeAbandoned;
    }
    
    
    public void setRemoveAbandoned(Boolean removeAbandoned) {
        this.removeAbandoned = removeAbandoned;
    }
    
    
    public void setRemoveAbandonedTimeoutMillis(Long removeAbandonedTimeoutMillis) {
        this.removeAbandonedTimeoutMillis = removeAbandonedTimeoutMillis;
    }
    
    
    public Long getRemoveAbandonedTimeoutMillis() {
        return removeAbandonedTimeoutMillis;
    }
    
    public List<Node> getNodes() {
		return nodes;
	}
    public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	public int getInitialSize() {
		return initialSize;
	}
	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}
	public int getMinIdle() {
		return minIdle;
	}
	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}
	public int getMaxActive() {
		return maxActive;
	}
	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}
	public int getMaxWait() {
		return maxWait;
	}
	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}
	public int getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}
	public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}
	public int getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}
	public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}
	public String getValidationQuery() {
		return validationQuery;
	}
	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}
	public boolean isTestWhileIdle() {
		return testWhileIdle;
	}
	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}
	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}
	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}
	public boolean isTestOnReturn() {
		return testOnReturn;
	}
	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}
	public boolean isPoolPreparedStatements() {
		return poolPreparedStatements;
	}
	public void setPoolPreparedStatements(boolean poolPreparedStatements) {
		this.poolPreparedStatements = poolPreparedStatements;
	}
	public int getMaxPoolPreparedStatementPerConnectionSize() {
		return maxPoolPreparedStatementPerConnectionSize;
	}
	public void setMaxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize) {
		this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
	}
	public String getFilters() {
		return filters;
	}
	public void setFilters(String filters) {
		this.filters = filters;
	}
	public String getConnectionProperties() {
		return connectionProperties;
	}
	public void setConnectionProperties(String connectionProperties) {
		this.connectionProperties = connectionProperties;
	}
    
    
    
}
