package com.petrovdevelopment.uchene.db;

import java.sql.*;

/**
 * Created by Andrey Petrov on 17-01-09.
 */
public class SelectQueries {
    public static String SELECT_ALL_TEST_QUESTIONS  = "SELECT * FROM TEST";
    public static String SELECT_ALL_USERS  = "SELECT u.*, r.description role FROM USER u LEFT JOIN ROLE r ON u.ROLE_ID = r.ID ";

    public static void selectAllUsers() {
        ResultSet resultSet = select(SELECT_ALL_USERS);
        try {
            while (resultSet.next()) {
                // read the result set
                System.out.println("first name = " + resultSet.getString("FIRST_NAME"));
                System.out.println("role = " + resultSet.getString("role"));
                System.out.println("id = " + resultSet.getInt("ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet select (String selectQuery) {
        ResultSet resultSet = null;
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:mate.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            resultSet = statement.executeQuery(selectQuery);
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
        return resultSet;
    }

}
