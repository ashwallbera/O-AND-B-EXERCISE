package com.orangeandbronze.enlistment.controllers;

import static com.orangeandbronze.enlistment.controllers.AttributeAndParamNames.*;

import javax.servlet.annotation.*;

import com.orangeandbronze.enlistment.service.UserService.*;

@WebFilter("/admin_home")
public class AdminLoginFilter extends AbstractLoginFilter {
	
	public AdminLoginFilter() {
		super(ADMIN_ID, ADMIN_LOGIN_ERROR, UserType.ADMIN, "admin ID");
	}

}
