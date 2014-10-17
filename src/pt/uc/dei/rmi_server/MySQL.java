package pt.uc.dei.rmi_server;

import java.sql.Connection;
import java.sql.*;

public class MySQL {

    String dbUrl, dbName, dbUser, dbPass, dbPort;

    private Connection conn;

    public MySQL() {
        dbUrl = "localhost";
        dbName = "meeto";
        dbUser = dbPass = "root";
        dbPort = "8889";
    }

    public boolean connect() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            dbUrl = "jdbc:mysql://" + dbUrl + ":" + dbPort + "/" + dbName;
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            System.out.println("Connection to the database successful.");
        } catch (Exception e) {
            System.err.println("There was an error while trying to connect to the database server.");
            return false;
        }

        return true;

    }

    public ResultSet doQuery(String query) {

        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            return rs;
        } catch (com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException ex) {
            System.err.println("The user violated one constraint. No big deal.");
            System.err.println("Query: " + query);
            return null;
        } catch (SQLException e) {
            System.err.println("There was an error processing the query: " + query);
            return null;
        }

    }

    public int doUpdate(String query) {

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            System.err.println("There was an error processing the query: " + query);
            return 0;
        }
        return 1;
    }


//    public void close() {
//        if (conn != null) {
//            try {
//                conn.close();
//            } catch (Exception e) {
//            }
//        }
//        conn = null;
//    }
//
//    public boolean isConnected() {
//        try {
//            return !conn.isClosed();
//        } catch (SQLException ex) {
//            return false;
//        }
//    }


}



