package com.petrovdevelopment.uchene.model;

/**
 * Created by Andrey Petrov on 17-01-09.
 */
public class User extends Model {
    //get all users, and for every user add their role field from the role table
    public static final String SELECT_ALL_USERS =
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
}
