package com.orangeandbronze.enlistment.service;

import java.util.*;

import org.apache.commons.lang3.*;

import com.orangeandbronze.enlistment.dao.*;
import com.orangeandbronze.enlistment.domain.*;

public class CreateSectionService extends AbstractService {

	private final SectionDAO sectionDao;
	private final SubjectDAO subjectDao;
	private final RoomDAO roomDao;
	private final FacultyDAO facultyDao;

	public CreateSectionService(SectionDAO sectionDao, SubjectDAO subjectDao,
			RoomDAO roomDao, FacultyDAO facultyDao) {
		this.sectionDao = sectionDao;
		this.subjectDao = subjectDao;
		this.roomDao = roomDao;
		this.facultyDao = facultyDao;
	}

	public SectionInfo createSection(String sectionId, String subjectId,
			String schedule, String roomName, String facultyNumber) {
		return retryIfDataIsStale(() -> {
			Room room = StringUtils.isBlank(roomName) || roomName.trim().equals("TBA") ? Room.TBA
					: roomDao.findBy(roomName);
			Faculty faculty = StringUtils.isBlank(facultyNumber) || facultyNumber.trim().equalsIgnoreCase("TBA")
					? Faculty.TBA
					: facultyDao.findBy(Integer.parseInt(facultyNumber));
			// apply validations
			Section section = new Section(sectionId, new Subject(subjectId),
					Schedule.valueOf(schedule), room, faculty);
			sectionDao.create(section);
			return new SectionInfo(section);
		});
	}

	public Collection<Map<String, String>> getSectionInfos() {
		return sectionDao.findAllSectionInfos();
	}

	public Collection<String> getSubjectIds() {
		return subjectDao.findAllIds();
	}

	public Collection<Map<String,String>> getRooms() {
		return roomDao.findAllRoomInfos();
	}

	public Collection<Map<String,String>> getFaculty() {
		return facultyDao.findAllFacultyInfos();
	}
}
