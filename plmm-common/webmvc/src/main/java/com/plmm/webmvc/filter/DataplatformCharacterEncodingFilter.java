package com.plmm.webmvc.filter;
//package com.plmm.webmvc.filter;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletRequestWrapper;
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.web.filter.OncePerRequestFilter;
//
///**
// * 字符集设置
// */
//@ConditionalOnProperty(prefix="plmm.http.encoding", name="type", havingValue="plmm")
//@WebFilter(filterName="plmmCharacterEncodingFilter", urlPatterns = {"/*"})
//public class plmmCharacterEncodingFilter extends OncePerRequestFilter {
//	protected Logger logger = LoggerFactory.getLogger(getClass());
//	@Value("${plmm.http.encoding.otherurls:/test}")
//	private String patterns;
//	
//	@Value("${plmm.http.encoding.default:utf-8}")
//	private String encoding;
//	
//	@Value("${plmm.http.encoding.other:gbk}")
//	private String otherEncoding;
//	
//	@Value("${plmm.http.encoding.forceEncoding:false}")
//	private boolean forceEncoding;
//	
//	@Value("#{systemProperties['os.name'].toUpperCase()}")
//	private String osName;
//	
//	private static final String FORCEOTHERTRANSFER_PARAM = "forceOtherTransfer";
//	
//
//	/**
//	 * Set the encoding to use for requests. This encoding will be passed into a
//	 * {@link javax.servlet.http.HttpServletRequest#setCharacterEncoding} call.
//	 * <p>
//	 * Whether this encoding will override existing request encodings (and
//	 * whether it will be applied as default response encoding as well) depends
//	 * on the {@link #setForceEncoding "forceEncoding"} flag.
//	 */
//	public void setEncoding(String encoding) {
//		this.encoding = encoding;
//	}
//
//	/**
//	 * Set whether the configured {@link #setEncoding encoding} of this filter
//	 * is supposed to override existing request and response encodings.
//	 * <p>
//	 * Default is "false", i.e. do not modify the encoding if
//	 * {@link javax.servlet.http.HttpServletRequest#getCharacterEncoding()}
//	 * returns a non-null value. Switch this to "true" to enforce the specified
//	 * encoding in any case, applying it as default response encoding as well.
//	 * <p>
//	 * Note that the response encoding will only be set on Servlet 2.4+
//	 * containers, since Servlet 2.3 did not provide a facility for setting a
//	 * default response encoding.
//	 */
//	public void setForceEncoding(boolean forceEncoding) {
//		this.forceEncoding = forceEncoding;
//	}
//	
//	private String filter(HttpServletRequest request, String input, String encoding) {
//		String ret = input;
//		// 客户端请求参数值可能为(null)服务端过滤掉当null处理即可
//		if (input == null || input.trim().equals("(null)")) {
//			ret = null;
//			return ret;
//		}
//		try {
//			if(osName.indexOf("WINDOWS") == -1) {
//				ret = new String(input.getBytes("ISO8859-1"), encoding);
//			}
//			logger.info("bytes {} to {},  {} transfer to {}","ISO8859-1", encoding, input, new String(input.getBytes("ISO8859-1"), encoding));
//		} catch (UnsupportedEncodingException e) {
//			logger.warn("编码不支持", e);
//		}
//		return ret;
//	}
//	
//	private boolean matches(String uri) {
//		boolean flag = false;
//		for(String url : patterns.split(",")) {
//			if(uri.indexOf(url) != -1) {
//				flag = true;
//				break;
//			}
//		}
//		return flag;
//	}
//
//	@Override
//	protected void doFilterInternal(final HttpServletRequest request, HttpServletResponse response,
//			FilterChain filterChain) throws ServletException, IOException {
//		boolean isOther = matches(request.getRequestURI());
//		String encoding = isOther?this.otherEncoding:this.encoding;
//		logger.debug("urls {}, uri {}, isOther {}, encoding: {}", patterns, request.getRequestURI(), isOther, encoding);
//		// 设置request和response的编码格式
//		if (this.encoding != null && (this.forceEncoding || request.getCharacterEncoding() == null)) {
//			request.setCharacterEncoding(encoding);
//			if (this.forceEncoding) {
//				response.setCharacterEncoding(encoding);
//			}
//		}
//		if(isOther) {
//			// 对request中的参数进行编码格式的转换
//			filterChain.doFilter(new HttpServletRequestWrapper(request) {
//				@Override
//				public String getParameter(String name) {
//					String value = super.getParameter(name);
//					return filter(this, value, encoding);
//				}
//	
//				@Override
//				public String[] getParameterValues(String name) {
//					String[] values = super.getParameterValues(name);
//					if (values == null) {
//						return null;
//					}
//					for (int i = 0; i < values.length; i++) {
//						values[i] = filter(this, values[i], encoding);
//					}
//					return values;
//				}
//			}, response);
//		} else {
//			filterChain.doFilter(request, response);
//		}
//	}
//}
