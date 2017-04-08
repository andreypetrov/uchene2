package com.petrovdevelopment.uchene;

import com.petrovdevelopment.uchene.resources.Resources;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.config.DefaultJaxrsConfig;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Created by Andrey Petrov on 17-02-20.
 *
 * Creates a jetty server with two handlers - one for static resources and another for rest api
 */
public class JettyManager {

    public static void initialize() throws Exception {

        Server jettyServer = new Server(8080);
        jettyServer.setHandler(createHandlers());
        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }

    private static HandlerList createHandlers() {
        Handler[] handlers = new Handler[2];
        handlers[0] = createStaticResourcesHandler();
        handlers[1] = createRestHandler();
        HandlerList handlerList = new HandlerList();
        handlerList.setHandlers(handlers);
        return handlerList;
    }

    private static Handler createStaticResourcesHandler() {
        ResourceHandler staticResourceHandler = new ResourceHandler();
        staticResourceHandler.setResourceBase("./src/public/");
        ContextHandler staticContextHandler = new ContextHandler("/");
        staticContextHandler.setHandler(staticResourceHandler);
        return staticResourceHandler;
    }

    private static  Handler createRestHandler () {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/rest/*");
        jerseyServlet.setInitOrder(0);

        // Tells the Jersey Servlet which REST service/class to load.
        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.classnames",
                Resources.class.getCanonicalName());

        // Setup Swagger
        jerseyServlet.setInitParameter("jersey.config.server.provider.packages", "io.swagger.jaxrs.json;io.swagger.jaxrs.listing");

        ServletHolder swaggerServlet = context.addServlet(DefaultJaxrsConfig.class, "/swagger-core");
        swaggerServlet.setInitOrder(1);
        swaggerServlet.setInitParameter("api.version", "1.0.0");

        return context;
    }


}
