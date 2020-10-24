package com.plmm.webmvc.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class PathMatcher {
	
	List<RequestMatcher> matcherList;
	
	public void setMatcherList(List<RequestMatcher> matcherList) {
		this.matcherList = matcherList;
	}
	
	public boolean isStatic(HttpServletRequest request) {
		boolean flag = false;
		for(RequestMatcher rm : matcherList) {
			if (rm.matches(request)) {
				flag = true;
				break;
			}
		}
		return flag;
	}
}
