package com.orangeandbronze.enlistment.dao;

import java.util.*;

import com.orangeandbronze.enlistment.domain.*;

public interface FacultyDAO {
	
	/** Retrieves faculty with associated sectionInfos. **/
	Faculty findBy(int facultyNumber);

	Collection<Map<String, String>> findAllFacultyInfos();

}
