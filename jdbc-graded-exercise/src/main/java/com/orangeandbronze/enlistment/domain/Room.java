package com.orangeandbronze.enlistment.domain;

import static org.apache.commons.lang3.Validate.*;

import java.util.*;

public class Room {
	public static final Room TBA = new Room("TBA", 0);

	private final String name;
	private final Integer capacity;
	private final Collection<Section> sections = new HashSet<>();
	private final Integer version;

	public Room(String roomName, int capacity, Collection<Section> sections, 
			Integer version) {
		matchesPattern(roomName, "[a-zA-Z0-9]+",
				"roomName should be alphanumeric, was %s", roomName);
		isTrue(capacity >= 0, "capacity must be non-negative, was %d",
				capacity);
		this.name = roomName;
		if (sections != null) {
			this.sections.addAll(sections);
		}
		this.capacity = capacity;
		this.version = version;
	}

	/** No sectionInfos & Version = 0. **/
	public Room(String roomName, int capacity) {
		this(roomName, capacity, null, 0);
	}
	
	public Room(String roomName, int capacity, Collection<Section> sections) {
		this(roomName, capacity, sections, null);
	}

	public Room(String roomName, int capacity, int version) {
		this(roomName, capacity, null, version);
	}
	
	/** Default capacity **/
	public Room(String roomName) {
		this(roomName, 10, null, 0);
	}

	void addSection(Section newSection) {
		if (sections.contains(newSection)) {
			return;
		}
		synchronized (sections) {
			validateScheduleAvailability(newSection);
			sections.add(newSection);
		}
	}

	private void validateScheduleAvailability(Section newSection) {
		for (Section currentSection : sections) {
			if (currentSection.equals(newSection)) {
				return;
			}
			if (currentSection.hasScheduleOverlapWith(newSection)) {
				throw new RoomScheduleConflictException("Room " + this
						+ " has a schedule overlap between new section "
						+ newSection + " with schedule "
						+ newSection.getSchedule() + " and current section "
						+ currentSection + " with schedule "
						+ currentSection.getSchedule() + ".");
			}
		}
	}

	public String getName() {
		return name;
	}

	public int getCapacity() {
		return capacity;
	}

	public Collection<Section> getSections() {
		return new ArrayList<>(sections);
	}

	public int getVersion() {
		return version;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Room other = (Room) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
