package com.orangeandbronze.enlistment.controllers;

import static com.orangeandbronze.enlistment.controllers.AttributeAndParamNames.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.lang3.*;

import com.orangeandbronze.enlistment.dao.*;
import com.orangeandbronze.enlistment.service.*;
import com.orangeandbronze.enlistment.service.UserService.*;

public abstract class AbstractLoginFilter implements Filter {

	static UserService service;
	
	private final String idAttributeName;
	private final String loginErrorAttributeName;
	private final UserType type;
	private final String idName;

	AbstractLoginFilter(String idAttributeName, String loginErrorAttributeName,
			UserType type, String idName) {
		this.idAttributeName = idAttributeName;
		this.loginErrorAttributeName = loginErrorAttributeName;
		this.type = type;
		this.idName = idName;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession();
		if (session.getAttribute(idAttributeName) == null) {
			String param = request.getParameter(idAttributeName);
			if (param == null) {
				forwardToLoginPage(request, response);
			} else {
				Integer id = null;
				try {
					id = Integer.parseInt(param);
					UserInfo info = service.getUserInfo(id, type);
					session.setAttribute(idAttributeName,
							Integer.parseInt(param));
					session.setAttribute("firstname", info.firstname);
					session.setAttribute("lastname", info.lastname);
					chain.doFilter(request, response);
				} catch (NumberFormatException e) {
					if (StringUtils.isNotBlank(param)) {
						request.setAttribute(loginErrorAttributeName,
								"You entered: '" + param
										+ "'. Please enter an integer.");
					}
					forwardToLoginPage(request, response);
				} catch (RecordNotFoundExeption e) {
					request.setAttribute(loginErrorAttributeName,
							"The " + idName + " '" + id
									+ "' was not found in our database.");
					forwardToLoginPage(request, response);
				}
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	void forwardToLoginPage(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		((HttpServletRequest) request).getRequestDispatcher("/index.jsp")
				.forward(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		if (service == null) {
			service = (UserService) fConfig.getServletContext()
					.getAttribute(USER_SERVICE);
		}
	}

	public void destroy() {
		// DO NOTHING
	}

}
