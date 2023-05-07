package com.orangeandbronze.enlistment.controllers;

import static com.orangeandbronze.enlistment.controllers.AttributeAndParamNames.*;

import javax.servlet.annotation.*;

import com.orangeandbronze.enlistment.service.UserService.*;

@WebFilter("/student_home")
public class StudentLoginFilter extends AbstractLoginFilter {

	public StudentLoginFilter() {
		super(STUDENT_NUMBER, STUDENT_LOGIN_ERROR, UserType.STUDENT,
				"student number");
	}
}
