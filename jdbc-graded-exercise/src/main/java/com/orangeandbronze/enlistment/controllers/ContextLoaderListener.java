package com.orangeandbronze.enlistment.controllers;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.sql.*;

import com.orangeandbronze.enlistment.dao.*;
import com.orangeandbronze.enlistment.dao.jdbc.*;
import com.orangeandbronze.enlistment.service.*;

@WebListener
public class ContextLoaderListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		DataSource ds = DataSourceManager.getDataSource();
		ServletContext context = sce.getServletContext();
		SectionDAO sectionDao = new SectionDaoJdbc(ds);
		StudentDAO studentDao = new StudentDaoJdbc(ds);
		EnlistmentDAO enlistmentDao = new EnlistmentDaoJdbc(ds);
		SubjectDAO subjectDao = new SubjectDaoJdbc(ds);
		RoomDAO roomDao = new RoomDaoJdbc(ds);
		FacultyDAO facultyDao = new FacultyDaoJdbc(ds);
		AdminDAO adminDao = new AdminDaoJdbc(ds);
		EnlistService enlistService = new EnlistService(sectionDao,
				studentDao, enlistmentDao);
		context.setAttribute(AttributeAndParamNames.ENLIST_SERVICE, enlistService);
		CreateSectionService createSectionService = new CreateSectionService(sectionDao, subjectDao, roomDao, facultyDao);
		context.setAttribute(AttributeAndParamNames.CREATE_SECTION_SERVICE, createSectionService);
		UserService userService = new UserService(adminDao, studentDao);
		context.setAttribute(AttributeAndParamNames.USER_SERVICE, userService);
	}

	public void contextDestroyed(ServletContextEvent sce) {
		// DO NOTHING
	}
}
