package com.orangeandbronze.enlistment.controllers;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import org.apache.commons.lang3.*;

import com.orangeandbronze.enlistment.domain.*;
import com.orangeandbronze.enlistment.service.*;

@SuppressWarnings("serial")
@WebServlet("/sections")
public class SectionsServlet extends HttpServlet {

	static CreateSectionService service;

	@Override
	public void init() throws ServletException {
		super.init();
		if (service == null) {
			service = (CreateSectionService) getServletContext().getAttribute(
					AttributeAndParamNames.CREATE_SECTION_SERVICE);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String sectionId = request.getParameter("section_id");
		String subjectId = request.getParameter("subject_id");
		String roomName = request.getParameter("room_name");
		String facultyNumber = request.getParameter("faculty_number");
		String days = request.getParameter("days");
		String period = request.getParameter("period");
		String schedule = StringUtils.isBlank(days)
				|| StringUtils.isBlank(period) ? "TBA" : days + " " + period;
		try {
			SectionInfo info = service.createSection(sectionId, subjectId, schedule, roomName,
					facultyNumber);
			request.getSession(false).setAttribute("sectionInfo", info);
		} catch (SectionCreationException | IllegalArgumentException e) {
			request.getSession(false).setAttribute("errorMessage", e.getMessage());
		}
		response.sendRedirect("admin_home");
	}

}
