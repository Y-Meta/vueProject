package com.plmm.localcache;

import com.plmm.localcache.service.ICacheContextService;
import com.plmm.localcache.service.impl.EhcacheContextService;
import com.plmm.localcache.service.impl.RedisContextService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class EnabledEhcacheConfiguration {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@ConditionalOnProperty(name="plmm.session.type", havingValue="local")
	@Bean
	public ICacheContextService localContextHolder(){
		logger.info("亲，你使用的是本地session");
		return new EhcacheContextService();
	}
	
	@ConditionalOnProperty(name="plmm.session.type", havingValue="token")
	@Bean
	public ICacheContextService tokenContextHolder(){
		logger.info("亲，你使用的是token session");
		return new RedisContextService();
	}
}
