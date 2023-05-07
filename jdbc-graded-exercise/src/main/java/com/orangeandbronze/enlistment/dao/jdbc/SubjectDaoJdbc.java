package com.orangeandbronze.enlistment.dao.jdbc;

import com.orangeandbronze.enlistment.dao.DataAccessException;
import com.orangeandbronze.enlistment.dao.SubjectDAO;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class SubjectDaoJdbc extends AbstractDaoJdbc implements SubjectDAO {

    private DataSource dataSource;
    public SubjectDaoJdbc(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public Collection<String> findAllIds() {
        Collection<String> subjects = new ArrayList<>();
        try (PreparedStatement stmt = preparedStatement(dataSource,"FindAllIds.sql")) {
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                subjects.add(rs.getString("subject_id"));
            }
            stmt.getConnection().close();

        } catch(SQLException ex) {
            throw new DataAccessException(
                    String.format(""+ex.getMessage()),
                    ex
            );
        }
        return subjects;
    }
}
