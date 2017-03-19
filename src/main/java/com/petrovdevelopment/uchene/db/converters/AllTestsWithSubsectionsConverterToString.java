package com.petrovdevelopment.uchene.db.converters;

import com.petrovdevelopment.uchene.db.JacksonParser;
import com.petrovdevelopment.uchene.model.Answer;
import com.petrovdevelopment.uchene.model.Question;
import com.petrovdevelopment.uchene.model.Test;
import com.petrovdevelopment.uchene.model.TestSection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey Petrov on 17-03-19.
 */

public class AllTestsWithSubsectionsConverterToString implements ResultSetConverterToString {

    @Override
    public String convertToString(ResultSet resultSet) {
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
                if (previousTestId != testId && testId != 0) {
                    test = new Test(resultSet);
                    list.add(test);
                    previousTestId = testId;
                    previousTestSectionId = -1; //reset
                    previousQuestionId = -1;    //reset
                }

                int testSectionId = resultSet.getInt(Test.TEST_SECTION_ID);
                if (previousTestSectionId != testSectionId && testSectionId != 0) {
                    testSection = TestSection.createTestSectionForTest(resultSet);
                    previousTestSectionId = testSectionId;
                    test.testSections.add(testSection);
                    previousQuestionId = -1; //reset
                }

                int questionId = resultSet.getInt(Test.QUESTION_ID);
                if (previousQuestionId != questionId  && questionId != 0) {
                    question = Question.createQuestionForTest(resultSet);
                    testSection.questions.add(question);
                    previousQuestionId = questionId;
                }
            }
            result = JacksonParser.getInstance().toJson(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
