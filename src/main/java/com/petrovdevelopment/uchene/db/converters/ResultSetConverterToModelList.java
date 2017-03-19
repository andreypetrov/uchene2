package com.petrovdevelopment.uchene.db.converters;

import com.petrovdevelopment.uchene.model.Model;
import com.sun.org.apache.xpath.internal.operations.Mod;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Andrey Petrov on 17-03-19.
 */
public interface ResultSetConverterToModelList<E extends Model> {
    List<E> convert(ResultSet resultSet) throws SQLException;
}
