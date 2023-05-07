package com.orangeandbronze.enlistment.controllers;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import com.orangeandbronze.enlistment.service.*;

@SuppressWarnings("serial")
@WebServlet("/admin_home")
public class AdminHomeServlet extends HttpServlet {

	private static CreateSectionService service;

	@Override
	public void init() throws ServletException {
		super.init();
		if (service == null) {
			service = (CreateSectionService) getServletContext().getAttribute(
					AttributeAndParamNames.CREATE_SECTION_SERVICE);
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		request.setAttribute("subjectIds", service.getSubjectIds());
		request.setAttribute("rooms", service.getRooms());
		request.setAttribute("faculties", service.getFaculty());
		request.setAttribute("sectionInfos", service.getSectionInfos());
		request.getRequestDispatcher("/WEB-INF/CreateSection.jsp")
				.forward(request, response);
		request.getSession().removeAttribute("errorMessage");
		request.getSession().removeAttribute("sectionInfo");
	}
}
