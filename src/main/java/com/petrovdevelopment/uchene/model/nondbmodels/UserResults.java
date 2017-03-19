package com.petrovdevelopment.uchene.model.nondbmodels;

import com.petrovdevelopment.uchene.db.DatabaseManager;
import com.petrovdevelopment.uchene.db.converters.ResultSetConverterToInt;
import com.petrovdevelopment.uchene.db.converters.ResultSetConverterToModel;
import com.petrovdevelopment.uchene.model.Model;
import com.petrovdevelopment.uchene.model.Test;
import com.petrovdevelopment.uchene.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * A model without a corresponding database table, kind of a db view
 * Created by Andrey Petrov on 17-03-19.
 */
public class UserResults extends Model {
    public User student;
    public int studentId;
    public int testId;
    public int questionsCount;
    public int questionsAnsweredCount;
    public int questionsAnsweredCorrectlyCount;
    public String firstAnsweredQuestionDatetime;
    public String lastAnsweredQuestionDatetime;

    public static String QUESTIONS_ANSWERED_COUNT = "QUESTIONS_ANSWERED_COUNT";
    public static String QUESTIONS_ANSWERED_CORRECTLY_COUNT = "QUESTIONS_ANSWERED_CORRECTLY_COUNT";
    public static String FIRST_ANSWERED_QUESTION_DATETIME = "FIRST_ANSWERED_QUESTION_DATETIME";
    public static String LAST_ANSWERED_QUESTION_DATETIME = "LAST_ANSWERED_QUESTION_DATETIME";

    public static String SELECT_QUESTIONS_ANSWERED_AND_CORRECTLY_ANSWERED_COUNT = "SELECT SUM(IS_CORRECT) as QUESTIONS_ANSWERED_CORRECTLY_COUNT, " +
            "COUNT(*) as QUESTIONS_ANSWERED_COUNT, " +
            "COALESCE(MIN(DATETIME), 'N/A') as FIRST_ANSWERED_QUESTION_DATETIME, " +
            "COALESCE(MAX(DATETIME), 'N/A') as LAST_ANSWERED_QUESTION_DATETIME " +
            "FROM TEST_RESULT_ANSWERS_FACTS " +
            "WHERE TEST_ID = ? AND STUDENT_ID = ?;";

    public static UserResults getResultsByStudentId(int testId, final int studentId) {
        int[] parameters = {testId, studentId};
        UserResults userResults = (UserResults) DatabaseManager.selectWithParameters(UserResults.SELECT_QUESTIONS_ANSWERED_AND_CORRECTLY_ANSWERED_COUNT, parameters, new ResultSetConverterToModel() {
            @Override
            public UserResults convert(ResultSet resultSet) throws SQLException {
                UserResults userResults = new UserResults();
                userResults.questionsAnsweredCount = resultSet.getInt(QUESTIONS_ANSWERED_COUNT);
                userResults.questionsAnsweredCorrectlyCount = resultSet.getInt(QUESTIONS_ANSWERED_CORRECTLY_COUNT);
                userResults.firstAnsweredQuestionDatetime = resultSet.getString(FIRST_ANSWERED_QUESTION_DATETIME);
                userResults.lastAnsweredQuestionDatetime = resultSet.getString(LAST_ANSWERED_QUESTION_DATETIME);
                return userResults;
            }
        });

        int[] parameters2 = {testId};
        int questionsCount = DatabaseManager.selectWithParameters(Test.SELECT_QUESTIONS_COUNT, parameters2, new ResultSetConverterToInt() {
            @Override
            public int convert(ResultSet resultSet) throws SQLException {
                return resultSet.getInt(Test.QUESTIONS_COUNT);
            }
        });

        userResults.id = testId*10000000 + studentId; //ignore this value as there is no underlying database table for UserResults. It is put here only for convenience
        userResults.testId = testId;
        userResults.studentId = studentId;
        userResults.questionsCount = questionsCount;
        return userResults;
    }

    public static List<UserResults> getResultsForAllStudents(int testId) {
        List<User> users = User.getAll();
        List<UserResults> list = new ArrayList<UserResults>();
        for (User user : users) {
            UserResults userResults = getResultsByStudentId(testId, user.id);
            list.add(userResults);
        }

        return list;
    }
}
