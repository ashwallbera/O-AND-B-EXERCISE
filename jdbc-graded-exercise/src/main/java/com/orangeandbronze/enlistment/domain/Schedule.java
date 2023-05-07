package com.orangeandbronze.enlistment.domain;

import static org.apache.commons.lang3.Validate.*;

import java.time.*;

import org.apache.commons.lang3.*;

public class Schedule {
	public static final Schedule TBA = new Schedule();
	private final Days days;
	private final LocalTime start;
	private final LocalTime end;

	private Schedule() {
		days = null;
		start = null;
		end = null;
	}

	public Schedule(Days days, LocalTime start, LocalTime end) {
		notNull(days);
		notNull(start);
		notNull(end);
		this.days = days;
		this.start = start;
		this.end = end;
	}

	Days getDays() {
		return days;
	}

	LocalTime getStart() {
		return start;
	}

	LocalTime getEnd() {
		return end;
	}

	void notOverlappingWith(Schedule other) {
		if (isOverlappingWith(other)) {
			throw new ScheduleConflictException(
					"This schedule: " + this + " Other schedule: " + other);
		}
	}

	boolean isOverlappingWith(Schedule other) {
		if (this == TBA || other == TBA) {
			return false;
		}		
		return (this.days == other.days) && (this.start.isBefore(other.end)
				&& this.end.isAfter(other.start));
	}

	public static Schedule valueOf(String scheduleString) {
		if (StringUtils.trimToEmpty(scheduleString).equals("")
				|| StringUtils.trimToEmpty(scheduleString)
						.equalsIgnoreCase("TBA")
				|| StringUtils.trimToEmpty(scheduleString).equals("NULL")) {
			return TBA;
		} else {
			String[] tokens = scheduleString.split(" |-");
			return new Schedule(Days.valueOf(tokens[0]),
					LocalTime.parse(tokens[1]), LocalTime.parse(tokens[2]));
		}

	}

	@Override
	public String toString() {
		return this == TBA ? "TBA" : days + " " + start + "-" + end;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((days == null) ? 0 : days.hashCode());
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Schedule))
			return false;
		Schedule other = (Schedule) obj;
		if (days != other.days)
			return false;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
	}

}
