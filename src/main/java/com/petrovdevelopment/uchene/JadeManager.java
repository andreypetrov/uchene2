package com.petrovdevelopment.uchene;

import com.petrovdevelopment.uchene.agents.CondVar;
import com.petrovdevelopment.uchene.agents.CustomAgent;
import jade.core.*;
import jade.core.Runtime;
import jade.wrapper.*;
import jade.wrapper.AgentContainer;

/**
 * Created by Andrey Petrov on 17-01-05.
 */
public class JadeManager {
    public static JadeManager instance;
    public static Runtime runtime;
    public static AgentContainer mainContainer;

    public static JadeManager getInstance() {
        if (instance == null) {
            instance = new JadeManager();
        }
        return instance;
    }


    public AgentController getAgent(String localAgentName) throws ControllerException{
        try {
          return mainContainer.getAgent(localAgentName, false);
        } catch (ControllerException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void initialize() {//TODO keep state here if needed, otherwise we can make this method static
        // Get a hold on JADE runtime
        runtime = Runtime.instance();
        // Exit the JVM when there are no more containers around
        runtime.setCloseVM(true);
        // Launch a complete platform on the 7777 port
        // create a default Profile
        Profile pMain = new ProfileImpl(null, 7777, null);

        System.out.println("Launching a whole in-process platform..." + pMain);
        mainContainer = runtime.createMainContainer(pMain);

        // set now the default Profile to start a container
        ProfileImpl pContainer = new ProfileImpl(null, 7777, null);

        System.out.println("Launching the rma agent on the main container ...");
        AgentController rma = null;
        try {
            //Start the remote agent management GUI
            rma = mainContainer.createNewAgent("rma", "jade.tools.rma.rma", new Object[0]);
            rma.start();

            // Launch a custom agent, taking an object via the
            // object-to-agent communication channel. Notice how an Object
            // is passed to the agent, to achieve a startup synchronization:
            // this Object is used as a POSIX 'condvar' or a Win32
            // 'EventSemaphore' object...

            CondVar startUpLatch = new CondVar();

            AgentController customAgent = mainContainer.createNewAgent("customAgent", CustomAgent.class.getName(), new Object[]{startUpLatch});
            customAgent.start();

            // Wait until the agent starts up and notifies the Object
            startUpLatch.waitOn();


            // Put an object in the queue, asynchronously
            System.out.println("Inserting an object, asynchronously...");
            customAgent.putO2AObject("Message 1", AgentController.ASYNC);
            System.out.println("Inserted.");

            // Put an object in the queue, synchronously
            System.out.println("Inserting an object, synchronously...");
            customAgent.putO2AObject(mainContainer, AgentController.SYNC);
            System.out.println("Inserted.");

        } catch (StaleProxyException e) {
            e.printStackTrace();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }


    }
}
