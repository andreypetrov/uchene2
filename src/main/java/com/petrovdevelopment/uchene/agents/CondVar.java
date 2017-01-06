package com.petrovdevelopment.uchene.agents;

/**
 * Created by user1 on 17-01-06.
 */
// Simple class behaving as a Condition Variable
public class CondVar {
    private boolean value = false;

    public synchronized void waitOn() throws InterruptedException {
        while (!value) {
            wait();
        }
    }

    synchronized void signal() {
        value = true;
        notifyAll();
    }

} // End of CondVar class

