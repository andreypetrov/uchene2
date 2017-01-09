package com.petrovdevelopment.uchene.db;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Andrey Petrov on 17-01-09.
 */
public class JacksonParser {
    private static JacksonParser instance;

    public static JacksonParser getInstance() {
        if (instance == null) {
            instance = new JacksonParser();
        }
        return instance;
    }

    //Jackson mapper
    private ObjectMapper mapper;

    public void initialize() {
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public String toJson(Object object) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }



}
