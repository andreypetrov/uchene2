/**
 * Created by Andrey Petrov on 16-12-19.
 */
package com.petrovdevelopment.uchene.resources;

import com.petrovdevelopment.uchene.JadeManager;
import com.petrovdevelopment.uchene.agents.AgentMessage;
import com.petrovdevelopment.uchene.db.JacksonParser;
import com.petrovdevelopment.uchene.model.Question;
import com.petrovdevelopment.uchene.model.Test;
import com.petrovdevelopment.uchene.model.TestResultAnswersFacts;
import com.petrovdevelopment.uchene.model.User;
import com.petrovdevelopment.uchene.model.nondbmodels.UserResults;
import jade.wrapper.ControllerException;
import jade.wrapper.*;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/rest")
public class Resources {

    public static String stringify(List list) {
        return JacksonParser.getInstance().toJson(list);
    }

    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public String test(@QueryParam("message") String message) {
        try {
            AgentController customAgent = JadeManager.getInstance().getAgent("customAgent");
            System.out.println("Inserting an object, asynchronously...");
            customAgent.putO2AObject(message, AgentController.ASYNC);
            System.out.println("Inserted.");

            //
            //return mainContainer.getAgent(localAgentName, false);
            return "Inserted";
        } catch (ControllerException e) {
            e.printStackTrace();
            return e.getStackTrace().toString();
        }
    }

    @GET
    @Path("test2")
    @Produces(MediaType.APPLICATION_JSON)
    public void test2(@QueryParam("message") String message, @Suspended final AsyncResponse asyncResponse) {
        try {
            AgentController customAgent = JadeManager.getInstance().getAgent("customAgent");
            System.out.println("Inserting an object, asynchronously...");
            customAgent.putO2AObject(new AgentMessage(message, new ResourcesCallback() {
                @Override
                public void onResponse(String message) {
                    asyncResponse.resume(message);
                }
            }), AgentController.ASYNC);
            System.out.println("Inserted.");
        } catch (ControllerException e) {
            e.printStackTrace();
            asyncResponse.resume(e.getStackTrace().toString());
        }
    }

    @GET
    @Path("users")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUsers() {
        return stringify(User.getAll());
    }

    @GET
    @Path("tests")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTests(@QueryParam("testId") int testId,
                           @QueryParam("studentId") int studentId,
                           @QueryParam("maxAnswers") int maxAnswers) {

        if (testId != 0 && studentId != 0) {
            Test test = Test.getAllWithAnswers(studentId, testId);
            if (maxAnswers != 0) {
                test.filterAnswersCountAndShuffleAnswers(maxAnswers);
            }
            return test.toString();
        } else {
            return stringify(Test.getAll());
        }
    }

    @GET
    @Path("questions")
    @Produces(MediaType.APPLICATION_JSON)
    public String answer(@QueryParam("questionId") int questionId) {
        if (questionId != 0) {
            return Question.getById(questionId).toString();
        } else {
            return stringify(Question.getAll());
        }
    }

    @GET
    @Path("results")
    @Produces(MediaType.APPLICATION_JSON)
    public String answer(@QueryParam("testId") int testId,
                         @QueryParam("studentId") int studentId) {
        if (testId == 0) return "Please specify test id";

        if (studentId !=0 ) {
            return UserResults.getResultsByStudentId(testId, studentId).toString();
        } else {
            return stringify(UserResults.getResultsForAllStudents(testId));
        }
    }



    @GET
    @Path("giveAnswer")
    @Produces(MediaType.APPLICATION_JSON)
    public String answer(@QueryParam("testId") int testId,
                         @QueryParam("studentId") int studentId,
                         @QueryParam("questionId") int questionId,
                         @QueryParam("answerId") int answerId) {
        if (testId != 0 && studentId != 0 && questionId != 0 && answerId != 0) {
            int result = TestResultAnswersFacts.insert(testId, studentId, questionId, answerId);
            return String.valueOf(result);
        } else {
            return "Please enter required parameters testId, studentId, questionId and answerId";
        }
    }
    @POST
    @Path("giveAnswer")
    @Produces(MediaType.APPLICATION_JSON)
    public String answerPost(@QueryParam("testId") int testId,
                         @QueryParam("studentId") int studentId,
                         @QueryParam("questionId") int questionId,
                         @QueryParam("answerId") int answerId) {
        return answer(testId, studentId, questionId, answerId);
    }
}
