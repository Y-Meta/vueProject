package com.plmm.security.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.plmm.security.core.AuthenticationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.plmm.security.web.DefaultRedirectStrategy;
import com.plmm.security.web.RedirectStrategy;
import com.plmm.security.web.UrlUtils;

public class SimpleUrlAuthenticationFailureHandler implements AuthenticationFailureHandler {
	protected final Log logger = LogFactory.getLog(getClass());
	private String defaultFailureUrl;
	private boolean forwardToDestination = false;
	private boolean allowSessionCreation = true;
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	public SimpleUrlAuthenticationFailureHandler() {
	}

	public SimpleUrlAuthenticationFailureHandler(String defaultFailureUrl) {
		setDefaultFailureUrl(defaultFailureUrl);
	}

	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		if (this.defaultFailureUrl == null) {
			this.logger.debug("No failure URL set, sending 401 Unauthorized error");

			response.sendError(401, "Authentication Failed: " + exception.getMessage());
		} else {
			saveException(request, exception);

			if (this.forwardToDestination) {
				this.logger.debug("Forwarding to " + this.defaultFailureUrl);

				request.getRequestDispatcher(this.defaultFailureUrl).forward(request, response);
			} else {
				this.logger.debug("Redirecting to " + this.defaultFailureUrl);
				this.redirectStrategy.sendRedirect(request, response, this.defaultFailureUrl);
			}
		}
	}

	protected final void saveException(HttpServletRequest request, AuthenticationException exception) {
		if (this.forwardToDestination) {
			request.setAttribute("SPRING_SECURITY_LAST_EXCEPTION", exception);
		} else {
			HttpSession session = request.getSession(false);

			if ((session != null) || (this.allowSessionCreation))
				request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION", exception);
		}
	}

	public void setDefaultFailureUrl(String defaultFailureUrl) {
		Assert.isTrue(UrlUtils.isValidRedirectUrl(defaultFailureUrl),
				"'" + defaultFailureUrl + "' is not a valid redirect URL");

		this.defaultFailureUrl = defaultFailureUrl;
	}

	protected boolean isUseForward() {
		return this.forwardToDestination;
	}

	public void setUseForward(boolean forwardToDestination) {
		this.forwardToDestination = forwardToDestination;
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	protected RedirectStrategy getRedirectStrategy() {
		return this.redirectStrategy;
	}

	protected boolean isAllowSessionCreation() {
		return this.allowSessionCreation;
	}

	public void setAllowSessionCreation(boolean allowSessionCreation) {
		this.allowSessionCreation = allowSessionCreation;
	}
}
