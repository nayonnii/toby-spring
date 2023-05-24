package com.example.tobyspring3.dao;

import com.example.tobyspring3.domain.User;

import java.sql.*;
import java.util.Map;

import static java.lang.System.getenv;

public class UserDao {
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Map<String, String> env = getenv();
        String dbHost = env.get("DB_HOST");
        String dbUser = env.get("DB_USER");
        String dbPassword = env.get("DB_PASSWORD");

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(dbHost, dbUser, dbPassword);

        return conn;
    }


    public void add(User user) throws ClassNotFoundException, SQLException {
          Connection conn = getConnection();

//        Map<String, String> env = getenv();
//        String dbHost = env.get("DB_HOST");
//        String dbUser = env.get("DB_USER");
//        String dbPassword = env.get("DB_PASSWORD");
//
//        Class.forName("com.mysql.cj.jdbc.Driver"); // 신 버전에는 cj가 들어감(책엔 없음)
//        Connection conn = DriverManager.getConnection(dbHost, dbUser, dbPassword);

        PreparedStatement pstmt = conn.prepareStatement("insert into users(id, name, password) values (?, ? ,?)");
        pstmt.setString(1, user.getId());
        pstmt.setString(2, user.getName());
        pstmt.setString(3, user.getPassword());

        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }


    public User get(String id) throws ClassNotFoundException, SQLException {
          Connection conn = getConnection();
//        Map<String, String> env = getenv();
//        String dbHost = env.get("DB_HOST");
//        String dbUser = env.get("DB_USER");
//        String dbPassword = env.get("DB_PASSWORD");
//
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        Connection conn = DriverManager.getConnection(dbHost, dbUser, dbPassword);

        PreparedStatement pstmt = conn.prepareStatement("select id, name, password from users where id = ?");    // 모든 걸 호출하더라도 컬럼명을 써주는 게 좋음
        pstmt.setString(1, id);
        ResultSet rs = pstmt.executeQuery();
        rs.next();  // sql workbench에서 control + enter를 입력하는 것과 동일

        User user= new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        // 안닫아주면 db와 계속 연결되어 있음(물론 지금은 이 메소드를 실행하고 jdbc가 내려가서 문제는 없지만 서버에서 안내려가는 프로그램이면 문제)
        rs.close();
        pstmt.close();
        conn.close();

        return user;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
        User user = new User();
        user.setId("1");
        user.setName("sunghan");
        user.setPassword("123456");
        userDao.add(user);

        User selectedUser = userDao.get("1");
        System.out.println(selectedUser.getId());
        System.out.println(selectedUser.getName());
        System.out.println(selectedUser.getPassword());
    }
}
