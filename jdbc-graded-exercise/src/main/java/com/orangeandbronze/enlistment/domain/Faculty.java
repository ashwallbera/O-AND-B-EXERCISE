package com.orangeandbronze.enlistment.domain;

import java.util.*;

import org.apache.commons.lang3.*;

public class Faculty {

	public static final Faculty TBA = new Faculty(0);

	private final Integer facultyNumber;
	private String firstname = "";
	private String lastname = "";
	private final Collection<Section> sections = new HashSet<>();
	private final Integer version;

	public Faculty(Integer facultyNumber, String firstname, String lastname,
			Collection<Section> sections, Integer version) {
		this.facultyNumber = facultyNumber;
		if (StringUtils.isNotBlank(firstname)) {
			this.firstname = firstname;
		}
		if (StringUtils.isNotBlank(lastname)) {
			this.lastname = lastname;
		}
		this.sections.addAll(sections);
		this.version = version;
	}
	
	public Faculty(Integer facultyNumber, String firstname, String lastname) {
		this(facultyNumber, firstname, lastname, Collections.emptyList(), null);
	}
	
	public Faculty(Integer facultyNumber, String firstname, String lastname, Collection<Section> sections) {
		this(facultyNumber, firstname, lastname, sections, null);
	}

	public Faculty(Integer facultyNumber, Collection<Section> sections,
			Integer version) {
		this(facultyNumber, "", "", sections, version);
	}
	
	public Faculty(Integer facultyNumber, Collection<Section> sections) {
		this(facultyNumber, "", "", sections, null);
	}

	public Faculty(Integer facultyNumber, Integer version) {
		this(facultyNumber, Collections.emptyList(), version);
	}

	public Faculty(Integer facultyNumber) {
		this(facultyNumber, Collections.emptyList(), null);
	}

	void addSection(Section newSection) {
		synchronized (sections) {
			validateScheduleAvailability(newSection);
			sections.add(newSection);
		}
	}

	private void validateScheduleAvailability(Section newSection) {
		for (Section currentSection : sections) {
			if (currentSection.hasScheduleOverlapWith(newSection)) {
				throw new FacultyScheduleConflictException("Faculty " + this
						+ " has a schedule overlap between new section "
						+ newSection + " with schedule "
						+ newSection.getSchedule() + " and current section "
						+ currentSection + " with schedule "
						+ currentSection.getSchedule() + ".");
			}
		}
	}

	public Integer getFacultyNumber() {
		return facultyNumber;
	}

	public Collection<Section> getSections() {
		return new ArrayList<>(sections);
	}

	public Integer getVersion() {
		return version;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	@Override
	public String toString() {
		return this == TBA ? "TBA"
				: firstname + " " + lastname + " " + " FN#" + facultyNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((facultyNumber == null) ? 0 : facultyNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Faculty))
			return false;
		Faculty other = (Faculty) obj;
		if (facultyNumber == null) {
			if (other.facultyNumber != null)
				return false;
		} else if (!facultyNumber.equals(other.facultyNumber))
			return false;
		return true;
	}
}
