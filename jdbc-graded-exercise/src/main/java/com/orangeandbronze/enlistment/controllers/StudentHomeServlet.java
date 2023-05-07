package com.orangeandbronze.enlistment.controllers;

import static com.orangeandbronze.enlistment.controllers.AttributeAndParamNames.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
@WebServlet("/student_home")
public class StudentHomeServlet extends AbstractEnlistmentServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(false);
		Integer studentNumber = (Integer) session.getAttribute(STUDENT_NUMBER);
		request.setAttribute("enlistedSectionInfos", service
				.getEnlistedSectionsOfStudent(studentNumber));
		request.setAttribute("availableSectionInfos", service
				.getSectionsAvailableForStudent(studentNumber));
		request.getRequestDispatcher("/WEB-INF/Enlistment.jsp").forward(request,
				response);
		session.removeAttribute(ENLISTMENT_EXCEPTION_MESSAGE);
	}

}
