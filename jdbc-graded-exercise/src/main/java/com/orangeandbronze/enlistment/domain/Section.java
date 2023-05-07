package com.orangeandbronze.enlistment.domain;

import static org.apache.commons.lang3.Validate.*;

import java.util.*;

import org.apache.commons.lang3.*;

public class Section {

	public static final Section NONE = new Section("NONE", Subject.NONE,
			Schedule.TBA, Room.TBA);

	private final String sectionId;
	private Schedule schedule = Schedule.TBA;
	private final Subject subject;
	private Room room = Room.TBA;
	private Faculty faculty = Faculty.TBA;
	private final Collection<Student> students = new HashSet<>();
	private final Integer version; // version recorded in database

	/**
	 * @param version
	 *            is the version recorded in the database
	 **/
	public Section(String sectionId, Subject subject, Schedule schedule,
			Room room, Faculty faculty, Collection<Student> enlistedStudents,
			Integer version) {
		isTrue(StringUtils.isAlphanumeric(sectionId),
				"sectionId should be alphanumeric, was %s", sectionId);
		notNull(schedule);
		notNull(subject);
		notNull(room);
		notNull(faculty);
		this.sectionId = sectionId;
		this.schedule = schedule;
		this.subject = subject;
		if (!room.TBA.equals(room)) {
			this.room = room;
			room.addSection(this);
		}
		if (!Faculty.TBA.equals(faculty)) {
			this.faculty = faculty;
			faculty.addSection(this);
		}
		students.addAll(enlistedStudents);
		this.version = version;
	}

	public Section(String sectionId, Subject subject, Schedule schedule,
			Room room, Integer version) {
		this(sectionId, subject, schedule, room, Faculty.TBA,
				new ArrayList<>(0), version);
	}

	public Section(String sectionId, Subject subject, Schedule schedule,
			Room room) {
		this(sectionId, subject, schedule, room, Faculty.TBA,
				new ArrayList<>(0), 0);
	}

	public Section(String sectionId, Subject subject, Schedule schedule,
			Room room, Collection<Student> enlistedStudents) {
		this(sectionId, subject, schedule, room, Faculty.TBA, enlistedStudents,
				0);
	}

	public Section(String sectionId, Subject subject, Schedule schedule,
			Room room, Faculty faculty) {
		this(sectionId, subject, schedule, room, faculty, new ArrayList<>(0),
				0);
	}

	public Section(String sectionId, Subject subject, Schedule schedule,
			Room room, Faculty faculty, int version) {
		this(sectionId, subject, schedule, room, faculty, new ArrayList<>(0),
				version);
	}

	void enlist(Student student) {
		student.checkIsEnlistedIn(this);
		validateSectionCanAccommodateEnlistment();
		students.add(student);
	}

	void cancel(Student student) {
		students.remove(student);
	}

	void validateConflict(Section otherSection) {
		this.schedule.notOverlappingWith(otherSection.schedule);
		if (subject.equals(otherSection.subject)) {
			throw new SameSubjectException("Section " + otherSection.sectionId
					+ " with subject " + subject.getSubjectId()
					+ " has same subject as currently enlisted section "
					+ this.sectionId);
		}
	}

	void validateSectionCanAccommodateEnlistment() {
		if (students.size() >= room.getCapacity()) {
			throw new RoomCapacityReachedException("Capacity Reached - enlistments: "
					+ students.size() + " capacity: " + room.getCapacity());
		}
	}

	boolean hasScheduleOverlapWith(Section other) {
		return this.schedule.isOverlappingWith(other.schedule);
	}

	public Collection<Student> getStudents() {
		return new ArrayList<>(students);
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public Subject getSubject() {
		return subject;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Room getRoom() {
		return room;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public int getVersion() {
		return version;
	}

	@Override
	public String toString() {
		return sectionId;
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Section))
			return false;
		Section other = (Section) obj;
		if (sectionId == null) {
			if (other.sectionId != null)
				return false;
		} else if (!sectionId.equals(other.sectionId))
			return false;
		return true;
	}

}
