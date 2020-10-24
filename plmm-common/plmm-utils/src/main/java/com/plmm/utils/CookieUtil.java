package com.plmm.utils;

import org.springframework.util.Assert;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CookieUtil {
	public static final String COOKIE_PAHT = "/cookie";

	/**
	 * 写入一个Cookie
	 *
	 * @param response
	 * @param name
	 * @param path
	 * @param value
	 * @param maxAge
	 */
	public static void addCookie(HttpServletResponse response, String name, String value, String path, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		if (path != null && !"".equals(path)) {
			cookie.setPath(path);
		}
		if (maxAge > 0) {
			cookie.setMaxAge(maxAge);
		}
		response.addCookie(cookie);
	}

	/**
	 * 根据名称获得Cookie
	 *
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getCookieByName(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = readCookieMap(request);
		if (cookieMap != null && cookieMap.containsKey(name)) {
			Cookie cookie = cookieMap.get(name);
			return cookie.getValue();
		} else {
			return null;
		}
	}

	/**
	 * 读取全部Cookie
	 *
	 * @param request
	 * @return
	 */
	protected static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (int i = 0; i < cookies.length; i++) {
				cookieMap.put(cookies[i].getName(), cookies[i]);
			}
		}
		return cookieMap;
	}

	public static void removeCookie(HttpServletResponse res, String name) {
		Cookie cookie = new Cookie(name, null);
		cookie.setMaxAge(0);
		cookie.setPath(COOKIE_PAHT);
		res.addCookie(cookie);
	}

	/**
	 * 清除Cookie
	 *
	 * @param req
	 * @param res
	 * @param name
	 */
	public static void remove(HttpServletRequest req, HttpServletResponse res, String name) {
		String cookieValue = getCookieByName(req, name);
		if (null != cookieValue) {
			Cookie cookie = new Cookie(name, null);
			cookie.setMaxAge(0);
			cookie.setPath(COOKIE_PAHT);
			res.addCookie(cookie);
		}
	}

	/**
	 * 获得cookie
	 *
	 * @param request
	 *            HttpServletRequest
	 * @param name
	 *            cookie name
	 * @return if exist return cookie, else return null.
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Assert.notNull(request);
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie c : cookies) {
				if (c.getName().equals(name)) {
					return c;
				}
			}
		}
		return null;
	}

	/**
	 * 设置HttpOnly Cookie
	 * 
	 * @param response
	 *            HTTP响应
	 * @param cookie
	 *            Cookie对象
	 * @param isHTTPOnly
	 *            是否为HttpOnly
	 */
	public static void addCookie(HttpServletResponse response, Cookie cookie, boolean isHttpOnly) {
		String name = cookie.getName();// Cookie名称
		String value = cookie.getValue();// Cookie值
		int maxAge = cookie.getMaxAge();// 最大生存时间(毫秒,0代表删除,-1代表与浏览器会话一致)
		String path = cookie.getPath();// 路径
		String domain = cookie.getDomain();// 域
		boolean isSecure = cookie.getSecure();// 是否为安全协议信息

		StringBuilder buffer = new StringBuilder();

		buffer.append(name).append("=").append(value).append(";");

		if (maxAge == 0) {
			buffer.append("Expires=Thu Jan 01 08:00:00 CST 1970;");
		} else if (maxAge > 0) {
			buffer.append("Max-Age=").append(maxAge).append(";");
		}

		if (domain != null) {
			buffer.append("domain=").append(domain).append(";");
		}

		if (path != null) {
			buffer.append("path=").append(path).append(";");
		}

		if (isSecure) {
			buffer.append("secure;");
		}

		if (isHttpOnly) {
			buffer.append("HTTPOnly;");
		}

		response.addHeader("Set-Cookie", buffer.toString());
	}
}
