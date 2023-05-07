package com.orangeandbronze.enlistment.dao.jdbc;

import static com.orangeandbronze.enlistment.dao.jdbc.DbUnitUtil.*;
import static com.orangeandbronze.enlistment.domain.Days.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
/*import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.beans.SamePropertyValuesAs.*;*/
import static org.junit.Assert.*;

import java.sql.*;
import java.time.*;
import java.util.*;

import org.hamcrest.*;
import org.junit.*;

import com.orangeandbronze.enlistment.dao.*;
import com.orangeandbronze.enlistment.domain.*;

public class StudentDaoJdbcIT {

	private static StudentDAO dao = new StudentDaoJdbc(
			DataSourceManager.getDataSource());

	@Test
	public void findStudent() throws Exception {
		initData("StudentNoSections.xml");
		Integer studentNumber = 1;
		Student actualStudent = dao.findWithSectionsBy(studentNumber);
		assertEquals(studentNumber, actualStudent.getStudentNumber());
	}

	@Test
	public void findStudentThatHasSections() throws Exception {
		initData("StudentHasSections.xml");
		Student actualStudent = dao.findWithSectionsBy(1);
		Collection<Section> actualSections = actualStudent.getSections();
		assertThat(actualSections, Matchers.containsInAnyOrder(
				new Section("MHX123", Subject.NONE, Schedule.TBA, Room.TBA),
				new Section("TFX555", Subject.NONE, Schedule.TBA, Room.TBA),
				new Section("MHW432", Subject.NONE, Schedule.TBA, Room.TBA)));
	}

	@Test
	public void findStudentThatHasSectionsWithSubjectSchedulesAndRooms()
			throws Exception {
		initData("StudentHasSections.xml");
		Collection<Section> sections = dao.findWithSectionsBy(1).getSections();
		Section sec1 = new Section("MHX123", new Subject("MATH11"),
				new Schedule(MTH, LocalTime.of(8, 30), LocalTime.of(10, 0)),
				new Room("MATH105", 10));
		Section sec2 = new Section("TFX555", new Subject("PHILO1"),
				new Schedule(TF, LocalTime.of(10, 0), LocalTime.of(11, 30)),
				new Room("AS113", 10));
		Section sec3 = new Section("MHW432", new Subject("COM1"),
				new Schedule(MTH, LocalTime.of(13, 0), LocalTime.of(14, 30)),
				new Room("AVR1", 10));
		
		// does not check room capacity
		assertThat(sections, containsInAnyOrder(samePropertyValuesAs(sec1),
				samePropertyValuesAs(sec2), samePropertyValuesAs(sec3)));
		
		// verbose but is able to check for room capacity
		sections.forEach(section -> {
			if (section.getSectionId().equals("MHX123")) {
				assertEquals(new Subject("MATH11"), section.getSubject());
				assertEquals(new Schedule(MTH, LocalTime.of(8, 30),
						LocalTime.of(10, 0)), section.getSchedule());
				Room room = section.getRoom();
				assertEquals(new Room("MATH105", 10), room);
				assertEquals(10, room.getCapacity());
			}
			if (section.getSectionId().equals("TFX555")) {
				assertEquals(new Subject("PHILO1"), section.getSubject());
				assertEquals(new Schedule(TF, LocalTime.of(10, 0),
						LocalTime.of(11, 30)), section.getSchedule());
				Room room = section.getRoom();
				assertEquals(new Room("AS113", 10), room);
				assertEquals(10, room.getCapacity());
			}
			if (section.getSectionId().equals("MHW432")) {
				assertEquals(new Subject("COM1"), section.getSubject());
				assertEquals(new Schedule(MTH, LocalTime.of(13, 0),
						LocalTime.of(14, 30)), section.getSchedule());
				Room room = section.getRoom();
				assertEquals(new Room("AVR1", 10), room);
				assertEquals(10, room.getCapacity());
			}
		});
		
		

		
		
	}
}
