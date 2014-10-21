package pt.uc.dei.client;


import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class EmbeddedHelper {

    static String url;
    static java.sql.Connection conn;


    public void startupEmbeddedDB() throws ClassNotFoundException, SQLException {
        url = "jdbc:h2:./data/meeto;INIT=runscript from './support/h2sqlscript.sql'";
        Class.forName("org.h2.Driver");
        conn = DriverManager.getConnection(url, "sa", "");

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


}



