package com.plmm.webmvc.config;

import javax.servlet.DispatcherType;

import org.apache.catalina.filters.HttpHeaderSecurityFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebSecurityConfig {

	@Bean
	public FilterRegistrationBean httpHeaderSecurityFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		HttpHeaderSecurityFilter httpHeaderSecurityFilter = new HttpHeaderSecurityFilter();
		filterRegistrationBean.setFilter(httpHeaderSecurityFilter);
		String url = "/*";
		filterRegistrationBean.setAsyncSupported(true);
		filterRegistrationBean.addUrlPatterns(url);
		filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST);
		filterRegistrationBean.setName("httpHeaderSecurity");
		return filterRegistrationBean;
	}
}
