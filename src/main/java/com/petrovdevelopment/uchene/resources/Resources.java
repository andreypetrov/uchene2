/**
 * Created by Andrey Petrov on 16-12-19.
 */
package com.petrovdevelopment.uchene.resources;

import com.petrovdevelopment.uchene.JadeManager;
import com.petrovdevelopment.uchene.agents.AgentMessage;
import com.petrovdevelopment.uchene.db.SelectQueries;
import jade.wrapper.ControllerException;
import jade.wrapper.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

@Path("/entry-point")
public class Resources {

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
            customAgent.putO2AObject( new AgentMessage(message, new ResourcesCallback() {
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
        SelectQueries.selectAllUsers();
        return "users";
    }
}
