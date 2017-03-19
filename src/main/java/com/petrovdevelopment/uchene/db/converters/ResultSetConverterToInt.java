package com.petrovdevelopment.uchene.db.converters;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Andrey Petrov on 17-03-19.
 */
public interface ResultSetConverterToInt {
    int convert(ResultSet resultSet) throws SQLException;
}
