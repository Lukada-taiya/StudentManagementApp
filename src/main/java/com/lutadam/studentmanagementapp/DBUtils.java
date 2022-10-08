package com.lutadam.studentmanagementapp;

import java.sql.*;

public class DBUtils {
    private static Connection connection = null;
    private static PreparedStatement fetch = null;
    private static ResultSet fetchedData = null;

    public static Connection connectDb() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentmanagementapp",
                    "root", "");
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeAllResources() {
        if(fetchedData != null) {
            try {
                fetchedData.close();
            }catch (SQLException e) { e.printStackTrace();}
        }
        if(fetch != null) {
            try {
                fetch.close();
            }catch (SQLException e) { e.printStackTrace();}
        }
        if(connection != null) {
            try {
                connection.close();
            }catch (SQLException e) { e.printStackTrace();}
        }
    }

    public static void closeResource(Connection opt) {
        if(opt != null) {
            try {
                opt.close();
            }catch (SQLException e) { e.printStackTrace();}
        }
    }
    public static void closeResource(PreparedStatement opt) {
        if(opt != null) {
            try {
                opt.close();
            }catch (SQLException e) { e.printStackTrace();}
        }
    }
    public static void closeResource(ResultSet opt) {
        if(opt != null) {
            try {
                opt.close();
            }catch (SQLException e) { e.printStackTrace();}
        }
    }


    public static ResultSet fetchDb(String query, String... opts) {
        connection = connectDb();

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
        }
        return fetchedData;
    }

    public static void insertDb(String query, String... opts) {
        Connection connection = connectDb();
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
