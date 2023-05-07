package com.orangeandbronze.enlistment.dao.jdbc;

import static com.orangeandbronze.enlistment.dao.jdbc.DbUnitUtil.*;
import static org.junit.Assert.*;

import java.sql.*;
import java.time.*;

import org.junit.*;

import com.orangeandbronze.enlistment.dao.*;
import com.orangeandbronze.enlistment.domain.*;

public class EnlistmentDaoJdbcIT implements JdbcDaoConstants {

	@Before
	public void setUp() throws Exception {
		initData("DefaultDataset.xml");
	}

	@After
	public void tearDown() throws Exception {
		try (Connection conn = DataSourceManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(
						"DELETE FROM enlistments WHERE student_number < 5")) {
			stmt.execute();
		}
	}

	private void assertEnlistmentExists(int studentNumber, String sectionId)
			throws Exception {
		assertEnlistmentCount(studentNumber, sectionId, 1);
	}

	private void assertEnlistmentDoesNotExist(int studentNumber, String sectionId)
			throws Exception {
		assertEnlistmentCount(studentNumber, sectionId, 0);
	}

	private void assertEnlistmentCount(int studentNumber, String sectionId,
			int expectedCount) throws Exception {
		String sql = "SELECT COUNT(*) FROM enlistments " + ""
				+ "WHERE student_number = ? AND section_id = ?";
		try (Connection conn = DataSourceManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, studentNumber);
			stmt.setString(2, sectionId);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			assertEquals(expectedCount, rs.getInt(1));
		}
	}

	private void assertVersionNumber(String sectionId,
			int expectedVersionNumber) throws Exception {
		String sql = "SELECT version FROM sections WHERE section_id = ?";
		try (Connection conn = DataSourceManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, sectionId);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			assertEquals(expectedVersionNumber, rs.getInt(1));
		}
	}

	@Test
	public void testCreate() throws Exception {
		final String sectionId = "NOSTUDENTS";
		final int versionOrig = 0;
		final Section sectionOrig = new Section(sectionId,
				new Subject("MATH11"), Schedule.valueOf("TF 08:30-10:00"),
				new Room("MATH105"), versionOrig);
		final int studentNumber = 1;
		final Student newStudent = new Student(studentNumber);
		enlistmentDao.create(newStudent, sectionOrig);
		assertEnlistmentExists(studentNumber, sectionId);
		assertVersionNumber(sectionId, versionOrig + 1);
	}

	@Test
	public void testDelete() throws Exception {
		final String sectionId = "HASSTUDENTS";
		final int studentNumber = 3;
		enlistmentDao.delete(studentNumber, sectionId);
		assertEnlistmentDoesNotExist(studentNumber, sectionId);
		assertVersionNumber(sectionId, 1);
	}

	@Test
	public void simultaneousCreate() throws Exception {
		Student student1 = new Student(1);
		Student student2 = new Student(2);
		Thread thread1 = new Thread() {
			public void run() {
				Section section = sectionDao.findBy("CAPACITY1"); // capacity 1
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				enlistmentDao.create(student1, section);
			}
		};
		Thread thread2 = new Thread() {
			Section section = sectionDao.findBy("CAPACITY1"); // capacity 1

			public void run() {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				enlistmentDao.create(student2, section);
			}
		};

		thread1.start();
		thread2.start();
		thread1.join();
		thread2.join();
		String sql = "SELECT COUNT(*) FROM enlistments WHERE SECTION_ID = '"
				+ "CAPACITY1" + "'";
		ResultSet rs = DataSourceManager.getDataSource().getConnection()
				.prepareStatement(sql).executeQuery();
		rs.next();
		assertEquals(1, rs.getInt(1));
	}
}
