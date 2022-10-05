package com.lutadam.studentmanagementapp;

import java.sql.*;

public class DBUtils {

    private static Connection connectDb() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql:localhost:3306/studentmanagementapp",
                    "root", "");
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static void closeResource(Connection opt) {
        if(opt != null) {
            try {
                opt.close();
            }catch (SQLException e) { e.printStackTrace();}
        }
    }
    private static void closeResource(PreparedStatement opt) {
        if(opt != null) {
            try {
                opt.close();
            }catch (SQLException e) { e.printStackTrace();}
        }
    }
    private static void closeResource(ResultSet opt) {
        if(opt != null) {
            try {
                opt.close();
            }catch (SQLException e) { e.printStackTrace();}
        }
    }


    private static ResultSet fetchDb(String query, String... opts) {
        Connection connection = connectDb();
        ResultSet fetchedData = null;
        PreparedStatement fetch = null;

        try{
            fetch = connection.prepareStatement(query);
            int count = 1;
            for(String opt:opts) {
                fetch.setString(count, opt);
                count++;
            }
            fetchedData = fetch.executeQuery();
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeResource(fetchedData);
            closeResource(fetch);
            closeResource(connection);
        }
        return fetchedData;
    }

    private static void insertDb(String query, String... opts) {
        Connection connection = connectDb();
        PreparedStatement fetch = null;

        try{
            fetch = connection.prepareStatement(query);
            int count = 1;
            for(String opt:opts) {
                fetch.setString(count, opt);
                count++;
            }
            fetch.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeResource(fetch);
            closeResource(connection);
        }
    }

}
