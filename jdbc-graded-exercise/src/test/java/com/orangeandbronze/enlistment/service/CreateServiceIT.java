package com.orangeandbronze.enlistment.service;

import static org.junit.Assert.*;

import java.sql.*;

import org.junit.*;

import com.orangeandbronze.enlistment.dao.*;
import com.orangeandbronze.enlistment.dao.jdbc.*;
import com.orangeandbronze.enlistment.domain.*;

public class CreateServiceIT implements JdbcDaoConstants {

	private static final String SECTION_ID_2 = "DEF456";
	private static final String SECTION_ID_1 = "ABC123";
	private final static CreateSectionService service = new CreateSectionService(
			sectionDao, subjectDao, roomDao, facultyDao);

	@Before
	public void setUp() throws Exception {
		DbUnitUtil.initData("DefaultDataset.xml");
		try (Connection conn = DataSourceManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(
						"DELETE FROM sections WHERE section_id = ?")) {
			stmt.setString(1, SECTION_ID_1);
			stmt.addBatch();
			stmt.setString(1, SECTION_ID_2);
			stmt.addBatch();
			stmt.executeBatch();
		}
	}

	@Test
	public void newSectionsNoConflict() throws Exception {
		service.createSection(SECTION_ID_1, "KAS1", "TF 08:30-10:00", "AS113",
				"1");
		service.createSection(SECTION_ID_2, "KAS1", "WS 08:30-10:00", "AS113",
				"1");
		ResultSet rs = ds.getConnection().createStatement().executeQuery(
				"SELECT COUNT(DISTINCT(SECTION_ID)) FROM sections WHERE SECTION_ID = 'ABC123' OR SECTION_ID = 'DEF456'");
		rs.next();
		assertEquals(2, rs.getInt(1));
	}

	@Test(expected = RoomScheduleConflictException.class)
	public void newSectionsWithOverlappingRoomSchedules() {
		// schedule TF 10:100 - 11:30 occupied by TFX555
		service.createSection(SECTION_ID_1, "KAS1", "TF 10:00-11:30", "AS113",
				"1");
	}

	@Test(expected = FacultyScheduleConflictException.class)
	public void newSectionsWithOverlappingFacultySchedules() {
		service.createSection(SECTION_ID_1, "KAS1", "TF 08:30-10:00", "TBA",
				"1");
		service.createSection(SECTION_ID_2, "KAS1", "TF 08:30-10:00", "TBA",
				"1");
	}

	@Test(expected = DataAccessException.class)
	public void newSectionSameID() {
		service.createSection("MHX123", "KAS1", "WS 10:00-11:30", "AVR2", "1");
	}

}
