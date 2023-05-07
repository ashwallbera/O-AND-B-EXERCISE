package com.orangeandbronze.enlistment.service;

import java.util.*;

import com.orangeandbronze.enlistment.dao.*;
import com.orangeandbronze.enlistment.domain.*;

public class EnlistService extends AbstractService {

	private final SectionDAO sectionDao;
	private final StudentDAO studentDao;
	private final EnlistmentDAO enlistmentsDao;

	public EnlistService(SectionDAO sectionDao, StudentDAO studentDao,
			EnlistmentDAO enlistmentDao) {
		this.sectionDao = sectionDao;
		this.studentDao = studentDao;
		this.enlistmentsDao = enlistmentDao;
	}

	public void enlist(int studentNo, String sectionId) {
		retryIfDataIsStale(() -> {
			Student student = studentDao.findWithSectionsBy(studentNo);
			Section section = sectionDao.findBy(sectionId);
			student.enlist(section);
			enlistmentsDao.create(student, section);
		});
	}

	public void cancel(int studentNo, String sectionId) {
		enlistmentsDao.delete(studentNo, sectionId);
	}

	public Collection<Map<String,String>> getEnlistedSectionsOfStudent(
			int studentNumber) {
		return sectionDao.findSectionInfosByStudentNo(studentNumber);
	}

	public Collection<Map<String,String>> getSectionsAvailableForStudent(
			int studentNumber) {
		return sectionDao.findSectionInfosNotByStudentNo(studentNumber);
	}

}
