package com.orangeandbronze.enlistment.domain;

import static org.junit.Assert.*;

import java.time.*;

import org.junit.*;

public class SectionTest {
	
	private Faculty defaultFaculty = new Faculty(1);
	private Room defaultRoom = new Room("defaultRoom", 0);

	@Test
	public void newSectionsNoConflict() {		
		Section sec1 = new Section(
				"SEC1", new Subject("COM1"), new Schedule(Days.MTH,
						LocalTime.of(10, 0), LocalTime.of(11, 0)),
				defaultRoom, defaultFaculty);
		Section sec2 = new Section(
				"SEC1", new Subject("COM1"), new Schedule(Days.MTH,
						LocalTime.of(11, 0), LocalTime.of(12, 0)),
				defaultRoom, defaultFaculty);
		assertNotNull(sec1);
		assertNotNull(sec2);
	}
	
	@Test(expected = RoomScheduleConflictException.class)
	public void newSectionsWithOverlappingRoomSchedules() {
		Section sec1 = new Section(
				"SEC1", new Subject("COM1"), new Schedule(Days.MTH,
						LocalTime.of(10, 0), LocalTime.of(11, 30)),
				defaultRoom, new Faculty(1));
		Section sec2 = new Section(
				"SEC2", new Subject("COM1"), new Schedule(Days.MTH,
						LocalTime.of(11, 00), LocalTime.of(12, 30)),
				defaultRoom, new Faculty(2));
	}
	
	@Test(expected = FacultyScheduleConflictException.class) 
	public void newSectionsWithOverlappingFacultySchedules() {
		Section sec1 = new Section(
				"SEC1", new Subject("COM1"), new Schedule(Days.MTH,
						LocalTime.of(10, 0), LocalTime.of(11, 30)),
				new Room("Room1", 0), defaultFaculty);
		Section sec2 = new Section(
				"SEC2", new Subject("COM1"), new Schedule(Days.MTH,
						LocalTime.of(11, 00), LocalTime.of(12, 30)),
				new Room("Room2", 0), defaultFaculty);
	}
	
	
	
	

}
