package com.orangeandbronze.enlistment.dao.jdbc;

import com.orangeandbronze.enlistment.dao.DataAccessException;
import com.orangeandbronze.enlistment.dao.SectionDAO;
import com.orangeandbronze.enlistment.domain.*;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SectionDaoJdbc extends AbstractDaoJdbc implements SectionDAO {

    private DataSource dataSource;
    public SectionDaoJdbc(DataSource dataSource){
        this.dataSource = dataSource;
    }


    @Override
    public Section findBy(String sectionId) {
        Section section = null;

        try (PreparedStatement stmt = preparedStatement(dataSource, "queries/FindSectionById.sql")) {

            stmt.setString(1, sectionId);
            Collection<Student> students = new ArrayList<>();
            Subject subject = null;
            Schedule schedule = null;
            Faculty faculty = null;
            Room room = null;
            int version = -1;
            boolean found = false;
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                if(!found) {
                    subject = new Subject(rs.getString("subject_id"));
                    schedule = Schedule.valueOf(rs.getString("schedule"));
                    faculty = rs.getInt("faculty_number") > 0 ? new Faculty(rs.getInt("faculty_number"), rs.getString("firstname"), rs.getString("lastname")) : Faculty.TBA;
                    room = rs.getString("room_name") != null ? new Room(rs.getString("room_name"), rs.getInt("capacity")) : Room.TBA;
                    version = rs.getInt("version");

                    found = true;
                }
                students.add(new Student(rs.getInt("student_number")));
            }

            if(found)
                section = new Section(sectionId, subject, schedule, room, faculty, students, version);
        } catch (SQLException ex) {
            throw new DataAccessException("Error finding section with ID "+ex.getMessage() + sectionId, ex);
        }

        if (section == null) {
            throw new DataAccessException("Section with ID " + sectionId + " not found. ");
        }

        return section;

    }

    @Override
    public Collection<Map<String, String>> findAllSectionInfos() {
        Collection<Map<String, String>> sections = new ArrayList<>();

        try (PreparedStatement stmt = preparedStatement(dataSource, "queries/FindAllSections.sql")) {
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Map<String, String> section = new HashMap<>();
                section.put("sectionId", rs.getString("section_id"));
                section.put("subjectId", rs.getString("subject_id"));
                section.put("schedule", rs.getString("schedule"));

                if(!StringUtils.isBlank(rs.getString("room_name"))) {
                    section.put("roomName", rs.getString("room_name"));
                } else {
                    section.put("roomName", "TBA");
                }
                if(!StringUtils.isBlank(rs.getString("faculty_number"))) {
                    section.put(
                            "faculty",
                            rs.getString("firstname") + " " + rs.getString("lastname") +
                                    " FN#" + rs.getInt("faculty_number"));
                } else {
                    section.put("faculty", "TBA");
                }
                sections.add(section);
            }
        } catch (SQLException e) {
            throw new DataAccessException(
                    "There was a problem retrieving Section entries "+e.getMessage(), e);
        }
        return sections;
    }

    @Override
    public Collection<Map<String, String>> findSectionInfosByStudentNo(int studentNumber) {
        Collection<Map<String, String>> sections = new ArrayList<>();

        try (PreparedStatement stmt = preparedStatement(dataSource, "queries/FindSectionsByStudentNo.sql")) {
            stmt.setInt(1, studentNumber);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Map<String, String> section = new HashMap<>();
                section.put("sectionId", rs.getString("section_id"));
                section.put("subjectId", rs.getString("subject_id"));
                section.put("schedule", rs.getString("schedule"));

                if(!StringUtils.isBlank(rs.getString("room_name"))) {
                    section.put("roomName", rs.getString("room_name"));
                } else {
                    section.put("roomName", "TBA");
                }
                if(!StringUtils.isBlank(rs.getString("faculty_number"))) {
                    section.put(
                            "faculty",
                            rs.getString("firstname") + " " + rs.getString("lastname") +
                                    " FN#" + rs.getInt("faculty_number"));
                } else {
                    section.put("faculty", "TBA");
                }
                sections.add(section);
            }
            stmt.getConnection().close();
        } catch (SQLException e) {
            throw new DataAccessException(
                    "There was a problem retrieving Section entries", e);
        }
        return sections;
    }

    @Override
    public Collection<Map<String, String>> findSectionInfosNotByStudentNo(int studentNumber) {
        Collection<Map<String, String>> sections = new ArrayList<>();

        try (PreparedStatement stmt = preparedStatement(dataSource, "queries/FindSectionsNotByStudentNo.sql")) {
            stmt.setInt(1, studentNumber);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Map<String, String> section = new HashMap<>();
                section.put("sectionId", rs.getString("section_id"));
                section.put("subjectId", rs.getString("subject_id"));
                section.put("schedule", rs.getString("schedule"));

                if(!StringUtils.isBlank(rs.getString("room_name"))) {
                    section.put("roomName", rs.getString("room_name"));
                } else {
                    section.put("roomName", "TBA");
                }
                if(!StringUtils.isBlank(rs.getString("faculty_number"))) {
                    section.put(
                            "faculty",
                            rs.getString("firstname") + " " + rs.getString("lastname") +
                                    " FN#" + rs.getInt("faculty_number"));
                } else {
                    section.put("faculty", "TBA");
                }
                sections.add(section);
            }
            stmt.getConnection().close();
        } catch (SQLException e) {
            throw new DataAccessException(
                    "There was a problem retrieving Section entries", e);
        }
        return sections;
    }


    @Override
    public void create(Section section) {
        try (PreparedStatement stmt = preparedStatement(dataSource, "InsertSection.sql")) {
            stmt.setString(1, section.getSectionId());
            stmt.setString(2, section.getSubject().getSubjectId());
            stmt.setString(3, section.getSchedule().toString());
            stmt.setString(4, section.getRoom().getName());
            stmt.setInt(5, section.getFaculty().getFacultyNumber());

            stmt.execute();
            stmt.getConnection().close();
        }
        catch (FacultyScheduleConflictException e){
            throw new FacultyScheduleConflictException("There was a problem retrieving Section entries: "+e.getMessage());
        }
        catch (DataAccessException e) {
            throw new DataAccessException("There was a problem retrieving Section entries: "+e.getMessage(), e);
        }
        catch (SQLException e){

        }
    }



    private PreparedStatement getPreparedStatement(Connection conn, String sqlFile) {
        String sql = getSql(sqlFile);

        try {
            return conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new DataAccessException("Problem preparing statement with SQL: " + sql, e);
        }
    }
    private String getSql(String sqlFile) {
        if (!sqlCache.containsKey(sqlFile)) {
            try (Reader reader = new BufferedReader(new InputStreamReader(
                    getClass().getClassLoader().getResourceAsStream(sqlFile)))) {
                StringBuilder bldr = new StringBuilder();
                int i = 0;
                while ((i = reader.read()) > 0) {
                    bldr.append((char) i);
                }
                sqlCache.put(sqlFile, bldr.toString());
            } catch (IOException e) {
                throw new DataAccessException("Problem while trying to read file '"
                        + sqlFile + "' from classpath.", e);
            }
        }
        return sqlCache.get(sqlFile);
    }

    public static void main(String[] args) {
        SectionDaoJdbc sectionDaoJdbc = new SectionDaoJdbc(DataSourceManager.getDataSource());
        sectionDaoJdbc.findBy("3");
    }
}
