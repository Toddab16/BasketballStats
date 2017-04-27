package com.toddbray.basketballstats;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.provider.Settings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Brad on 4/24/2017.
 */

public class MyDbDataSource extends AsyncTask<Void, Integer, Integer> {

    public static final String MYSQL_DATABASE = "t_bray_bball_stats";
    public static final String MYSQL_HOST = "jdbc:mysql://foxi.wuffhost.ovh:3306/t_bray_bball_stats";
    public static final String MYSQL_USERNAME = "t_bray_19751087";
    public static final String MYSQL_PASSWORD = "bballadmin16";
    public static final int MYSQL_PORT = 3306;

    /*
    Synchronize MySQL
    INSERT INTO table (id, name, age) VALUES(1, "A", 19) ON DUPLICATE KEY UPDATE
    name="A", age=19

    Synchronize SQLite
    INSERT OR IGNORE INTO table_name VALUES ($variable_id, $variable_name);
    UPDATE visits SET field_name = $variable_field WHERE id = $variable_id;
     */


    @Override
    protected void onPreExecute() {
        /*
        TextView tv = (TextView) findViewById(R.id.result_textView);
        tv.setText("Download 0% complete");
        progressBar.setProgress(0);
        */


    }

    @Override
    protected Integer doInBackground(Void... params) {
        try {


            String result = "Database connection was successful\n";

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(MYSQL_HOST, MYSQL_USERNAME, MYSQL_PASSWORD);


            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Season");
            ResultSetMetaData rsmd = rs.getMetaData();

            rs.first();
            while(!rs.isAfterLast()) {
                result += rsmd.getColumnName(1) + ": " + rs.getInt(1) + "\n";
                result += rsmd.getColumnName(2) + ": " + rs.getInt(2) + "\n";
                rs.next();
            }


            return null;
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        finally
        {
            // Clean up connection?
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        /*
        TextView tv = (TextView) findViewById(R.id.result_textView);
        int percent = (100 * values[0]) / values[1];
        tv.setText("Download " + percent + "% complete");
        progressBar.setProgress(percent);
        */
    }

    @Override
    protected void onPostExecute(Integer integer) {
        /*
        super.onPostExecute(integer);
        TextView tv = (TextView) findViewById(R.id.result_textView);
        tv.setText("Download complete.  Downloaded " + integer + " bytes");
        */
    }

    @Override
    protected void onCancelled() {
        /*
        TextView tv = (TextView) findViewById(R.id.result_textView);
        tv.setText("Download canceled");
        */
    }
}
