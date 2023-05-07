package com.orangeandbronze.enlistment.controllers;

import java.util.*;

import com.orangeandbronze.enlistment.service.*;

public class MockEnlistService extends EnlistService {
	
	private final static Map<String, SectionInfo> sectionInfos = new HashMap<>();
	private final static HashMap<Integer, Set<SectionInfo>> enlistments = new HashMap<>();
	
	static {
		SectionInfo sec1 = new SectionInfo("sec1", "Math1", "MTH 8:30-10:00", "Room1");
		SectionInfo sec2 = new SectionInfo("sec2", "Com1", "TF 8:30-10:00", "Room2");
		SectionInfo sec3 = new SectionInfo("sec3", "Philo1", "WS 8:30-10:00", "Room1");
		SectionInfo sec4 = new SectionInfo("sec4", "History1", "MTH 11:30-1:00", "Room3");
		SectionInfo sec5 = new SectionInfo("sec5", "English1", "WS 2:30-4:00", "Room3");
		SectionInfo sec6 = new SectionInfo("sec6", "Filipino1", "TF 4:00-5:30", "Room2");
		sectionInfos.put(sec1.sectionId, sec1);
		sectionInfos.put(sec2.sectionId, sec2);
		sectionInfos.put(sec3.sectionId, sec3);
		sectionInfos.put(sec4.sectionId, sec4);
		sectionInfos.put(sec5.sectionId, sec5);
		sectionInfos.put(sec6.sectionId, sec6);
		enlistments.put(1, new HashSet<>());
		enlistments.put(2, new HashSet<>());
		enlistments.put(3, new HashSet<>());
		enlistments.put(4, new HashSet<>());
	}

	
	MockEnlistService() {
		super(null, null, null);
	}

	@Override
	public void enlist(int studentNo, String sectionId) {
		enlistments.get(studentNo).add(sectionInfos.get(sectionId));
	}

	@Override
	public void cancel(int studentNo, String sectionId) {
		enlistments.get(studentNo).remove(sectionInfos.get(sectionId));
	}

//	@Override
//	public Collection<SectionInfo> getSectionsAvailableForStudent(
//			int studentNo) {
//		 List<SectionInfo> DTOs = new ArrayList<>(sectionInfos.values());
//		 DTOs.removeAll(enlistments.get(studentNo));
//		 return DTOs;
//	}

//	@Override
//	public StudentAndSectionsInfo getStudentAndSectionsInfo(int studentNumber) {
//		// TODO Auto-generated method stub
//		throw new UnsupportedOperationException("This method has not yet been implemented.");
//	}
}
