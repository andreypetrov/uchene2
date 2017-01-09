package com.petrovdevelopment.uchene.model;

import java.util.List;

/**
 * Created by Andrey Petrov on 17-01-09.
 */
public class Question extends Model {
    public String questionCategoryDescription;
    public AnswerPool answerPool;
    public Answer correctAnswer;
    public List<Answer> answers;
}
