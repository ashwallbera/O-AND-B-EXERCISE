package com.orangeandbronze.enlistment.dao.jdbc;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import javax.sql.*;

import com.orangeandbronze.enlistment.dao.DataAccessException;
import org.postgresql.ds.*;


public class DataSourceManager extends AbstractDaoJdbc{

    private static DataSource dataSource;

    public static DataSource getDataSource(){
        if (dataSource == null) {
            Properties prop = new Properties();
            String propFileName = "pg.datasource.properties";
            try (Reader reader = new BufferedReader(
                    new InputStreamReader(
                            DataSourceManager.class.getClassLoader()
                                    .getResourceAsStream(propFileName)))) {
                prop.load(reader);
            } catch (IOException e) {
                throw new RuntimeException("problem reading file "
                        + propFileName, e);
            }

            PGSimpleDataSource ds = new PGSimpleDataSource();
            ds.setDatabaseName("enlistment");
            ds.setUser("enlistment");
            ds.setPassword("enlistment");

            dataSource = ds;
        }
        return dataSource;
    }

    public static DataSource getHikariPgDataSource() {
        return dataSource;
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static String getSql(String sqlFile) {
        if(!sqlCache.containsKey(sqlFile)) {
            try (Reader reader = new BufferedReader(new InputStreamReader(
                    Objects.requireNonNull(DataSourceManager.class.getClassLoader().getResourceAsStream(sqlFile))
            ))) {

                StringBuilder builder = new StringBuilder();
                int i = 0;

                while ((i = reader.read()) !=  -1) {
                    builder.append((char) i);
                }

                sqlCache.put(sqlFile, builder.toString());
            } catch (IOException ex) {
                throw new DataAccessException(
                        String.format("Problem while trying to read file %s from classpath", sqlFile),
                        ex
                );
            }
        }

        return sqlCache.get(sqlFile);
    }



}

