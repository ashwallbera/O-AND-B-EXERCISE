package com.orangeandbronze.enlistment.dao;

import java.util.*;

import com.orangeandbronze.enlistment.domain.*;

public interface SectionDAO {

	/**
	 * @return The section that has Section ID @param sectionId . If none found,
	 *         returns Section.NONE.
	 */
	Section findBy(String sectionId);
	
	Collection<Map<String, String>> findAllSectionInfos();
	
	/** Saves a NEW section. **/
	void create(Section section);

	Collection<Map<String, String>> findSectionInfosByStudentNo(
			int studentNumber);

	Collection<Map<String, String>> findSectionInfosNotByStudentNo(
			int studentNumber);
}
