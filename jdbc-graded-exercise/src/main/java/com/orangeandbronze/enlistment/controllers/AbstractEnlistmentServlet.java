package com.orangeandbronze.enlistment.controllers;

import javax.servlet.*;
import javax.servlet.http.*;

import com.orangeandbronze.enlistment.service.*;

/** Initializes EnlistService field. **/
@SuppressWarnings("serial")
public abstract class AbstractEnlistmentServlet extends HttpServlet {

	static EnlistService service;

	@Override
	public void init() throws ServletException {
		super.init();
		if (service == null) {
			service = (EnlistService) getServletContext()
					.getAttribute(AttributeAndParamNames.ENLIST_SERVICE);
		}
	}
	
	

}
