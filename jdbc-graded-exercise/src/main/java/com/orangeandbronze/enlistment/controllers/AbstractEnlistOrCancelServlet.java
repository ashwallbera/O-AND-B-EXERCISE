package com.orangeandbronze.enlistment.controllers;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.lang3.*;

import com.orangeandbronze.enlistment.domain.*;

public abstract class AbstractEnlistOrCancelServlet
		extends AbstractEnlistmentServlet {

	@Override
	// Template Method
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String sectionId = request
				.getParameter(AttributeAndParamNames.SECTION_ID);
		Validate.notBlank(sectionId, "secton_id parameter cannot be blank, was '%s'", sectionId);
		int studentNo = (int) request.getSession()
				.getAttribute(AttributeAndParamNames.STUDENT_NUMBER);
		try {
			enlistOrCancel(studentNo, sectionId);
		} catch (EnlistmentException e) {
			request.getSession().setAttribute(
					AttributeAndParamNames.ENLISTMENT_EXCEPTION_MESSAGE,
					e.getMessage());
		}
		response.sendRedirect("student_home");
	}

	abstract void enlistOrCancel(int studentNo, String sectionId);

}
