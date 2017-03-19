package com.petrovdevelopment.uchene.model;

/**
 * Created by Andrey Petrov on 17-01-09.
 */
public class TestResult {
    public static String TABLE  = "TEST_RESULT";

    public int testId;
    public User student;
    public String datetimeStart;
    public String datetimeEnd;
    public boolean isCompleted;
}
