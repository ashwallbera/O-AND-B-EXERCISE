package com.orangeandbronze.enlistment.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.time.*;

import org.junit.*;

import com.orangeandbronze.enlistment.dao.*;
import com.orangeandbronze.enlistment.domain.*;

public class EnlistServiceTest {

	@Test
	public void enlistFirst() {
		SectionDAO sectionDao = mock(SectionDAO.class);
		StudentDAO studentDao = mock(StudentDAO.class);
		EnlistmentDAO enlistmentDao = mock(EnlistmentDAO.class);
		EnlistService service = new EnlistService(sectionDao, studentDao, enlistmentDao);

		int studentNo = 777;
		Student student = new Student(studentNo);
		when(studentDao.findWithSectionsBy(studentNo)).thenReturn(student);
		String sectionId = "TFX123";
		Section section = new Section(sectionId, new Subject("Math53"),
				new Schedule(Days.TF, LocalTime.of(8, 30), LocalTime.of(10, 0)),
				new Room("Lec1", 50));
		when(sectionDao.findBy(sectionId)).thenReturn(section);

		service.enlist(studentNo, sectionId);

		assertTrue(student.getSections().contains(section));
		verify(enlistmentDao).create(student, section);
	}

}
