package com.petrovdevelopment.uchene.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Andrey Petrov on 17-01-09.
 */
public class Answer extends Model {
    public static String TABLE  = "ANSWER";

    public AnswerPool answerPool;

    public static Answer createAnswerForTest(ResultSet resultSet) throws SQLException {
        Answer answer = new Answer();
        answer.id = resultSet.getInt(Test.ANSWER_ID);
        answer.description = resultSet.getString(Test.ANSWER_DESCRIPTION);
        return answer;
    }
}
