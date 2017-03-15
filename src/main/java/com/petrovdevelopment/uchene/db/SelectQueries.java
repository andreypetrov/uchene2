package com.petrovdevelopment.uchene.db;

import com.petrovdevelopment.uchene.model.*;

import java.sql.*;
import java.util.*;

/**
 * Created by Andrey Petrov on 17-01-09.
 */
public class SelectQueries {


//    public static PreparedStatement createStatement(String selectQuery, int studentId, int testId, Connection connection) throws SQLException {
//        PreparedStatement statement = connection.prepareStatement(selectQuery);
//        statement.setInt(1, studentId);
//        statement.setInt(2, testId);
//        return statement;
//    }


    public static String convertResultSetToString(ResultSet resultSet, String selectQuery) {
        if (User.SELECT_ALL_USERS.equals(selectQuery)) {
            return getAllUsers(resultSet);
        } else if (Test.SELECT_ALL_TESTS.equals(selectQuery)) {
            return getAllTestsWithSubsections(resultSet);
        } else if (Test.SELECT_TEST_WITH_ANSWERS.equals(selectQuery)) {
            return getTestWithAnswers(resultSet);
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

    private static String getTestWithAnswers(ResultSet resultSet) {
        String result = null;

        try {
            int previousTestId = -1;
            int previousTestSectionId = -1;
            int previousQuestionId = -1;

            Test test = null;
            TestSection testSection = null;
            Question question = null;
            while (resultSet.next()) {

                int testId = resultSet.getInt(Test.ID);
                //check if a new test has started
                //this check is required because we have test id repetitions
                if (previousTestId != testId) {
                    test = createTest(resultSet);
                    previousTestId = testId;
                    previousTestSectionId = -1; //reset
                    previousQuestionId = -1;    //reset
                }

                int testSectionId = resultSet.getInt(Test.TEST_SECTION_ID);
                if (previousTestSectionId != testSectionId) {
                    testSection = createTestSection(resultSet);
                    test.testSections.add(testSection);
                    previousTestSectionId = testSectionId;
                    previousQuestionId = -1; //reset
                }

                int questionId = resultSet.getInt(Test.QUESTION_ID);
                if (previousQuestionId != questionId) {
                    question = createQuestionWithAnswers(resultSet);
                    testSection.questions.add(question);
                    previousQuestionId = questionId;
                }
                question.answers.add(createAnswer(resultSet));
            }
            result = JacksonParser.getInstance().toJson(test);
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

            Test test = null;
            TestSection testSection = null;
            Question question = null;
            while (resultSet.next()) {

                int testId = resultSet.getInt(Test.ID);
                //check if a new test has started
                //this check is required because we have test id repetitions
                if (previousTestId != testId) {
                    test = createTest(resultSet);
                    list.add(test);
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
                    test.testSections.add(testSection);
                    previousQuestionId = -1; //reset
                }

                int questionId = resultSet.getInt(Test.QUESTION_ID);
                if (previousQuestionId != questionId) {
                    question = createQuestion(resultSet);
                    testSection.questions.add(question);
                    previousQuestionId = questionId;
                }
                question.answers.add(createAnswer(resultSet));
            }
            result = JacksonParser.getInstance().toJson(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    private static Test createTest(ResultSet resultSet) throws SQLException {
        Test row = new Test();
        row.id = resultSet.getInt(Test.ID);
        row.description = resultSet.getString(Test.DESCRIPTION);
        row.testSections = new ArrayList<TestSection>();
        return row;
    }

    private static TestSection createTestSection(ResultSet resultSet) throws SQLException {
        TestSection testSection = new TestSection();
        testSection.id = resultSet.getInt(Test.TEST_SECTION_ID);
        testSection.description = resultSet.getString(Test.TEST_SECTION_DESCRIPTION);
        testSection.questions = new ArrayList<Question>();
        return testSection;
    }

    private static Question createQuestion(ResultSet resultSet) throws SQLException {
        Question question = new Question();
        question.id = resultSet.getInt(Test.QUESTION_ID);
        question.description = resultSet.getString(Test.QUESTION_DESCRIPTION);
        question.questionCategoryDescription = resultSet.getString(Test.QUESTION_CATEGORY_DESCRIPTION);
        question.imageUrl = resultSet.getString(Test.IMAGE_URL);
        question.answers = new ArrayList<Answer>();
        return question;
    }

    private static Question createQuestionWithAnswers(ResultSet resultSet) throws SQLException {
        Question question = new Question();
        question.id = resultSet.getInt(Test.QUESTION_ID);
        question.description = resultSet.getString(Test.QUESTION_DESCRIPTION);
        question.questionCategoryDescription = resultSet.getString(Test.QUESTION_CATEGORY_DESCRIPTION);
        question.imageUrl = resultSet.getString(Test.IMAGE_URL);
        question.givenAnswerId = resultSet.getInt(Test.GIVEN_ANSWER);
        question.isAnswered = question.givenAnswerId != 0;
        question.isCorrect = resultSet.getBoolean(Test.IS_CORRECT);
        question.answers = new ArrayList<Answer>();
        return question;
    }

    private static Answer createAnswer(ResultSet resultSet) throws SQLException {
        Answer answer = new Answer();
        answer.id = resultSet.getInt(Test.ANSWER_ID);
        answer.description = resultSet.getString(Test.ANSWER_DESCRIPTION);
        return answer;
    }
}