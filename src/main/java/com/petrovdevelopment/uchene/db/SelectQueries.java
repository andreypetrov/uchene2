package com.petrovdevelopment.uchene.db;

import com.petrovdevelopment.uchene.model.*;

import java.sql.*;
import java.util.*;

/**
 * Created by Andrey Petrov on 17-01-09.
 */
public class SelectQueries {
    public static final String DATABASE_CONNECTION = "jdbc:sqlite:mate.db";
    public static final int QUERY_TIMEOUT = 30;

    public static String select(String selectQuery) {
        String result = null;
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection(DATABASE_CONNECTION);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(QUERY_TIMEOUT);  // set timeout to 30 sec.
            ResultSet resultSet = statement.executeQuery(selectQuery);
            result = convertResultSetToString(resultSet, selectQuery);
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
        return result;
    }

    private static String convertResultSetToString(ResultSet resultSet, String selectQuery) {
       if (User.SELECT_ALL_USERS.equals(selectQuery)) {
            return getAllUsers(resultSet);
        } else if (Test.SELECT_ALL_TESTS.equals(selectQuery)) {
            return getAllTestsWithSubsections(resultSet);
        } else {
            return "";
        }
    }

    private static String getAllUsers(ResultSet resultSet) {
        String result = null;
        List<User> list = new ArrayList<User>();
        try {
            while (resultSet.next()) {
                User row = new User();
                row.id = resultSet.getInt(User.ID);
                row.firstName = resultSet.getString(User.FIRST_NAME);
                row.lastName = resultSet.getString(User.LAST_NAME);
                row.role = resultSet.getString(User.ROLE);
                row.imageUrl = resultSet.getString(User.IMAGE_URL);
                list.add(row);
            }
            result = JacksonParser.getInstance().toJson(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String getAllTestsWithSubsections(ResultSet resultSet) {
        String result = null;

        List<Test> list = new ArrayList<Test>();
        try {
            int previousTestId = -1;
            int previousTestSectionId = -1;
            int previousQuestionId = -1;

            Test row = null;
            TestSection testSection = null;
            Question question = null;
            while (resultSet.next()) {

                int testId = resultSet.getInt(Test.ID);
                //check if a new test has started
                //this check is required because we have test id repetitions
                if (previousTestId != testId) {
                    row = new Test();
                    row.id = testId;
                    row.description = resultSet.getString(Test.DESCRIPTION);
                    row.testSections = new ArrayList<TestSection>();
                    list.add(row);

                    previousTestId = testId;
                    previousTestSectionId = -1; //reset
                    previousQuestionId = -1;    //reset
                }

                int testSectionId = resultSet.getInt(Test.TEST_SECTION_ID);
                if (previousTestSectionId != testSectionId) {
                    testSection = new TestSection();
                    testSection.id = testSectionId;
                    testSection.description = resultSet.getString(Test.TEST_SECTION_DESCRIPTION);
                    testSection.questions = new ArrayList<Question>();

                    previousTestSectionId = testSectionId;
                    row.testSections.add(testSection);
                    previousQuestionId = -1; //reset
                }

                int questionId = resultSet.getInt(Test.QUESTION_ID);
                if (previousQuestionId != questionId) {
                    question = new Question();
                    question.id = questionId;
                    question.description = resultSet.getString(Test.QUESTION_DESCRIPTION);
                    question.questionCategoryDescription = resultSet.getString(Test.QUESTION_CATEGORY_DESCRIPTION);
                    question.answers = new ArrayList<Answer>();

                    testSection.questions.add(question);
                    previousQuestionId = questionId;
                }
                Answer answer = new Answer();
                answer.id = resultSet.getInt(Test.ANSWER_ID);
                answer.description = resultSet.getString(Test.ANSWER_DESCRIPTION);
                question.answers.add(answer);
            }
            result = JacksonParser.getInstance().toJson(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
