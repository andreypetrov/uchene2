/**
 * Created by Andrey Petrov on 16-12-19.
 */
package com.petrovdevelopment.uchene.resources;

import com.petrovdevelopment.uchene.db.JacksonParser;
import com.petrovdevelopment.uchene.model.Question;
import com.petrovdevelopment.uchene.model.Test;
import com.petrovdevelopment.uchene.model.TestResultAnswersFacts;
import com.petrovdevelopment.uchene.model.User;
import com.petrovdevelopment.uchene.model.nondbmodels.UserResults;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/rest")
@Api(value = "/rest", description = "Operations for tests")
public class Resources {
    public static final String JSON_UTF = MediaType.APPLICATION_JSON + "; charset=UTF-8";

    public static String stringify(List list) {
        return JacksonParser.getInstance().toJson(list);
    }

    /* JADE AGENT tests. TODO reintroduce jade later
    @GET
    @Path("test")
    @Produces(JSON_UTF)
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
    @Produces(JSON_UTF)
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
    */

    @GET
    @Path("users")
    @Produces(JSON_UTF)
    @ApiOperation(value = "Returns a list of all users and their roles",
            response = User.class,
            responseContainer = "List")
    public String users() {
        return stringify(User.getAll());
    }


    @GET
    @Path("tests")
    @Produces(JSON_UTF)
    @ApiOperation(value = "Returns a list of all tests, or if a testId and stundentId is specified then only a single test",
            notes = "Either a single test or a list of all tests",
            response = Test.class,
            responseContainer = "List")
    public String getTests(@QueryParam("testId") int testId,
                           @QueryParam("studentId") int studentId,
                           @QueryParam("maxAnswers") int maxAnswers,
                           @QueryParam("shuffleAnswers") boolean shuffleAnswers) {
        if (testId != 0 && studentId != 0) {
            Test test = Test.getAllWithAnswers(studentId, testId);
            if (shuffleAnswers) {
                test.shuffleAnswers();
            }
            if (maxAnswers != 0) {
                test.filterAnswers(maxAnswers);
            }
            if (shuffleAnswers) {//shuffle again, because correct answer is first otherwise
                test.shuffleAnswers();
            }
            return test.toString();
        } else {
            return stringify(Test.getAll());
        }
    }

    @GET
    @Path("questions")
    @Produces(JSON_UTF)
    @ApiOperation(value = "Returns a list of all questions or a single question if questionId is specified",
            response = Question.class,
            responseContainer = "List")
    public String questions(@QueryParam("questionId") int questionId) {
        if (questionId != 0) {
            return Question.getById(questionId).toString();
        } else {
            return stringify(Question.getAll());
        }
    }

    @GET
    @Path("results")
    @Produces(JSON_UTF)
    @ApiOperation(value = "Returns a list of all student results for a given test or for one student if studentId is specified",
            response = UserResults.class,
            responseContainer = "List")
    public String results(@NotNull @QueryParam("testId") int testId,
                         @QueryParam("studentId") int studentId) {
        if (testId == 0) return "Please specify test id";

        if (studentId != 0) {
            return UserResults.getResultsByStudentId(testId, studentId).toString();
        } else {
            return stringify(UserResults.getResultsForAllStudents(testId));
        }
    }


    @GET
    @Path("giveAnswer")
    @Produces(JSON_UTF)
    @ApiOperation(value = "Answer a question. Returns 1 if success and -1 in case of failure",
            notes = "This GET method is given only for testing convenience. In your app use the POST version of it",
            response = Integer.class)
    public String answer(@NotNull @QueryParam("testId") int testId,
                         @NotNull @QueryParam("studentId") int studentId,
                         @NotNull @QueryParam("questionId") int questionId,
                         @NotNull @QueryParam("answerId") int answerId) {
        if (testId != 0 && studentId != 0 && questionId != 0 && answerId != 0) {
            int result = TestResultAnswersFacts.insert(testId, studentId, questionId, answerId);
            return String.valueOf(result);
        } else {
            return "Please enter required parameters testId, studentId, questionId and answerId";
        }
    }

    @POST
    @Path("giveAnswer")
    @Produces(JSON_UTF)
    @ApiOperation(value = "Answer a question. Returns 1 if success and -1 in case of failure",
            response = Integer.class)
    public String answerPost(@NotNull @QueryParam("testId") int testId,
                             @NotNull @QueryParam("studentId") int studentId,
                             @NotNull @QueryParam("questionId") int questionId,
                             @NotNull @QueryParam("answerId") int answerId) {
        return answer(testId, studentId, questionId, answerId);
    }
}
