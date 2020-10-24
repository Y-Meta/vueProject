package com.plmm.redis;

import java.util.HashSet;
import java.util.Set;

import com.plmm.cryptography.Base64Cryptography;
import com.plmm.cryptography.Cryptography;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

//@Configuration
//@EnableConfigurationProperties(RedisProperty.class)
public class EnabledRedisConfiguration {
	
	@Autowired
	@Qualifier("redisProperty")
	RedisProperty property;
	@Autowired(required=false)
    Cryptography cryptography;
	@Bean
	public JedisCluster jedisCluster(){
		Set<HostAndPort> hostAndPortSet = new HashSet<HostAndPort>(3);
		for(Redis redis : property.getJedis()) {
			hostAndPortSet.add(new HostAndPort(redis.getHost(), redis.getPort()));
		}
		String dev = System.getProperty("spring.profiles.active");
		String passwd = null;//测试环境没有设置密码
//		String passwd = property.getPasswd();
//		if(!(dev == null || "local".equals(dev))) {//生产测试环境密码需解密
//			passwd = cryptography.decrypt(passwd);
//	    } 
		return new JedisCluster(hostAndPortSet, property.getConnectionTimeout(), property.getSoTimeout(), property.getMaxAttempts(),
				passwd, jedisPoolConfig());
	}
	
	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(property.getMaxIdle());
		jedisPoolConfig.setMaxTotal(property.getMaxTotal());
		jedisPoolConfig.setMinIdle(property.getMinIdel());
		jedisPoolConfig.setTestOnBorrow(property.isTestOnBorrow());
		jedisPoolConfig.setTestOnReturn(property.isTestOnReturn());
		jedisPoolConfig.setTestWhileIdle(property.isTestOnIdle());
		jedisPoolConfig.setTimeBetweenEvictionRunsMillis(property.getTimeBetweenEvictionRunsMillis());
		jedisPoolConfig.setNumTestsPerEvictionRun(property.getNumTestsPerEvictionRun());
		return jedisPoolConfig;
	}
	
	@Bean
	@ConditionalOnMissingBean
	@Profile({"dev","pro","uat","ft","sit","pre"})
	public Cryptography cryptography(){
		return new Base64Cryptography();
	}
}
