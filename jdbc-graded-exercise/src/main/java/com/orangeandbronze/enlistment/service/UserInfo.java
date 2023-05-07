package com.orangeandbronze.enlistment.service;

import java.util.*;

import org.apache.commons.lang3.*;

public class UserInfo {
	public final String firstname;
	public final String lastname;
	
	UserInfo(String firstname, String lastname) {
		this.firstname = StringUtils.isBlank(firstname) ? "" : firstname;
		this.lastname = StringUtils.isBlank(lastname) ? "" : lastname; 
	}
	
	UserInfo(Map<String, String> infoMap) {
		this(infoMap.get("firstname"), infoMap.get("lastname"));
	}
}
