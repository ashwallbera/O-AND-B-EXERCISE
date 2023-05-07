package com.orangeandbronze.enlistment.dao;

import com.orangeandbronze.enlistment.domain.*;

public interface EnlistmentDAO {

	/**
	 * Persists a new relationship between a Student and a Section.
	 */
	void create(Student student, Section section);

	/**
	 * Deletes the relationship between a Student and a Section.
	 */
	void delete(int studentNumber, String sectionId);

}
