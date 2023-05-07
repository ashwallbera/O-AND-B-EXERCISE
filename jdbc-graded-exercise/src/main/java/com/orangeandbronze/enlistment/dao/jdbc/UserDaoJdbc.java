package com.orangeandbronze.enlistment.dao.jdbc;

import com.orangeandbronze.enlistment.dao.DataAccessException;
import com.orangeandbronze.enlistment.dao.UserDAO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.orangeandbronze.enlistment.dao.jdbc.DataSourceManager.getSql;

public class UserDaoJdbc extends AbstractDaoJdbc implements UserDAO {

    private DataSource ds;

    public UserDaoJdbc(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public Map<String, String> findUserInfobById(int id) {
        Map<String, String> userInfo = new HashMap<>();
        // no users table.
        try (PreparedStatement stmt = preparedStatement(ds, "FindAdminById.sql")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    userInfo.put("firstname", rs.getString("firstname"));
                    userInfo.put("lastname", rs.getString("lastname"));
                }
            }
            stmt.getConnection().close();
        } catch (SQLException e) {
            throw new DataAccessException("Error finding admin info for id: " + id, e);
        }
        return userInfo;
    }

}
