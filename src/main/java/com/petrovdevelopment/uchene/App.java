package com.petrovdevelopment.uchene;

/**
 * Created by Andrey Petrov on 16-12-19.
 * Entry point of the application
 */
import com.petrovdevelopment.uchene.db.DatabaseManager;
import com.petrovdevelopment.uchene.db.JacksonParser;
import com.petrovdevelopment.uchene.db.SelectQueries;
import com.petrovdevelopment.uchene.resources.Resources;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class App {
    public static void main(String[] args) throws Exception {
        //start jade
        //JadeManager.getInstance().initialize();
        //start database
        DatabaseManager.getInstance().initialize();
        //start parser
        JacksonParser.getInstance().initialize();
        initJetty();
    }

    public static void initJetty () throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        Server jettyServer = new Server(8080);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);


        // Tells the Jersey Servlet which REST service/class to load.
        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.classnames",
                Resources.class.getCanonicalName());

        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }
}