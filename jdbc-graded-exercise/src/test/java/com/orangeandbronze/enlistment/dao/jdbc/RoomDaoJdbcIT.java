package com.orangeandbronze.enlistment.dao.jdbc;

import static org.junit.Assert.*;

import java.util.*;
import java.util.stream.*;

import org.hamcrest.*;
import org.junit.*;

import com.orangeandbronze.enlistment.dao.*;
import com.orangeandbronze.enlistment.domain.*;

public class RoomDaoJdbcIT {

	private static RoomDAO dao = new RoomDaoJdbc(
			DataSourceManager.getDataSource());

	@Before
	public void init() throws Exception {
		DbUnitUtil.initData("RoomDataset.xml");
	}

	@Test
	public void testFindBy() throws Exception {
		final String roomName = "AVR1";
		final Room room = dao.findBy(roomName);
		assertEquals(roomName, room.getName());
		assertEquals(5, room.getCapacity());
		assertEquals(2, room.getVersion());
		List<String> sectionIds = room.getSections().stream()
				.map(Section::getSectionId).collect(Collectors.toList());
		assertThat(sectionIds,
				Matchers.containsInAnyOrder("MHW432", "MHY987", "TF888"));
	}

	@Test
	public void testFindAllIds() throws Exception {
		Collection<String> ids = dao.findAllIds();
		assertEquals(4, ids.size());
		assertThat(ids, Matchers.containsInAnyOrder("AVR1", "MATH105", "IP103",
				"AS113"));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testFindAllRoomInfos() throws Exception {
		Collection<Map<String, String>> infos = dao.findAllRoomInfos();
		assertEquals(4, infos.size());
		Map<String,String> rm1 = new HashMap<>();
		rm1.put("name", "AVR1");
		rm1.put("capacity", "5");
		Map<String,String> rm2 = new HashMap<>();
		rm2.put("name", "MATH105");
		rm2.put("capacity", "10");
		Map<String,String> rm3 = new HashMap<>();
		rm3.put("name", "IP103");
		rm3.put("capacity", "15");
		Map<String,String> rm4 = new HashMap<>();
		rm4.put("name", "AS113");
		rm4.put("capacity", "20");
		assertThat(infos, Matchers.containsInAnyOrder(rm1, rm2, rm3, rm4));
	}

}
