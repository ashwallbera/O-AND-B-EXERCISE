package com.orangeandbronze.enlistment.dao.jdbc;

import com.orangeandbronze.enlistment.dao.AdminDAO;

import javax.sql.DataSource;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AdminDaoJdbc extends AbstractDaoJdbc implements AdminDAO {
    private DataSource dataSource;
    public AdminDaoJdbc(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public Map<String, String> findAdminInfoBy(int adminId) {
        return null;
    }

    @Override
    public Map<String, String> findUserInfobById(int id) {
        try(Connection conn = this.dataSource.getConnection()){
            PreparedStatement stmt = preparedStatement(this.dataSource,
                    "queries/FindUserById.sql");
            stmt.setInt(1,id);
            Map<String,String> users = new HashMap<>();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                users.put("firstname",rs.getString("firstname"));
                users.put("lastname",rs.getString("lastname"));
            }
            conn.close();
            return users;
        }catch (SQLException e){

        }
        return null;
    }


}
