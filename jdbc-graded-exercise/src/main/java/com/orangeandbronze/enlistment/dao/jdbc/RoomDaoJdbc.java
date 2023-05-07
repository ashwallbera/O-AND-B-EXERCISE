package com.orangeandbronze.enlistment.dao.jdbc;


import com.orangeandbronze.enlistment.dao.DataAccessException;
import com.orangeandbronze.enlistment.domain.*;
import com.orangeandbronze.enlistment.dao.RoomDAO;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RoomDaoJdbc extends AbstractDaoJdbc implements RoomDAO {
    private DataSource ds;
    public RoomDaoJdbc(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public Room findBy(String roomName) {
        // TODO replace this with an exception?
        Room room = Room.TBA;

        try (PreparedStatement stmt = preparedStatement(ds, "queries/FindRoomById.sql")) {
            Collection<Section> sections = new ArrayList<>();

            boolean found = false;
            int capacity = -1;

            stmt.setString(1, roomName);

            try (ResultSet rs = stmt.executeQuery()) {
                while(rs.next()) {
                    if(!found) found = true;

                    if(capacity < 0) {
                        capacity = rs.getInt("capacity");
                    }

                    if(StringUtils.isNotBlank(rs.getString("section_id"))) {
                        String sectionId = rs.getString("section_id");
                        Subject subject = new Subject(rs.getString("subject_id"));
                        Schedule schedule = Schedule.valueOf(rs.getString("schedule"));
                        Room _room = new Room(roomName);
                        Faculty faculty = new Faculty(rs.getInt("faculty_number"));

                        sections.add(new Section(sectionId, subject, schedule, _room, faculty));
                    }

                    room = new Room(roomName, capacity, sections, rs.getInt("version"));
                }
            }
            stmt.getConnection().close();
        } catch(SQLException ex) {
            throw new DataAccessException(
                    String.format("SQL Query failed: Problem retrieving room data for id %s", roomName),
                    ex
            );
        }

        return room;
    }


    @Override
    public Collection<String> findAllIds() {
        Collection<String> ids = new ArrayList<>();
        try (PreparedStatement stmt = preparedStatement(ds, "queries/FindRoomByName.sql");
             ResultSet rs = stmt.executeQuery()){
            while (rs.next()) {
                String id = rs.getString("room_name");
                ids.add(id);
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Error finding all room ids "+ex.getMessage(), ex);
        }
        return ids;
    }

    @Override
    public Collection<Map<String, String>> findAllRoomInfos() {
        Collection<Map<String, String>> roomInfos = new ArrayList<>();
        try (PreparedStatement stmt = preparedStatement(ds, "queries/GetAllRooms.sql");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Map<String, String> roomInfo = new HashMap<>();
                roomInfo.put("name", rs.getString("room_name"));
                roomInfo.put("capacity", rs.getInt("capacity") + "");
                roomInfos.add(roomInfo);
            }
            stmt.getConnection().close();
        } catch (SQLException e) {
            throw new DataAccessException("Error finding all room infos: "+e.getMessage(), e);
        }
        return roomInfos;
    }

    public static void main(String[] args) {
        DataSource dataSource = DataSourceManager.getDataSource();
        RoomDAO roomDao = new RoomDaoJdbc(dataSource);

        Collection<Map<String, String>> roomInfos = roomDao.findAllRoomInfos();
        for (Map<String, String> roomInfo : roomInfos) {
            System.out.println(roomInfo);
        }
    }

}