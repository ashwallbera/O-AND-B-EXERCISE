package com.orangeandbronze.enlistment.service;

import com.orangeandbronze.enlistment.domain.*;

/** DTO for section information. **/
public class SectionInfo {
	public final String sectionId;
	public final String subjectId;
	public final String schedule;
	public final String roomName;
	public final String faculty;
	
	public static final SectionInfo NONE = new SectionInfo("NONE", "", "", "", "");

	public SectionInfo(String sectionId, String subjectId, String schedule,
			String roomName, String faculty) {
		this.sectionId = sectionId;
		this.subjectId = subjectId;
		this.schedule = schedule;
		this.roomName = roomName;
		this.faculty = faculty;
	}

	public SectionInfo(String sectionId, String subjectId, String schedule,
			String roomName) {
		this(sectionId, subjectId, schedule, roomName, "TBA");
	}

	SectionInfo(Section section) {
		this(section.getSectionId(), section.getSubject().getSubjectId(),
				section.getSchedule().toString(), section.getRoom().getName(),
				section.getFaculty().toString());
	}

	public String getSectionId() {
		return sectionId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public String getSchedule() {
		return schedule;
	}

	public String getRoomName() {
		return roomName;
	}

	public String getFaculty() {
		return faculty;
	}

	@Override
	public String toString() {
		return sectionId + " " + subjectId + " " + schedule + " " + roomName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((sectionId == null) ? 0 : sectionId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof SectionInfo)) {
			return false;
		}
		SectionInfo other = (SectionInfo) obj;
		if (sectionId == null) {
			if (other.sectionId != null) {
				return false;
			}
		} else if (!sectionId.equals(other.sectionId)) {
			return false;
		}
		return true;
	}
}
