package com.plmm.webmvc.util;
//package com.plmm.webmvc.util;
//
//import com.plmm.localcache.service.ICacheContextService;
//import com.plmm.security.core.Authentication;
//import com.plmm.security.core.userdetails.UserDetails;
//import com.plmm.utils.SerializeUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
///**
// * redis工具类
// */
//@Component
//public class RedisUtil {
//
//    private static final String TOKEN = "plmm-token";
//    private static final String AUTH = "authentication";
//
//    @Autowired
//    private ICacheContextService cacheContextService;
//    /**
//     * 获取登录用户名
//     * @return 用户名
//     */
//    public String getLoginName(){
//    	Authentication auth = getAuth();
//    	if(auth==null)
//    		return null;
//        UserDetails user = (UserDetails)getAuth().getPrincipal();
//        return user.getLoginName();
//    }
//
//    /**
//     * 获取登录用户code
//     * @return code
//     */
//    public String getUserCode(){
//        UserDetails user = (UserDetails)getAuth().getPrincipal();
//        return user.getUsercode();
//    }
//    
//    /**
//     * 获取登录用户
//     * @return 用户
//     */
//    public Authentication getAuth(String token){
//    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        Object obj = null;
//        byte[] key =cacheContextService.get(token.getBytes());
//        if(key==null){
//        	Authentication auth = (Authentication)request.getSession().getAttribute("authentication");
//        	return auth;
//        }
//        HttpSession session = (HttpSession)SerializeUtils.deSerialize(key);
//        obj=session.getAttribute(AUTH);
//        Authentication auth = (Authentication)obj;
//        return auth;
//    }
//
//    /**
//     * 获取登录用户
//     * @return 用户
//     */
//    public Authentication getAuth(){
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        Object obj = null;
//        if(request.getCookies()!=null) {
//            for(Cookie cookie : request.getCookies()) {
//                if(TOKEN.equals(cookie.getName())){
//                    String id = cookie.getValue();
//                    byte[] key =cacheContextService.get(id.getBytes());
//                    if(key==null){
//                    	Authentication auth = (Authentication)request.getSession().getAttribute("authentication");
//                    	return auth;
//                    }
//                    HttpSession session = (HttpSession)SerializeUtils.deSerialize(key);
//                    obj=session.getAttribute(AUTH);
//                    break;
//                }
//            }
//        }
//        Authentication auth = (Authentication)obj;
//        return auth;
//    }
//
//	public ICacheContextService getCacheContextService() {
//		return cacheContextService;
//	}
//
//	public void setCacheContextService(ICacheContextService cacheContextService) {
//		this.cacheContextService = cacheContextService;
//	}
//}
