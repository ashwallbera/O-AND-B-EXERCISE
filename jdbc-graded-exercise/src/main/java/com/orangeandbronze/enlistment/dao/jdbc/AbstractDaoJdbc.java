package com.orangeandbronze.enlistment.dao.jdbc;

import com.orangeandbronze.enlistment.dao.DataAccessException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.orangeandbronze.enlistment.dao.jdbc.DataSourceManager.getSql;

public abstract class AbstractDaoJdbc  {

    public final static Map<String, String> sqlCache = new HashMap<>();
    public static PreparedStatement preparedStatement(DataSource ds, String sqlFile) throws DataAccessException {
        PreparedStatement preparedStatement = null;

        try {
            Connection conn = ds.getConnection();
            preparedStatement = conn.prepareStatement(getSql(sqlFile));
        } catch (SQLException ex) {
            throw new DataAccessException(
                    ex.getMessage(),
                    ex
            );
        }

        return preparedStatement;
    }
}
