package com.orangeandbronze.enlistment.dao.jdbc;

import static org.junit.Assert.*;

import java.util.*;
import java.util.stream.*;

import org.apache.poi.util.*;
import org.hamcrest.*;
import org.junit.*;

import com.orangeandbronze.enlistment.dao.*;
import com.orangeandbronze.enlistment.domain.*;

public class FacultyDaoJdbcIT {

	private static FacultyDAO dao = new FacultyDaoJdbc(
			DataSourceManager.getDataSource());

	@Test
	public void testFindBy() throws Exception {
		DbUnitUtil.initData("DefaultDataset.xml");
		Faculty faculty = dao.findBy(2);
		assertEquals("Buzz", faculty.getFirstname());
		assertEquals("Lightyear", faculty.getLastname());
		Collection<Section> sections = faculty.getSections();
		assertEquals(2, sections.size());
		Collection<String> sectionIds = sections.stream()
				.map(Section::getSectionId).collect(Collectors.toList());
		assertThat(sectionIds,
				Matchers.containsInAnyOrder("TFX555", "CAPACITY1"));
		Collection<Schedule> schedules = sections.stream()
				.map(Section::getSchedule).collect(Collectors.toList());
		assertThat(schedules,
				Matchers.containsInAnyOrder(Schedule.valueOf("TF 10:00-11:30"),
						Schedule.valueOf("MTH 11:30-13:00")));

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindAllFacultyInfos() throws Exception {
		DbUnitUtil.initData("FacultyDataset.xml");
		Collection<Map<String,String>> infos = dao.findAllFacultyInfos();
		assertEquals(3, infos.size());
		Map<String,String> fac1 = new HashMap<>();
		fac1.put("facultyNumber", "1");
		fac1.put("firstname", "Optimus");
		fac1.put("lastname", "Prime");
		Map<String,String> fac2 = new HashMap<>();
		fac2.put("facultyNumber", "2");
		fac2.put("firstname", "Buzz");
		fac2.put("lastname", "Lightyear");
		Map<String,String> fac3 = new HashMap<>();
		fac3.put("facultyNumber", "3");
		fac3.put("firstname", "Clark");
		fac3.put("lastname", "Kent");	
		assertThat(infos, Matchers.containsInAnyOrder(fac1, fac2, fac3));
	}

}
