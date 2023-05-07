package com.orangeandbronze.enlistment.service;

import com.orangeandbronze.enlistment.dao.*;

public class UserService {
	
	public enum UserType {
		STUDENT, ADMIN;
		UserDAO dao;
		UserInfo getUserInfo(int id) {
			return new UserInfo(dao.findUserInfobById(id));
		}
	}
		
	public UserService(AdminDAO adminDao, StudentDAO studentDao) {
		UserType.STUDENT.dao = studentDao;
		UserType.ADMIN.dao = adminDao;
	}

	public UserInfo getUserInfo(int id, UserType type) {
		return type.getUserInfo(id);
	}
}
