package com.petrovdevelopment.uchene.db;

import java.sql.*;

/**
 * Created by Andrey Petrov on 17-01-07.
 */
public class DatabaseManager {
    private static DatabaseManager instance;

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }


    public static void getUsers() {

    }

    public static String resultSetToJson(ResultSet resultSet) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            while (resultSet.next()) {
                // read the result set
                sb.append("name:\"");
                sb.append(resultSet.getString("name"));
                sb.append("\"");
            }

            return sb.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            return "{}";
        }
    }

    public static void initialize () {
        // load the sqlite-JDBC driver using the current class loader
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("drop table if exists person");
            statement.executeUpdate("create table person (id integer, name string)");
            statement.executeUpdate("insert into person values(1, 'leo')");
            statement.executeUpdate("insert into person values(2, 'yui')");
            ResultSet rs = statement.executeQuery("select * from person");
            while (rs.next()) {
                // read the result set
                System.out.println("name = " + rs.getString("name"));
                System.out.println("id = " + rs.getInt("id"));
            }
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
    }
}


