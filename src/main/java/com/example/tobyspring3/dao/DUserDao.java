package com.example.tobyspring3.dao;

import com.example.tobyspring3.domain.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DUserDao extends UserDao{

    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
                Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("", "", "");

        return conn;
    }
}
