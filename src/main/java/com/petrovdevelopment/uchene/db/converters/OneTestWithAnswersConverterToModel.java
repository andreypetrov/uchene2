package com.petrovdevelopment.uchene.db.converters;

import com.petrovdevelopment.uchene.db.JacksonParser;
import com.petrovdevelopment.uchene.model.Answer;
import com.petrovdevelopment.uchene.model.Question;
import com.petrovdevelopment.uchene.model.Test;
import com.petrovdevelopment.uchene.model.TestSection;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Andrey Petrov on 17-03-19.
 */
public class OneTestWithAnswersConverterToModel implements ResultSetConverterToModel<Test> {
    @Override
    public Test convert(ResultSet resultSet) {
        Test result = null;
        try {
            int previousTestId = -1;
            int previousTestSectionId = -1;
            int previousQuestionId = -1;
            TestSection testSection = null;
            Question question = null;
            while (resultSet.next()) {

                int testId = resultSet.getInt(Test.ID);
                //check if a new test has started
                //this check is required because we have test id repetitions
                if (previousTestId != testId && testId != 0) {
                    result = new Test(resultSet);
                    previousTestId = testId;
                    previousTestSectionId = -1; //reset
                    previousQuestionId = -1;    //reset
                }

                int testSectionId = resultSet.getInt(Test.TEST_SECTION_ID);
                if (previousTestSectionId != testSectionId && testSectionId != 0) {
                    testSection = TestSection.createTestSectionForTest(resultSet);
                    result.testSections.add(testSection);
                    previousTestSectionId = testSectionId;
                    previousQuestionId = -1; //reset
                }

                int questionId = resultSet.getInt(Test.QUESTION_ID);
                if (previousQuestionId != questionId && questionId != 0) {
                    question = Question.createQuestionWithAnswersForTest(resultSet);
                    testSection.questions.add(question);
                    previousQuestionId = questionId;
                }

                int answerId = resultSet.getInt(Test.ANSWER_ID);
                if (answerId != 0) {
                    question.answers.add(Answer.createAnswerForTest(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
