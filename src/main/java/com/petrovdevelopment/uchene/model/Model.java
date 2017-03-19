package com.petrovdevelopment.uchene.model;

import com.petrovdevelopment.uchene.db.JacksonParser;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Andrey Petrov on 17-01-09.
 */
public abstract class Model {
    public static final String ID = "ID";
    public static final String DESCRIPTION = "DESCRIPTION";
    public int id;
    public String description;

    @Override
    public String toString() {
        return JacksonParser.getInstance().toJson(this);
    }
}
