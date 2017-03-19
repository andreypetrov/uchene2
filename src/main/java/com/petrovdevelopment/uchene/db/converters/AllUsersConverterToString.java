package com.petrovdevelopment.uchene.db.converters;

import com.petrovdevelopment.uchene.db.JacksonParser;
import com.petrovdevelopment.uchene.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey Petrov on 17-03-19.
 */
public class AllUsersConverterToString implements ResultSetConverterToString {

    @Override
    public String convert(ResultSet resultSet) {
        String result = null;
        List<User> list = new ArrayList<User>();
        try {
            while (resultSet.next()) {
                User user = User.createUserWithRole(resultSet);
                list.add(user);
            }
            result = JacksonParser.getInstance().toJson(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
