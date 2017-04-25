package com.toddbray.basketballstats;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Brad on 4/24/2017.
 */

public class MyDbDataSource {
    // Add MySQL connection here
    // https://www.youtube.com/watch?v=cDGc28XQnRo

    public static final String MYSQL_DATABASE = "t_bray_bball_stats";
    public static final String MYSQL_HOST = "jdbc:mysql://10.0.5.190:3306/instructor";
    public static final String MYSQL_USERNAME = "newUser";
    public static final String MYSQL_PASSWORD = "pass123";
    public static final int MYSQL_PORT = 3306;

    public String testConnect() throws SQLException {
        try {

            // Just for testing, replace with ASync task
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
                           StrictMode.setThreadPolicy(policy);

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(MYSQL_HOST, MYSQL_USERNAME, MYSQL_PASSWORD);
            //Connection con = DriverManager.getConnection("jdbc:mysql://10.0.5.190/instructor/?user=test&password=test123");

            String result = "Database connection was successfuly\n";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM teach");
            ResultSetMetaData rsmd = rs.getMetaData();

            while(rs.next()) {
                result += rsmd.getColumnName(1) + ": " + rs.getInt(1) + "\n";
                result += rsmd.getColumnName(2) + ": " + rs.getInt(2) + "\n";
                result += rsmd.getColumnName(3) + ": " + rs.getString(3) + "\n";
            }

            return result;
        }
        catch(Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }
}
