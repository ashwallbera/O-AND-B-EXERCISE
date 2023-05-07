package com.orangeandbronze.enlistment.init;

import java.sql.*;

import javax.sql.*;

import com.orangeandbronze.enlistment.dao.*;
import com.orangeandbronze.enlistment.dao.jdbc.*;
import com.orangeandbronze.enlistment.service.*;

public class PopulateEnlistments {

	private static DataSource dataSource = DataSourceManager
			.getHikariPgDataSource();
	private static EnlistService service;
	static {
		SectionDAO sectionDao = new SectionDaoJdbc(dataSource);
		StudentDAO studentDao = new StudentDaoJdbc(dataSource);
		EnlistmentDAO enlistmentDao = new EnlistmentDaoJdbc(
				dataSource);
		service = new EnlistService(sectionDao, studentDao, enlistmentDao);
	}

	private static int queryInt(String sql) {
		int returnVal = 0;
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			rs.next();
			returnVal = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnVal;
	}

	private static String queryString(String sql) {
		String returnVal = "";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			rs.next();
			returnVal = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnVal;
	}

	public static void main(String[] args) {
		String sqlRandomStudentNo = "SELECT student_number FROM students WHERE student_number > 4  ORDER BY RANDOM() LIMIT 1";
		String sqlRandomSectionId = "SELECT section_id FROM sections WHERE section_id LIKE 'AA____' ORDER BY RANDOM() LIMIT 1";
		for (int i = 0; i < 996_000; i++) {
			try {
				int studentNo = queryInt(sqlRandomStudentNo);
				String sectionId = queryString(sqlRandomSectionId);
				service.enlist(studentNo, sectionId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
