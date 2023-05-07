package com.orangeandbronze.enlistment.dao.jdbc;

import javax.sql.*;

import com.orangeandbronze.enlistment.dao.*;

/** Static *DaoJdbc instances for testing. **/
public interface JdbcDaoConstants {
	// static for single instance across all unit test objects
	DataSource ds = DataSourceManager.getDataSource();
	SectionDAO sectionDao = new SectionDaoJdbc(ds);
	StudentDAO studentDao = new StudentDaoJdbc(ds);
	EnlistmentDAO enlistmentDao = new EnlistmentDaoJdbc(ds);
	SubjectDAO subjectDao = new SubjectDaoJdbc(ds);
	RoomDAO roomDao = new RoomDaoJdbc(ds);
	FacultyDAO facultyDao = new FacultyDaoJdbc(ds);
}
