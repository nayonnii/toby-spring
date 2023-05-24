package com.example.tobyspring3.db;

import java.sql.*;
import java.util.Map;

import static java.lang.System.getenv;

// 실행 시 TobySpring3Application 실행하면 spring이 떠버려서 ConnectionChecer를 실행해야함
public class ConnectionChecker {
    public void check() throws ClassNotFoundException, SQLException {
        Map<String, String> env = getenv(); //getenv()메소드는 static 메소드임
        String dbHost = env.get("DB_HOST");
        String dbUser = env.get("DB_USER");
        String dbPassword = env.get("DB_PASSWORD");

        Class.forName("com.mysql.cj.jdbc.Driver");  //추가한 드라이브 안에 있는 driver라는 클래스를 쓰겠다는 의미
        Connection conn = DriverManager.getConnection(dbHost, dbUser, dbPassword);




        Statement stmt = conn.createStatement();    //쿼리를 날릴 수 있는 오브젝트(statement = 명령), 이 줄과 아랫줄은 같이 쓰임
        ResultSet rs = stmt.executeQuery("show databases");

        while(rs.next()) {  //null이 나올 때까지(결과가 없을 때까지 = 쿼리에 해당하는 결과 모두 출력할 때까지) roop을 돔
            String line = rs.getString(1);  // 컬럼(열)이 하나이기 때문에 1
            System.out.println(line);
        }
    }

//    public void add() throws ClassNotFoundException, SQLException {
//        Map<String, String> env = getenv();
//        String dbHost = env.get("DB_HOST");
//        String dbUser = env.get("DB_USER");
//        String dbPassword = env.get("DB_PASSWORD");
//
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        Connection conn = DriverManager.getConnection(dbHost, dbUser, dbPassword);
//
//        PreparedStatement pstmt = conn.prepareStatement(
//                "insert into users(id, name, password) values (?, ?, ?)"
//        );
//        pstmt.setString(1, "2");
//        pstmt.setString(2, "nayeon");
//        pstmt.setString(3, "1234");
//        pstmt.executeUpdate();
//    }
//
//    public void update() throws ClassNotFoundException, SQLException {
//        Map<String, String> env = getenv();
//        String dbHost = env.get("DB_HOST");
//        String dbUser = env.get("DB_USER");
//        String dbPassword = env.get("DB_PASSWORD");
//
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        Connection conn = DriverManager.getConnection(dbHost, dbUser, dbPassword);
//        PreparedStatement pstmt = conn.prepareStatement(
//                "update users set name = ? where id = ?"
//        );
//        pstmt.setString(1, "test");
//        pstmt.setString(2, "1");
//        pstmt.executeUpdate();
//    }
//
//    public void select() throws ClassNotFoundException, SQLException {
//        Map<String, String> env = getenv();
//        String dbHost = env.get("DB_HOST");
//        String dbUser = env.get("DB_USER");
//        String dbPassword = env.get("DB_PASSWORD");
//
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        Connection conn = DriverManager.getConnection(dbHost, dbUser, dbPassword);
//
//        Statement stmt = conn.createStatement();    //쿼리를 날릴 수 있는 오브젝트(statement = 명령), 이 줄과 아랫줄은 같이 쓰임
//        ResultSet rs = stmt.executeQuery("select * from users");
//
//        while(rs.next()) {  // users 속성(열) 개수가 3개라서 3개 출력
//            String col1 = rs.getString(1);
//            String col2 = rs.getString(2);
//            String col3 = rs.getString(3);
//
//            System.out.printf("%s %s %s\n", col1, col2, col3);
//        }
//    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ConnectionChecker cc = new ConnectionChecker();
        cc.check();
    }
}

