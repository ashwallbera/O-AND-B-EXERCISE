package com.orangeandbronze.enlistment.domain;

import static org.apache.commons.lang3.Validate.*;

import java.util.*;

import org.apache.commons.lang3.*;

public class Student {

	public static final Student NONE = new Student(0);

	private final Integer studentNumber;
	private String firstname = "";
	private String lastname = "";
	private final Collection<Section> sections = new HashSet<>();

	public Student(int studentNumber) {
		this(studentNumber, "", "", Collections.emptyList());
	}
	
	public Student(int studentNumber, Collection<Section> enlistedSections) {
		this(studentNumber, "", "", enlistedSections);
	}
	
	public Student(int studentNumber, String firstname, String lastname) {
		this(studentNumber, firstname, lastname, Collections.emptyList());
	}

	public Student(int studentNumber, String firstname, String lastname, Collection<Section> enlistedSections) {
		isTrue(studentNumber >= 0, "studentNumber must be non-negative, was %d",
				studentNumber);
		this.studentNumber = studentNumber;
		if (StringUtils.isNotBlank(firstname)) {
			this.firstname = firstname;
		}
		if (StringUtils.isNotBlank(lastname)) {
			this.lastname = lastname;
		}
		if (enlistedSections != null && !enlistedSections.isEmpty()) {
			sections.addAll(enlistedSections);
		}
	}

	public void enlist(Section newSection) {
		notNull(newSection);
		validateSectionConflict(newSection);
		newSection.validateSectionCanAccommodateEnlistment();
		// two-way add:
		sections.add(newSection);
		newSection.enlist(this);
	}
	
	public void cancel(Section sectionToCancel) {
		sections.remove(sectionToCancel);
		sectionToCancel.cancel(this);
	}
	
	void checkIsEnlistedIn(Section section) {
		if (!sections.contains(section)) {
			throw new IllegalArgumentException("Student not enlisted in " + section);
		}
	}

	private void validateSectionConflict(Section newSection) {
		notNull(newSection);
		if (sections.contains(newSection)) {
			throw new DuplicateEnlistmentException(
					"enlisted more than once: " + newSection);
		}
		sections.forEach(
				currentSection -> currentSection.validateConflict(newSection));
	}

	public Collection<Section> getSections() {
		return new ArrayList<>(sections);
	}

	public Integer getStudentNumber() {
		return studentNumber;
	}

	public String getLastname() {
		return lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	@Override
	public String toString() {
		return firstname + " " + lastname + " SN#" + studentNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((studentNumber == null) ? 0 : studentNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (studentNumber == null) {
			if (other.studentNumber != null)
				return false;
		} else if (!studentNumber.equals(other.studentNumber))
			return false;
		return true;
	}
}
