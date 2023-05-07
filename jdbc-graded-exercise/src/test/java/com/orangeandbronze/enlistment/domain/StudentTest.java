package com.orangeandbronze.enlistment.domain;

import static org.junit.Assert.*;

import java.time.*;
import java.util.*;

import org.junit.Test;

import static com.orangeandbronze.enlistment.domain.Days.*;

public class StudentTest {

	@Test
	public void enlistFirstSection() {
		Student student = new Student(7);
		Section section = new Section("MHX123", new Subject("Math53"),
				new Schedule(MTH, LocalTime.of(8, 30), LocalTime.of(10, 0)),
				new Room("Lec1", 50));
		student.enlist(section);
		assertTrue(student.getSections().contains(section));
	}

	@Test(expected = ScheduleConflictException.class)
	public void enlistSectionSameScheduleAsCurrentSection() {
		Student student = new Student(7);
		Section section1 = new Section("MHX123", new Subject("Math53"),
				new Schedule(MTH, LocalTime.of(8, 30), LocalTime.of(10, 0)),
				new Room("AVR2", 100));
		student.enlist(section1);
		Section section2 = new Section("MHX456", new Subject("Philo1"),
				new Schedule(MTH, LocalTime.of(9, 30), LocalTime.of(11, 0)),
				new Room("Lec1", 50));
		student.enlist(section2);
	}

	@Test(expected = SameSubjectException.class)
	public void enlistSectionSameSubject() {
		Student student = new Student(7);
		Section section1 = new Section("MHX123", new Subject("Math53"),
				new Schedule(MTH, LocalTime.of(8, 30), LocalTime.of(10, 0)),
				new Room("Lec1", 50));
		student.enlist(section1);
		Section section2 = new Section("MHX456", new Subject("Math53"),
				new Schedule(TF, LocalTime.of(8, 30), LocalTime.of(10, 0)),
				new Room("AVR2", 100));
		student.enlist(section2);
	}

	@Test(expected = RoomCapacityReachedException.class)
	public void enlistSectionAtCapacity() {
		Student student = new Student(7);
		Section section = new Section("MHX123", new Subject("Math53"),
				new Schedule(MTH, LocalTime.of(8, 30), LocalTime.of(10, 0)),
				new Room("Lec1", 0));
		student.enlist(section);
	}

	@Test
	public void cancelEnlistment() {
		Section toCancel = new Section("CANCEL", new Subject("CANCEL"),
				new Schedule(MTH, LocalTime.of(14, 30), LocalTime.of(16, 0)),
				new Room("Lec1", 50));
		Student student = new Student(5, Arrays.asList(
				new Section("MHX123", new Subject("Math53"),
						new Schedule(MTH, LocalTime.of(8, 30),
								LocalTime.of(10, 0)),
						new Room("Lec1", 50)),
				new Section("A", new Subject("A"),
						new Schedule(WS, LocalTime.of(8, 30),
								LocalTime.of(10, 0)),
						new Room("Lec1", 50)),
				toCancel));
		student.cancel(toCancel);
		assertFalse(student.getSections().contains(toCancel));
	}

}
