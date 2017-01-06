package com.petrovdevelopment.uchene.agents;

import com.petrovdevelopment.uchene.resources.ResourcesCallback;

/**
 * Created by Andrey Petrov on 17-01-06.
 */
public class AgentMessage {
    public AgentMessage(String message, ResourcesCallback resourcesCallback) {
        this.message = message;
        this.resourcesCallback = resourcesCallback;
    }

    public String message;
    public ResourcesCallback resourcesCallback;
}
