package com.petrovdevelopment.uchene.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Andrey Petrov on 17-01-09.
 */
public class User extends Model {
    public static String TABLE = "USER";

    //get all users, and for every user add their role field from the role table
    public static final String SELECT_ALL_USERS_WITH_ROLES =
            "SELECT u.*, r.description ROLE " +
                    "FROM USER u " +
                    "LEFT JOIN ROLE r " +
                    "ON u.ROLE_ID = r.ID ";


    public final static String ID = "ID";
    public final static String FIRST_NAME = "FIRST_NAME";
    public final static String LAST_NAME = "LAST_NAME";
    public final static String IMAGE_URL = "IMAGE_URL";
    public final static String PASSWORD = "PASSWORD";
    public final static String ROLE = "ROLE";

    public String firstName;
    public String lastName;
    public String imageUrl;
    public String password;
    public String role;

    public static User createUserWithRole(ResultSet resultSet) throws SQLException{
        User user = new User();
        user.id = resultSet.getInt(User.ID);
        user.firstName = resultSet.getString(User.FIRST_NAME);
        user.lastName = resultSet.getString(User.LAST_NAME);
        user.role = resultSet.getString(User.ROLE);
        user.imageUrl = resultSet.getString(User.IMAGE_URL);
        return user;
    }
}
