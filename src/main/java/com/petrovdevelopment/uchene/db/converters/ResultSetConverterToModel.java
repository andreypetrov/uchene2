package com.petrovdevelopment.uchene.db.converters;

import com.petrovdevelopment.uchene.model.Model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Andrey Petrov on 17-03-19.
 */
public interface ResultSetConverterToModel {
    Model convertToModel(ResultSet resultSet) throws SQLException;
}
