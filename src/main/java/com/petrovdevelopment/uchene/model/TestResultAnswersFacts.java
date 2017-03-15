package com.petrovdevelopment.uchene.model;

import com.petrovdevelopment.uchene.db.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Andrey Petrov on 17-01-09.
 */
public class TestResultAnswersFacts {
    public static String INSERT = "INSERT INTO TEST_RESULT_ANSWERS_FACTS"
            + "(TEST_ID, STUDENT_ID, QUESTION_ID, ANSWER_ID, IS_CORRECT, DATETIME) VALUES"
            + "(?,?,?,?,?,DateTime('now'))";

    public static String SELECT = "Select * from TEST_RESULT_ANSWERS_FACTS";


    public static int insert(int testId, int studentId, int questionId, int answerId) {
        try {
            PreparedStatement preparedStatement = null;
            preparedStatement = DatabaseManager.getInstance().getDBConnection().prepareStatement(TestResultAnswersFacts.INSERT);
            preparedStatement.setInt(1, testId);
            preparedStatement.setInt(2, studentId);
            preparedStatement.setInt(3, questionId);
            preparedStatement.setInt(4, answerId);
            preparedStatement.setInt(5, Question.isAnswerCorrect(questionId, answerId) ? 1 : 0);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }


    public int testResultId;
    public int questionId;
    public int answerId;
    public boolean isCorrect;
    public String datetime;
}
