package com.petrovdevelopment.uchene;

/**
 * Created by Andrey Petrov on 16-12-19.
 * Entry point of the application
 */
import com.petrovdevelopment.uchene.db.DatabaseManager;
import com.petrovdevelopment.uchene.db.JacksonParser;

public class App {
    public static void main(String[] args) throws Exception {
        //start jade
        //JadeManager.getInstance().initialize();
        //start database
        DatabaseManager.getInstance().initialize();
        //start parser
        JacksonParser.getInstance().initialize();
        //start server
        JettyManager.initialize();
    }


}