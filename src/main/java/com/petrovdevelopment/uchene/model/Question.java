package com.petrovdevelopment.uchene.model;

import java.util.List;

/**
 * Created by Andrey Petrov on 17-01-09.
 */
public class Question extends Model {
    public static String SELECT_COUNT = "Select count(*) from TEST_RESULT_ANSWERS_FACTS WHERE ID=? AND CORRECT_ANSWER_ID=?";


    public static boolean isAnswerCorrect(int questionId, int answerId) {
        //TODO implement proper logic
        return true;
    }

    public String questionCategoryDescription;
    public boolean isAnswered;
    public int givenAnswerId;
    public boolean isCorrect;
    public String imageUrl;

    public AnswerPool answerPool;
    public Answer correctAnswer;
    public List<Answer> answers;
}
