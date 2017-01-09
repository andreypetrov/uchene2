package com.petrovdevelopment.uchene.model;

import java.util.List;

/**
 * Created by Andrey Petrov on 17-01-09.
 */
public class Test extends Model {
    //get all users, and for every user add their role field from the role table
   public static final String SELECT_ALL_TESTS =
            "SELECT t.*, " +
                    "ts.id TEST_SECTION_ID, " +
                    "ts.description TEST_SECTION_DESCRIPTION, " +
                    "q.id QUESTION_ID, " +
                    "q.description QUESTION_DESCRIPTION " +

                    "FROM TEST t " +
                    "LEFT JOIN TEST_SECTION ts " +
                    "ON t.ID = ts.TEST_ID " +
                    "LEFT JOIN TEST_SECTION_QUESTION_XREF tsqx " +
                    "ON ts.ID = tsqx.TEST_SECTION_ID " +
                    "INNER JOIN QUESTION q " +
                    "ON tsqx.QUESTION_ID = q.ID " +
                    "ORDER BY t.ID, TEST_SECTION_ID, q.id";

    public static final String  TEST_SECTION_ID = "TEST_SECTION_ID";
    public static final String  TEST_SECTION_DESCRIPTION = "TEST_SECTION_DESCRIPTION";
    public static final String  QUESTION_ID = "QUESTION_ID";
    public static final String  QUESTION_DESCRIPTION = "QUESTION_DESCRIPTION";


    public List<TestSection> testSections;


}
