package com.orangeandbronze.enlistment.dao.jdbc;

import com.orangeandbronze.enlistment.dao.DataAccessException;
import com.orangeandbronze.enlistment.dao.StudentDAO;
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
import java.util.Map;

public class StudentDaoJdbc extends AbstractDaoJdbc implements StudentDAO {
    private DataSource dataSource;
    public StudentDaoJdbc(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public StudentDaoJdbc(){

    }

    @Override
    public Student findWithSectionsBy(int studentNumber) {
        String firstName = null, lastName = null;

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = getPreparedStatement(conn,
                    "queries/FindStudentWithSections.sql");
            stmt.setInt(1, studentNumber);

            boolean found = false;
            Collection<Section> sections = new ArrayList<>();

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // turned conditional statement into ternary
                    found = (found) ? found : true;

                    if (!StringUtils.isBlank(rs.getString("section_id"))) {
                        String sectionId = rs.getString("section_id");
                        String subjectId = rs.getString("subject_id");
                        Schedule schedule = Schedule.valueOf(rs.getString("schedule"));
                        String roomName = rs.getString("room_name");
                        int capacity = rs.getInt("capacity");
                        Room room = new Room(roomName, capacity);

                        firstName = rs.getString("firstname");
                        lastName = rs.getString("lastname");

                        Subject subject = new Subject(subjectId);
                        Section section = new Section(sectionId, subject, schedule, room);
                        sections.add(section);
                    }
                }
                stmt.getConnection().close();
            }
            return found ? new Student(studentNumber, firstName, lastName, sections) : Student.NONE;
        } catch (SQLException e) {
            throw new DataAccessException(
                    "Problem retrieving student data for student with student number "
                            + studentNumber, e);
        }
    }

    @Override
    public Student findWithoutSectionsBy(int studentNumber) {
        Student student = Student.NONE;
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = getPreparedStatement(conn, "FindStudentByStudentNo.sql");

            stmt.setInt(1, studentNumber);

            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("sample"+rs);
                if (rs.next()) {
                    String firstName = rs.getString("firstname");
                    String lastName = rs.getString("lastname");
                    student = new Student(studentNumber, firstName, lastName);
                }
            }
            stmt.getConnection().close();
            return student;
        } catch (SQLException e) {
            throw new DataAccessException(
                    "Problem retrieving student data for student " +
                            "with student number " + studentNumber, e);
        }
    }

    @Override
    public Map<String, String> findUserInfobById(int id) {
        return null;
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


}
