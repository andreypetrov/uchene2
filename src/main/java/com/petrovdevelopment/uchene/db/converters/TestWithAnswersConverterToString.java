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
public class TestWithAnswersConverterToString implements ResultSetConverterToString {
    @Override
    public String convertToString(ResultSet resultSet) {
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
                if (previousTestId != testId && testId != 0) {
                    test = new Test(resultSet);
                    previousTestId = testId;
                    previousTestSectionId = -1; //reset
                    previousQuestionId = -1;    //reset
                }

                int testSectionId = resultSet.getInt(Test.TEST_SECTION_ID);
                if (previousTestSectionId != testSectionId && testSectionId != 0) {
                    testSection = TestSection.createTestSectionForTest(resultSet);
                    test.testSections.add(testSection);
                    previousTestSectionId = testSectionId;
                    previousQuestionId = -1; //reset
                }

                int questionId = resultSet.getInt(Test.QUESTION_ID);
                if (previousQuestionId != questionId && questionId != 0) {
                    question = Question.createQuestionWithAnswersForTest(resultSet);
                    testSection.questions.add(question);
                    previousQuestionId = questionId;
                }
                question.answers.add(Answer.createAnswerForTest(resultSet));
            }
            result = JacksonParser.getInstance().toJson(test);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
