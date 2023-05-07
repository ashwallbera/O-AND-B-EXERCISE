package com.orangeandbronze.enlistment.dao;

import java.util.*;

public interface AdminDAO extends UserDAO {
	
	Map<String, String> findAdminInfoBy(int adminId);

}
