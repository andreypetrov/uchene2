package com.petrovdevelopment.uchene.model;

import com.petrovdevelopment.uchene.db.DatabaseManager;
import com.petrovdevelopment.uchene.db.JacksonParser;
import com.petrovdevelopment.uchene.db.Queries;
import com.petrovdevelopment.uchene.db.converters.ResultSetConverterToModel;
import com.petrovdevelopment.uchene.db.converters.ResultSetConverterToModelList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey Petrov on 17-01-09.
 */
public class Question extends Model {
    public static String TABLE = "QUESTION";

    public static String IMAGE_URL = "IMAGE_URL";
    public static String CORRECT_ANSWER_ID = "CORRECT_ANSWER_ID";

//    public static String SELECT_COUNT = "Select count(*) from TEST_RESULT_ANSWERS_FACTS WHERE ID=? AND CORRECT_ANSWER_ID=?";

    public String questionCategoryDescription;
    public boolean isAnswered;
    public int givenAnswerId;
    public int correctAnswerId;
    public boolean isCorrect;
    public String imageUrl;

    public AnswerPool answerPool;
    public Answer correctAnswer;
    public List<Answer> answers;


    public static Question createQuestionWithCorrectAnswerRevealed(ResultSet resultSet) throws SQLException {
        Question question = new Question();
        question.id = resultSet.getInt(ID);
        question.description = resultSet.getString(DESCRIPTION);
        question.imageUrl = resultSet.getString(IMAGE_URL);
        question.correctAnswerId = resultSet.getInt(CORRECT_ANSWER_ID);
        return question;

    }


    public static Question createQuestionForTest(ResultSet resultSet) throws SQLException {
        Question question = new Question();
        question.id = resultSet.getInt(Test.QUESTION_ID);
        question.description = resultSet.getString(Test.QUESTION_DESCRIPTION);
        question.questionCategoryDescription = resultSet.getString(Test.QUESTION_CATEGORY_DESCRIPTION);
        question.imageUrl = resultSet.getString(Test.QUESTION_IMAGE_URL);
        question.correctAnswerId = resultSet.getInt(Test.QUESTION_CORRECT_ANSWER_ID);
        return question;
    }

    public static Question createQuestionWithAnswersForTest(ResultSet resultSet) throws SQLException {
        Question question = createQuestionForTest(resultSet);
        question.givenAnswerId = resultSet.getInt(Test.GIVEN_ANSWER);
        question.isAnswered = question.givenAnswerId != 0;
        question.correctAnswerId = resultSet.getInt(Test.QUESTION_CORRECT_ANSWER_ID);
        question.isCorrect = resultSet.getBoolean(Test.IS_CORRECT);
        question.answers = new ArrayList<Answer>();
        return question;
    }

    public static List<Question> getAll() {
        final String query = Queries.SELECT_ALL_FROM_PREFIX + TABLE;
        return DatabaseManager.select(query, new ResultSetConverterToModelList<Question>() {
            @Override
            public List<Question> convert(ResultSet resultSet) throws SQLException {
                List<Question> result = new ArrayList<Question>();
                try {
                    while (resultSet.next()) {
                        Question question = createQuestionWithCorrectAnswerRevealed(resultSet);
                        result.add(question);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return result;
            }
        });
    }

    public static Question getById(int questionId) {
        final String query = Queries.SELECT_ALL_FROM_PREFIX + TABLE + Queries.WHERE_ID_SUFFIX;
        int[] idArray = {questionId};
        return (Question) DatabaseManager.selectWithParameters(query, idArray, new ResultSetConverterToModel() {
            @Override
            public Question convert(ResultSet resultSet) throws SQLException {
                Question question = createQuestionWithCorrectAnswerRevealed(resultSet);
                return question;
            }
        });
    }


    public static boolean isAnswerCorrect(int questionId, int answerId) {
        Question question = getById(questionId);
        return question.correctAnswerId == answerId;
    }


}
