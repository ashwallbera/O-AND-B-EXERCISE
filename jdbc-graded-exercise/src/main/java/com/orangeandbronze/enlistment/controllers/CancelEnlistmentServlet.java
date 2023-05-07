package com.orangeandbronze.enlistment.controllers;

import javax.servlet.annotation.*;

@SuppressWarnings("serial")
@WebServlet("/cancel_enlistment")
public class CancelEnlistmentServlet extends AbstractEnlistOrCancelServlet {

    @Override
	void enlistOrCancel(int studentNo, String sectionId) {
		service.cancel(studentNo, sectionId);
	}

}
