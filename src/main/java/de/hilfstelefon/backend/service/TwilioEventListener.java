package de.hilfstelefon.backend.service;

import de.hilfstelefon.backend.events.TwilioCallCompleted;
import io.quarkus.vertx.ConsumeEvent;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TwilioEventListener {

    @ConsumeEvent(TwilioCallCompleted.EVENTNAME)
    public void onTwilioCallCompleted(TwilioCallCompleted event) {
        System.out.println("Processing call " + event.getCall().callSid);
    }
}
