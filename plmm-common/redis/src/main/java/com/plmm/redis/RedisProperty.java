package com.plmm.redis;

import java.io.Serializable;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

@SuppressWarnings("serial")
//@ConfigurationProperties(prefix = "plmm.redis")
@ConfigurationProperties(prefix = "redis")
@EnableApolloConfig("tp_micro_service.redis_config")
@RefreshScope
@Component("redisProperty")
public class RedisProperty implements Serializable{
    /**
     * The default value for the {@code maxTotal} configuration attribute.
     */
    private static final int DEFAULT_MAX_TOTAL = 8;

    /**
     * The default value for the {@code maxIdle} configuration attribute.
     */
    private static final int DEFAULT_MAX_IDLE = 8;

    /**
     * The default value for the {@code minIdle} configuration attribute.
     */
    private static final int DEFAULT_MIN_IDLE = 0;
    /**
     * The default value for the {@code testOnBorrow} configuration attribute.
     */
    private static final boolean DEFAULT_TEST_ON_BORROW = false;

    /**
     * The default value for the {@code testOnReturn} configuration attribute.
     */
    private static final boolean DEFAULT_TEST_ON_RETURN = false;

    /**
     * The default value for the {@code testWhileIdle} configuration attribute.
     */
    private static final boolean DEFAULT_TEST_WHILE_IDLE = false;
    /**
     * The default value for the {@code timeBetweenEvictionRunsMillis}
     * configuration attribute.
     */
    private static final long DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS = -1L;
    /**
     * The default value for the {@code numTestsPerEvictionRun} configuration
     * attribute.
     */
    private static final int DEFAULT_NUM_TESTS_PER_EVICTION_RUN = 3;
    /**
     * The default value for the {@code soTimeout} configuration attribute.
     */
    private static final int DEFAULT_SO_TIMEOUT = 5000;
    /**
     * The default value for the {@code connectionTimeout} configuration attribute.
     */
    private static final int DEFAULT_CONNECTION_TIMEOUT = 3000;
    /**
     * The default value for the {@code connectionTimeout} configuration attribute.
     */
    private static final int DEFAULT_MAX_ATTEMPTS = 2;
	/**连接命令执行超时时间，默认为5秒，单位为毫秒*/
    private int soTimeout = DEFAULT_SO_TIMEOUT;
	/**获取连接的超时时间,默认为3秒，单位毫秒*/
	private int connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;
	/**命令失败时，最大重试次数,默认为2次*/
	private int maxAttempts = DEFAULT_MAX_ATTEMPTS;
	/**最大连接数，默认值为8，如果赋值为-1，则表示不限制**/
	private int maxTotal = DEFAULT_MAX_TOTAL;
	/**最大空闲连接数，默认值为8**/
	private int maxIdle = DEFAULT_MAX_IDLE;
	/**最小空闲连接数，默认为0**/
	private int minIdel = DEFAULT_MIN_IDLE;
	/**从连接池中获取连接时是否进行有效性检查*/
	private boolean testOnBorrow = DEFAULT_TEST_ON_BORROW;
	/**在连接空闲时进行是否进行有效性检查*/
	private boolean testOnIdle = DEFAULT_TEST_WHILE_IDLE;
	/**连接归还到连接池时是否进行有效性检查 */
	private boolean testOnReturn = DEFAULT_TEST_ON_RETURN;
	/**空闲连接的检测线程，检测周期时间，单位为毫秒； 如果为负值则不进行检查，默认为-1 */
	private long timeBetweenEvictionRunsMillis = DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS;
	/**检测线程每次检测连接的个数，默认为3,为负数时全部检查*/
	private int numTestsPerEvictionRun = DEFAULT_NUM_TESTS_PER_EVICTION_RUN;
	/**redis密码*/
	private String passwd;
	/**redis集群地址*/
	private List<Redis> jedis;
	
	public void setSoTimeout(int soTimeout) {
		this.soTimeout = soTimeout;
	}
	
	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}
	
	public void setMaxAttempts(int maxAttempts) {
		this.maxAttempts = maxAttempts;
	}
	
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	public int getSoTimeout() {
		return soTimeout;
	}
	
	public int getConnectionTimeout() {
		return connectionTimeout;
	}
	
	public String getPasswd() {
		return passwd;
	}
	
	public int getMaxAttempts() {
		return maxAttempts;
	}
	
	public void setJedis(List<Redis> jedis) {
		this.jedis = jedis;
	}
	
	public List<Redis> getJedis() {
		return jedis;
	}

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMinIdel() {
        return minIdel;
    }

    public void setMinIdel(int minIdel) {
        this.minIdel = minIdel;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnIdle() {
        return testOnIdle;
    }

    public void setTestOnIdle(boolean testOnIdle) {
        this.testOnIdle = testOnIdle;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public long getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public int getNumTestsPerEvictionRun() {
        return numTestsPerEvictionRun;
    }

    public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
        this.numTestsPerEvictionRun = numTestsPerEvictionRun;
    }
	
	
}
