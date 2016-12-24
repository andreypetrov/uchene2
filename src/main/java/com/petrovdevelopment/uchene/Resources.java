/**
 * Created by Andrey Petrov on 16-12-19.
 */
package com.petrovdevelopment.uchene;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/entry-point")
public class Resources {

    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public String test() {
        return "Test";
    }

    @GET
    @Path("test2")
    @Produces(MediaType.APPLICATION_JSON)
    public String test2() {
        return "Test 2";
    }
}
