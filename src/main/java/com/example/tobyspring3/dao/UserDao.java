package com.example.tobyspring3.dao;

import com.example.tobyspring3.domain.User;

import java.sql.*;

import static java.lang.System.getenv;

public class UserDao {
    ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public UserDao() {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection conn = connectionMaker.makeConnection();

        PreparedStatement pstmt = conn.prepareStatement("insert into users(id, name, password) values (?, ? ,?)");
        pstmt.setString(1, user.getId());
        pstmt.setString(2, user.getName());
        pstmt.setString(3, user.getPassword());

        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }


    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection conn = connectionMaker.makeConnection();

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
        ConnectionMaker cm = new DConnectionMaker();
        UserDao userDao = new UserDao(cm);
        User user = new User();
        user.setId("5");
        user.setName("shinsoo");
        user.setPassword("123456789");
        userDao.add(user);

        User selectedUser = userDao.get("5");
        System.out.println(selectedUser.getId());
        System.out.println(selectedUser.getName());
        System.out.println(selectedUser.getPassword());
    }
}
