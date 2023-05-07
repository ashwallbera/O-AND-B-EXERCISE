package com.orangeandbronze.enlistment.service;

import static org.junit.Assert.*;

import java.sql.*;

import javax.sql.*;

import org.dbunit.dataset.*;
import org.junit.*;

import com.orangeandbronze.enlistment.dao.jdbc.*;
import com.orangeandbronze.enlistment.domain.*;

/** Integration test - accesses database **/
public class EnlistServiceIT implements JdbcDaoConstants {

	// made static so that one instance across multiple unit test objects
	private final static EnlistService service = new EnlistService(sectionDao,
			studentDao, enlistmentDao);

	@Before
	public void cleanInsert() throws Exception {
		DbUnitUtil.initData("DefaultDataset.xml");
	}

	@After
	public void tearDown() throws Exception {
		try (Connection conn = DataSourceManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(
						"DELETE FROM enlistments WHERE student_number < 5")) {
			stmt.execute();
		}
	}

	@Test
	public void enlistFirstSection() throws Exception {
		final int STUDENT_NO = 1;
		final String SECTION_ID = "MHX123";
		service.enlist(STUDENT_NO, SECTION_ID);
		ITable table = DbUnitUtil.getIDatabaseConnection().createQueryTable(
				"result",
				"SELECT section_id FROM enlistments WHERE student_number = "
						+ STUDENT_NO);
		assertEquals(SECTION_ID, table.getValue(0, "section_id"));
	}

	@Test
	public void cancelSection() throws Exception {
		final int STUDENT_NO = 4;
		final String SECTION_ID = "HASSTUDENTS";
		service.cancel(STUDENT_NO, SECTION_ID);
		ITable table = DbUnitUtil.getIDatabaseConnection().createQueryTable(
				"result",
				"SELECT section_id FROM enlistments WHERE section_id = '"
						+ SECTION_ID + "'");
		assertEquals(1, table.getRowCount());
	}

	@Test(expected = ScheduleConflictException.class)
	public void enlistSectionSameScheduleAsCurrentSection() throws Exception {
		final int STUDENT_NO = 1;
		final String SECTION_ID_1 = "MHX123";
		final String SECTION_ID_2 = "MHW432";
		service.enlist(STUDENT_NO, SECTION_ID_1);
		service.enlist(STUDENT_NO, SECTION_ID_2);
	}

	@Test(expected = SameSubjectException.class)
	public void enlistSectionSameSubject() throws Exception {
		final int STUDENT_NO = 1;
		final String SECTION_ID_1 = "MHW432"; // COM 1
		final String SECTION_ID_2 = "MHY987"; // COM 1
		service.enlist(STUDENT_NO, SECTION_ID_1);
		service.enlist(STUDENT_NO, SECTION_ID_2);
	}

	@Test
	public void simultaneousEnlistment() throws Exception {
		final int STUDENT_NO1 = 1;
		final int STUDENT_NO2 = 2;
		final String SEC_ID = "CAPACITY1"; // section with capacity 1
		Thread thread1 = new Thread() {
			public void run() {
				service.enlist(STUDENT_NO1, SEC_ID);
			}
		};
		Thread thread2 = new Thread() {
			public void run() {
				service.enlist(STUDENT_NO2, SEC_ID);
			}
		};
		thread1.start();
		thread2.start();
		thread1.join();
		thread2.join();
		String sql = "SELECT COUNT(*) FROM enlistments WHERE SECTION_ID = '"
				+ SEC_ID + "'";
		ResultSet rs = DataSourceManager.getDataSource().getConnection()
				.prepareStatement(sql).executeQuery();
		rs.next();
		assertEquals(1, rs.getInt(1));
	}
}
