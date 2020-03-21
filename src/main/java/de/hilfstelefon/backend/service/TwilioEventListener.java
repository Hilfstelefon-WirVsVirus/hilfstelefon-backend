package de.hilfstelefon.backend.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import de.hilfstelefon.backend.domain.TwilioCall;
import de.hilfstelefon.backend.events.TwilioCallCompleted;
import de.hilfstelefon.backend.repository.TwilioCallRepository;
import io.quarkus.vertx.ConsumeEvent;

@ApplicationScoped
public class TwilioEventListener {

    @Inject
    TwilioCallRepository repository;

    @ConsumeEvent(value = TwilioCallCompleted.EVENTNAME, blocking = true)
    @Transactional
    public void onTwilioCallCompleted(TwilioCallCompleted event) {
        System.out.println("Processing call " + event.getCallSid());

        TwilioCall call = repository.getOrCreate(event.getCallSid());
        call.caller = event.getCaller();
        call.fromCity = event.getFromCity();
        call.fromZip = event.getFromZip();
        call.timestamp = event.getTimestamp();

        repository.persist(call);
    }
}
