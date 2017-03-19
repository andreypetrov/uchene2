package com.petrovdevelopment.uchene.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey Petrov on 17-01-09.
 */
public class Test extends Model {
    public static String TABLE = "TEST";

    public static final String SELECT_TEST_WITH_ANSWERS =
            "SELECT t.*, " +
                    "ts.id TEST_SECTION_ID, " +
                    "ts.description TEST_SECTION_DESCRIPTION, " +
                    "q.id QUESTION_ID, " +
                    "q.description QUESTION_DESCRIPTION, " +
                    "q.correct_answer_id QUESTION_CORRECT_ANSWER_ID, " +
                    "q.IMAGE_URL QUESTION_IMAGE_URL, " +
                    "a.id ANSWER_ID, " +
                    "a.description ANSWER_DESCRIPTION, " +
                    "qc.description QUESTION_CATEGORY_DESCRIPTION, " +
                    "traf.ANSWER_ID GIVEN_ANSWER, " +
                    "traf.IS_CORRECT " +

                    "FROM TEST t " +
                    "LEFT JOIN TEST_SECTION ts " +
                    "ON t.ID = ts.TEST_ID " +
                    "LEFT JOIN TEST_SECTION_QUESTION_XREF tsqx " +
                    "ON ts.ID = tsqx.TEST_SECTION_ID " +
                    "LEFT JOIN QUESTION q " +
                    "ON tsqx.QUESTION_ID = q.ID " +
                    "LEFT JOIN ANSWER_POOL ap " +
                    "ON ap.ID = q.ANSWER_POOL_ID " +
                    "LEFT JOIN ANSWER a " +
                    "ON ap.ID = a.ANSWER_POOL_ID " +
                    "LEFT JOIN QUESTION_CATEGORY qc " +
                    "ON q.QUESTION_CATEGORY_ID = qc.ID " +
                    "LEFT JOIN TEST_RESULT_ANSWERS_FACTS traf " +
                    "ON t.ID = traf.TEST_ID AND q.id = traf.QUESTION_ID AND traf.STUDENT_ID = ? " +
                    "WHERE t.ID = ? " +
                    "ORDER BY t.ID, TEST_SECTION_ID, q.id, a.id";


    public static final String SELECT_ALL_TESTS =
            "SELECT t.*, " +
                    "ts.id TEST_SECTION_ID, " +
                    "ts.description TEST_SECTION_DESCRIPTION, " +
                    "q.id QUESTION_ID, " +
                    "q.description QUESTION_DESCRIPTION, " +
                    "q.correct_answer_id QUESTION_CORRECT_ANSWER_ID, " +
                    "q.IMAGE_URL QUESTION_IMAGE_URL, " +
                    "a.id ANSWER_ID, " +
                    "a.description ANSWER_DESCRIPTION, " +
                    "qc.description QUESTION_CATEGORY_DESCRIPTION " +

                    "FROM TEST t " +
                    "LEFT JOIN TEST_SECTION ts " +
                    "ON t.ID = ts.TEST_ID " +
                    "LEFT JOIN TEST_SECTION_QUESTION_XREF tsqx " +
                    "ON ts.ID = tsqx.TEST_SECTION_ID " +
                    "LEFT JOIN QUESTION q " +
                    "ON tsqx.QUESTION_ID = q.ID " +
                    "LEFT JOIN ANSWER_POOL ap " +
                    "ON ap.ID = q.ANSWER_POOL_ID " +
                    "LEFT JOIN ANSWER a " +
                    "ON ap.ID = a.ANSWER_POOL_ID " +
                    "LEFT JOIN QUESTION_CATEGORY qc " +
                    "ON q.QUESTION_CATEGORY_ID = qc.ID " +
                    "ORDER BY t.ID, TEST_SECTION_ID, q.id, a.id";


    /* -- all questions --*/
    public static String SELECT_QUESTIONS_COUNT = "SELECT COUNT(DISTINCT QUESTION_ID) AS QUESTIONS_COUNT FROM TEST t " +
            "LEFT JOIN TEST_SECTION ts " +
            "ON t.ID = ts.TEST_ID " +
            "LEFT JOIN TEST_SECTION_QUESTION_XREF tsqx " +
            "ON ts.ID = tsqx.TEST_SECTION_ID " +
            "LEFT JOIN QUESTION q " +
            "ON tsqx.QUESTION_ID = q.ID " +
            "WHERE t.ID = ?;";

    public static final String TEST_SECTION_ID = "TEST_SECTION_ID";
    public static final String TEST_SECTION_DESCRIPTION = "TEST_SECTION_DESCRIPTION";
    public static final String QUESTION_ID = "QUESTION_ID";
    public static final String QUESTION_DESCRIPTION = "QUESTION_DESCRIPTION";
    public static final String QUESTION_IMAGE_URL = "QUESTION_IMAGE_URL";
    public static final String QUESTION_CORRECT_ANSWER_ID = "QUESTION_CORRECT_ANSWER_ID";
    public static final String ANSWER_ID = "ANSWER_ID";
    public static final String ANSWER_DESCRIPTION = "ANSWER_DESCRIPTION";
    public static final String QUESTION_CATEGORY_DESCRIPTION = "QUESTION_CATEGORY_DESCRIPTION";
    public static final String GIVEN_ANSWER = "GIVEN_ANSWER";
    public static final String IS_CORRECT = "IS_CORRECT";
    public static final String QUESTIONS_COUNT = "QUESTIONS_COUNT";

    public List<TestSection> testSections;

    public Test(ResultSet resultSet) throws SQLException {
        id = resultSet.getInt(Test.ID);
        description = resultSet.getString(Test.DESCRIPTION);
        testSections = new ArrayList<TestSection>();
    }
}
