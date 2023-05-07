package com.orangeandbronze.enlistment.dao;

import java.util.*;

import com.orangeandbronze.enlistment.domain.*;

public interface RoomDAO {
	
	/** Room info with sectionInfos.**/
	Room findBy(String roomName);
	
	Collection<String> findAllIds();
	
	Collection<Map<String, String>> findAllRoomInfos();

}
