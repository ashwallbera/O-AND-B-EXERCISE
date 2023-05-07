package com.orangeandbronze.enlistment.dao.jdbc;

import static org.junit.Assert.*;

import org.junit.*;
import java.util.*;

import static com.orangeandbronze.enlistment.dao.jdbc.DbUnitUtil.*;

public class AdminDaoJdbcIT {

	@Test
	public void testFindUserInfobById() throws Exception {
		initData("AdminsDataset.xml");
		Map<String,String> expected = new HashMap<>();
		expected.put("firstname", "Charles");
		expected.put("lastname", "Xavier");
		assertEquals(expected, new AdminDaoJdbc(DataSourceManager.getDataSource()).findUserInfobById(3));
	}
}
