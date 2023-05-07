package com.orangeandbronze.enlistment.dao.jdbc;

import com.orangeandbronze.enlistment.dao.DataAccessException;
import com.orangeandbronze.enlistment.dao.EnlistmentDAO;
import com.orangeandbronze.enlistment.dao.StaleDataException;
import com.orangeandbronze.enlistment.domain.*;

import javax.sql.*;
import java.sql.*;

public class EnlistmentDaoJdbc extends AbstractDaoJdbc implements EnlistmentDAO {

    private final DataSource ds;

    public EnlistmentDaoJdbc(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public void create(Student student, Section section) {
        try (Connection conn = ds.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmtUpdateVersion = preparedStatement(ds, "queries/UpdateSection.sql")) {
                stmtUpdateVersion.setString(1, section.getSectionId());
                stmtUpdateVersion.setInt(2, section.getVersion());
                int recordsUpdated = stmtUpdateVersion.executeUpdate();
                if (recordsUpdated != 1) {
                    conn.rollback();
                    throw new StaleDataException("Enlistment data for "
                            + section + " was not updated to current version.");
                }
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO enlistments(student_number, section_id) VALUES(?, ?)")) {
                stmt.setInt(1, student.getStudentNumber());
                stmt.setString(2, section.getSectionId());
                if (stmt.executeUpdate() > 0) {
                    conn.commit();
                } else {
                    conn.rollback();
                }
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DataAccessException(
                    "while inserting into enlistments table for " + student + ", " + section, e);
        }
    }


    @Override
    public void delete(int studentNumber, String sectionId) {
        try (Connection conn = ds.getConnection()) {
            conn.setAutoCommit(false);
            int version = -1;

            // VERSION CHECK
            // ...is it needed here?
            try (PreparedStatement versionStatement = conn
                    .prepareStatement("SELECT version FROM sections WHERE section_id = ?;")) {
                versionStatement.setString(1, sectionId);
                ResultSet rs = versionStatement.executeQuery();

                if (rs.next()) {
                    version = rs.getInt("version");
                }
            }

            try (PreparedStatement stmtUpdateVersion = conn.prepareStatement(
                    "UPDATE sections SET version = version + 1 WHERE section_id = ? AND version = ?")) {
                stmtUpdateVersion.setString(1, sectionId);
                stmtUpdateVersion.setInt(2, version);
                int recordsUpdated = stmtUpdateVersion.executeUpdate();
                if (recordsUpdated != 1) {
                    conn.rollback();
                    throw new StaleDataException("Enlistment data for "
                            + sectionId + " was not updated to current version.");
                }
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }

            try (PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM enlistments WHERE student_number = ? AND section_id = ?;")) {
                stmt.setInt(1, studentNumber);
                stmt.setString(2, sectionId);
                if (stmt.executeUpdate() > 0) {
                    conn.commit();
                } else {
                    conn.rollback();
                }
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DataAccessException(
                    "while removing from enlistments table for " + studentNumber + ", " + sectionId, e);
        }
    }
}