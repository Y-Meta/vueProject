package com.plmm.security.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.plmm.security.core.Authentication;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.plmm.security.web.DefaultRedirectStrategy;
import com.plmm.security.web.RedirectStrategy;
import com.plmm.security.web.UrlUtils;

public abstract class AbstractAuthenticationTargetUrlRequestHandler {
	protected final Log logger = LogFactory.getLog(getClass());
	private String targetUrlParameter = null;
	private String defaultTargetUrl = "/";
	private boolean alwaysUseDefaultTargetUrl = false;
	private boolean useReferer = false;
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		String targetUrl = determineTargetUrl(request, response);

		if (response.isCommitted()) {
			this.logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);

			return;
		}

		this.redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
		if (isAlwaysUseDefaultTargetUrl()) {
			return this.defaultTargetUrl;
		}

		String targetUrl = null;

		if (this.targetUrlParameter != null) {
			targetUrl = request.getParameter(this.targetUrlParameter);

			if (StringUtils.hasText(targetUrl)) {
				this.logger.debug("Found targetUrlParameter in request: " + targetUrl);

				return targetUrl;
			}
		}

		if ((this.useReferer) && (!StringUtils.hasLength(targetUrl))) {
			targetUrl = request.getHeader("Referer");
			this.logger.debug("Using Referer header: " + targetUrl);
		}

		if (!StringUtils.hasText(targetUrl)) {
			targetUrl = this.defaultTargetUrl;
			this.logger.debug("Using default Url: " + targetUrl);
		}

		return targetUrl;
	}

	protected final String getDefaultTargetUrl() {
		return this.defaultTargetUrl;
	}

	public void setDefaultTargetUrl(String defaultTargetUrl) {
		Assert.isTrue(UrlUtils.isValidRedirectUrl(defaultTargetUrl),
				"defaultTarget must start with '/' or with 'http(s)'");

		this.defaultTargetUrl = defaultTargetUrl;
	}

	public void setAlwaysUseDefaultTargetUrl(boolean alwaysUseDefaultTargetUrl) {
		this.alwaysUseDefaultTargetUrl = alwaysUseDefaultTargetUrl;
	}

	protected boolean isAlwaysUseDefaultTargetUrl() {
		return this.alwaysUseDefaultTargetUrl;
	}

	public void setTargetUrlParameter(String targetUrlParameter) {
		if (targetUrlParameter != null) {
			Assert.hasText(targetUrlParameter, "targetUrlParameter cannot be empty");
		}
		this.targetUrlParameter = targetUrlParameter;
	}

	protected String getTargetUrlParameter() {
		return this.targetUrlParameter;
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	protected RedirectStrategy getRedirectStrategy() {
		return this.redirectStrategy;
	}

	public void setUseReferer(boolean useReferer) {
		this.useReferer = useReferer;
	}
}
