package com.petrovdevelopment.uchene.agents;

/**
 * Created by Andrey Petrov on 17-01-06.
 */
// This class is a custom agent, accepting an Object through the
// object-to-agent communication channel, and displying it on the
// standard output.
public class CustomAgent extends jade.core.Agent {

    public void setup() {
        // Accept objects through the object-to-agent communication
        // channel, with a maximum size of 10 queued objects
        setEnabledO2ACommunication(true, 10);

        // Notify blocked threads that the agent is ready and that
        // object-to-agent communication is enabled
        Object[] args = getArguments();
        if (args.length > 0) {
            CondVar latch = (CondVar) args[0];
            latch.signal();
        }

        // Add a suitable cyclic behaviour...
        addBehaviour(new jade.core.behaviours.CyclicBehaviour() {

            public void action() {
                // Retrieve the first object in the queue and print it on
                // the standard output
                Object obj = getO2AObject();
                if (obj != null) {
                    System.out.println("Got an object from the queue: [" + obj + "]");
                } else
                    block();
            }

        });
    }

    public void takeDown() {
        // Disables the object-to-agent communication channel, thus
        // waking up all waiting threads
        setEnabledO2ACommunication(false, 0);
    }

} // End of CustomAgent class