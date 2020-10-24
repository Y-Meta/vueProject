package com.plmm.webmvc.filter;

import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 *  安全字符过滤 
 */

@Order(Integer.MIN_VALUE + 21)
@WebFilter(filterName="xssFilter", urlPatterns = {"/*"})
public class XssFilter extends OncePerRequestFilter{
	private static final Set<String> urlSet = new HashSet<String>(){
		{
			add("/tpSysMenu/save");
			add("/tpSysMenu/update");
			add("/tpSysConf/save");
			add("/tpSysConf/update");
			add("/menu/saveTpSysMenu");
			add("/menu/updateTpSysMenu");
		}
	};
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String url = request.getRequestURL().toString();
		for (String s: urlSet) {
			if(url.contains(s)){
				filterChain.doFilter(request,response);
				return;
			}
		}
		filterChain.doFilter(new XssHttpServletRequestWrapper(request),response);
	}


}
