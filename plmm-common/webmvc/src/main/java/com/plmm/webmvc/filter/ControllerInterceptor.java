package com.plmm.webmvc.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import redis.clients.jedis.JedisCluster;

public class ControllerInterceptor extends HandlerInterceptorAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerInterceptor.class);
	
	@Autowired
	private static JedisCluster jedisCluster;
	
	/**
	 * 进入Controller方法前的拦截处理
	 * @return true继续controller的方法，false不再进入方法直接返回
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		request.setAttribute("startTime", System.currentTimeMillis());
		return true;
	}
	
	/**
	 * controller方法处理后，进入view前的拦截处理
	 * 可以在这里做一些公共处理
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
        long startTime = (Long)request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        LOGGER.info("{} used time: {}ms", request.getRequestURI(), (endTime - startTime));
	}
	
}

