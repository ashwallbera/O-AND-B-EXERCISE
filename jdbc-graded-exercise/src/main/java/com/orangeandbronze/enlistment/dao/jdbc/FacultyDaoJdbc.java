package com.orangeandbronze.enlistment.dao.jdbc;

import com.orangeandbronze.enlistment.dao.FacultyDAO;
import com.orangeandbronze.enlistment.domain.*;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;


public class FacultyDaoJdbc extends AbstractDaoJdbc implements FacultyDAO {

    private DataSource ds;

    public FacultyDaoJdbc(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public Faculty findBy(int facultyNumber) {
        Faculty faculty = null;
        try (Connection conn = ds.getConnection();
             PreparedStatement stmt = preparedStatement(ds, "queries/FindFacultyById.sql")) {
            stmt.setInt(1, facultyNumber);

            String firstName = "";
            String lastName = "";
            Collection<Section> sections = new ArrayList<>();

            boolean found = false;

            stmt.setInt(1, facultyNumber);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    if(!found) found = true;

                    if(StringUtils.isBlank(lastName) || StringUtils.isBlank(firstName)) {
                        firstName = rs.getString("firstname");
                        lastName = rs.getString("lastname");
                    }

                    if(StringUtils.isNotBlank(rs.getString("section_id"))) {
                        String sectionId = rs.getString("section_id");
                        Subject subject = new Subject(rs.getString("subject_id"));
                        Schedule schedule = Schedule.valueOf(rs.getString("schedule"));
                        Room room = new Room(rs.getString("room_name"), rs.getInt("capacity"));
                        Faculty _sFaculty = new Faculty(facultyNumber);

                        sections.add(new Section(sectionId, subject, schedule, room, _sFaculty));
                    }
                }

                faculty = new Faculty(facultyNumber, firstName, lastName, sections);
                stmt.getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error finding faculty: "+ex.getMessage(), ex);
        }

         return faculty;
    }


    @Override
    public Collection<Map<String, String>> findAllFacultyInfos() {
        try (PreparedStatement stmt = preparedStatement(ds,
                "queries/FindFacultyInfo.sql")) {
            ResultSet resultSet = stmt.executeQuery();
            List<Map<String, String>> result = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, String> faculty = new HashMap<>();
                faculty.put("facultyNumber", resultSet.getString("faculty_number"));
                faculty.put("firstname", resultSet.getString("firstname"));
                faculty.put("lastname", resultSet.getString("lastname"));
                result.add(faculty);
            }
            stmt.getConnection().close();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all faculty infos: "+e.getMessage(), e);
        }
    }
}