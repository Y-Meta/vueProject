package com.plmm.security.web;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DefaultRedirectStrategy implements RedirectStrategy {
	protected final Log logger = LogFactory.getLog(getClass());
	private boolean contextRelative;

	public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
		String redirectUrl = calculateRedirectUrl(request.getContextPath(), url);
		redirectUrl = response.encodeRedirectURL(redirectUrl);

		if (this.logger.isDebugEnabled()) {
			this.logger.debug("Redirecting to '" + redirectUrl + "'");
		}

		response.sendRedirect(redirectUrl);
	}

	private String calculateRedirectUrl(String contextPath, String url) {
		if (!UrlUtils.isAbsoluteUrl(url)) {
			if (this.contextRelative) {
				return url;
			}

			return contextPath + url;
		}

		if (!this.contextRelative) {
			return url;
		}

		url = url.substring(url.lastIndexOf("://") + 3);
		url = url.substring(url.indexOf(contextPath) + contextPath.length());

		if ((url.length() > 1) && (url.charAt(0) == '/')) {
			url = url.substring(1);
		}

		return url;
	}

	public void setContextRelative(boolean useRelativeContext) {
		this.contextRelative = useRelativeContext;
	}
}
