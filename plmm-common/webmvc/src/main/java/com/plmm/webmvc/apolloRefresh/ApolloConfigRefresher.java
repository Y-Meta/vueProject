package com.plmm.webmvc.apolloRefresh;

import com.plmm.utils.SpringUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.plmm.webmvc.config.MultipartConfigElementChild;

import java.util.Set;

@Component
public class ApolloConfigRefresher{
	
	private Logger logger = LoggerFactory.getLogger(ApolloConfigRefresher.class);
	
//	@Autowired
//    private ApplicationContext applicationContext;

	@Value("${file-size:10}")
	private int fileSize;

	//子系统的名称
	@Value("${app.id}")
	private String appId;

    @Autowired
    private org.springframework.cloud.context.scope.refresh.RefreshScope refreshScope;

    /***
     * 监听apollo 的配置变更
     * @Param [configChangeEvent]
     * @return void
     * @Author lay.chen
     * @Date 2020/3/26
     **/
    // ,interestedKeyPrefixes = {"redis"}
    @ApolloConfigChangeListener(value = {"tp_micro_service.redis_config","tp_micro_service.ds_mysql_config","tp_micro_service.common_config"})
    public void onChange(ConfigChangeEvent configChangeEvent){
    	String namespace = configChangeEvent.getNamespace();
		Set<String> keys = configChangeEvent.changedKeys();
    	if(namespace!=null&&!"".equals(namespace)) {
    		if("tp_micro_service.redis_config".equals(namespace)) {
    			refreshScope.refresh("redisProperty");
    			logger.info("Apollo config changed {}","redisProperty");
    		}else if("tp_micro_service.ds_mysql_config".equals(namespace)) {
    			// 重新编译DataSource 初始化bean
    			refreshScope.refresh("datasourceProperties");
    			refreshScope.refresh("datasourceProperty");
    			refreshScope.refresh("datasource");
    			logger.info("Apollo config changed {}","datasourceProperties");
    			logger.info("Apollo config changed {}","datasourceProperty");
    			logger.info("Apollo config changed {}","datasource");
    		}else if("tp_micro_service.common_config".equals(namespace) && null != keys && !keys.isEmpty()){
				if(StringUtils.isNotEmpty(appId) && "web-dsp".equals(appId) && keys.contains("file-size")){
					//门户的
					MultipartConfigElementChild multipartConfigElement = (MultipartConfigElementChild)SpringUtil.getBean("multipartConfigElement");
					multipartConfigElement.setMaxFileSize(fileSize);
				}else if(StringUtils.isNotEmpty(appId) && "web-dsrp".equals(appId) && keys.contains("file-size")){
					//补录的
					MultipartConfigElementChild multipartConfigElement = (MultipartConfigElementChild)SpringUtil.getBean("multipartConfigElement");
					multipartConfigElement.setMaxFileSize(fileSize);
				}
			}
    	}else {
    		logger.info("Apollo config changed {}","nothing");
    	}

    }
}
