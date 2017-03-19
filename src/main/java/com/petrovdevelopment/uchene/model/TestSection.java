package com.petrovdevelopment.uchene.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey Petrov on 17-01-09.
 */
public class TestSection extends Model {
    public static String TABLE  = "TEST_SECTION";

    public List<Question> questions;

    public static TestSection createTestSectionForTest(ResultSet resultSet) throws SQLException {
        TestSection testSection = new TestSection();
        testSection.id = resultSet.getInt(Test.TEST_SECTION_ID);
        testSection.description = resultSet.getString(Test.TEST_SECTION_DESCRIPTION);
        testSection.questions = new ArrayList<Question>();
        return testSection;
    }
}
