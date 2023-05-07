package com.orangeandbronze.enlistment.domain;

import static org.junit.Assert.*;

import java.time.*;

import org.junit.*;

public class ScheduleTest {

	@Test
	public void testValueOf() {
		assertEquals(new Schedule(Days.MTH, LocalTime.of(8, 30), LocalTime.of(11, 30)), Schedule.valueOf("MTH 08:30-11:30"));
	}
	
	
	public static void main(String[] args) {
		System.out.println(new Schedule(Days.MTH, LocalTime.of(8, 30), LocalTime.of(14, 30)));
	}
}
