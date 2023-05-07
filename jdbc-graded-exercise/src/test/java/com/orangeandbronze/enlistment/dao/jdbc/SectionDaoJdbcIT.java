package com.orangeandbronze.enlistment.dao.jdbc;

import static org.junit.Assert.*;

import java.util.*;
import java.util.stream.*;

import org.hamcrest.*;
import org.hamcrest.beans.*;
import org.junit.*;

import com.orangeandbronze.enlistment.dao.*;
import com.orangeandbronze.enlistment.domain.*;

public class SectionDaoJdbcIT {

	private static SectionDAO dao = new SectionDaoJdbc(
			DataSourceManager.getDataSource());

	@Before
	public void init() throws Exception {
		DbUnitUtil.initData("SectionDataset.xml");
	}

	@Test
	public void testFindBy() throws Exception {
		final String sectionId = "TFX555";
		final Section section = dao.findBy(sectionId);
		assertEquals(sectionId, section.getSectionId());
		assertEquals("PHILO1", section.getSubject().getSubjectId());
		assertEquals("TF 10:00-11:30", section.getSchedule().toString());
		assertThat(section.getFaculty(),
				SamePropertyValuesAs.samePropertyValuesAs(new Faculty(2, "Buzz",
						"Lightyear", Arrays.asList(section))));
		assertEquals("AS113", section.getRoom().getName());
		assertEquals(0, section.getVersion());
		Collection<Student> students = section.getStudents();
		assertEquals(2, students.size());
		Collection<Integer> studentNumbers = students.stream()
				.map(Student::getStudentNumber).collect(Collectors.toList());
		assertThat(studentNumbers, Matchers.containsInAnyOrder(3, 4));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindAllSectionInfos() {
		Map<String, String> info1 = new HashMap<>();
		info1.put("sectionId", "MHX123");
		info1.put("subjectId", "MATH11");
		info1.put("schedule", "MTH 08:30-10:00");
		info1.put("roomName", "TBA");
		info1.put("faculty", "Optimus Prime FN#1");
		Map<String, String> info2 = new HashMap<>();
		info2.put("sectionId", "TFX555");
		info2.put("subjectId", "PHILO1");
		info2.put("schedule", "TF 10:00-11:30");
		info2.put("roomName", "AS113");
		info2.put("faculty", "Buzz Lightyear FN#2");
		Map<String, String> info3 = new HashMap<>();
		info3.put("sectionId", "MHW432");
		info3.put("subjectId", "COM1");
		info3.put("schedule", "MTH 08:30-10:00");
		info3.put("roomName", "AVR1");
		info3.put("faculty", "TBA");
		Collection<Map<String, String>> infos = dao.findAllSectionInfos();
		assertThat(infos, Matchers.containsInAnyOrder(info1, info2, info3));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindSectionInfosByStudentNo() {
		Map<String, String> info1 = new HashMap<>();
		info1.put("sectionId", "MHX123");
		info1.put("subjectId", "MATH11");
		info1.put("schedule", "MTH 08:30-10:00");
		info1.put("roomName", "TBA");
		info1.put("faculty", "Optimus Prime FN#1");
		Map<String, String> info2 = new HashMap<>();
		info2.put("sectionId", "TFX555");
		info2.put("subjectId", "PHILO1");
		info2.put("schedule", "TF 10:00-11:30");
		info2.put("roomName", "AS113");
		info2.put("faculty", "Buzz Lightyear FN#2");
		Collection<Map<String, String>> infos = dao.findSectionInfosByStudentNo(3);
		assertThat(infos, Matchers.containsInAnyOrder(info1, info2));
	}

	 @SuppressWarnings("unchecked")
	@Test
	 public void testFindSectionInfosNotByStudentNo() {
		 Map<String, String> info1 = new HashMap<>();
			info1.put("sectionId", "MHX123");
			info1.put("subjectId", "MATH11");
			info1.put("schedule", "MTH 08:30-10:00");
			info1.put("roomName", "TBA");
			info1.put("faculty", "Optimus Prime FN#1");
			Map<String, String> info2 = new HashMap<>();
			info2.put("sectionId", "MHW432");
			info2.put("subjectId", "COM1");
			info2.put("schedule", "MTH 08:30-10:00");
			info2.put("roomName", "AVR1");
			info2.put("faculty", "TBA");
			Collection<Map<String, String>> infos = dao.findSectionInfosNotByStudentNo(4);
			assertThat(infos, Matchers.containsInAnyOrder(info1, info2));
	 }

}
