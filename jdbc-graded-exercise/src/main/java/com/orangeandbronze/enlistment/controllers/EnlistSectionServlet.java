package com.orangeandbronze.enlistment.controllers;

import javax.servlet.annotation.*;

@WebServlet("/enlist_section")
public class EnlistSectionServlet extends AbstractEnlistOrCancelServlet {

	@Override
	void enlistOrCancel(int studentNo, String sectionId) {
		service.enlist(studentNo, sectionId);
	}
	

}
