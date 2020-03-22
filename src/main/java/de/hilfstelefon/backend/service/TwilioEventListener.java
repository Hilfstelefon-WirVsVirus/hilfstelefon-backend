package de.hilfstelefon.backend.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import de.hilfstelefon.backend.domain.TwilioCall;
import de.hilfstelefon.backend.events.HelpRequestAvailable;
import de.hilfstelefon.backend.events.TwilioCallCompleted;
import de.hilfstelefon.backend.events.TwilioGatherTranscriptionCompleted;
import de.hilfstelefon.backend.events.TwilioRecordingCompleted;
import de.hilfstelefon.backend.events.TwilioTranscriptionCompleted;
import de.hilfstelefon.backend.repository.TwilioCallRepository;
import io.quarkus.vertx.ConsumeEvent;
import io.vertx.core.eventbus.EventBus;

@ApplicationScoped
public class TwilioEventListener {

    @Inject
    TwilioCallRepository repository;

    @Inject
    EventBus eventBus;

    @ConsumeEvent(value = TwilioCallCompleted.EVENTNAME, blocking = true)
    @Transactional
    public void onTwilioCallCompleted(TwilioCallCompleted event) {
        System.out.println("Processing call completed: " + event.getCallSid());

        TwilioCall call = repository.getOrCreate(event.getCallSid());
        call.phonenumber = event.getCaller();
        call.city = event.getFromCity();
        call.zip = event.getFromZip();
        call.timestamp = event.getTimestamp();

        repository.persist(call);

        this.publishHelpRequestAvailableOnDemand(call);
    }

    @ConsumeEvent(value = TwilioTranscriptionCompleted.EVENTNAME, blocking = true)
    @Transactional
    public void onTwilioTranscriptionCompleted(TwilioTranscriptionCompleted event) {
        System.out.println("Processing transcription completed: " + event.getCallSid());

        TwilioCall call = repository.getOrCreate(event.getCallSid());
        call.transcription_sid = event.getTranscriptionSid();

        repository.persist(call);

        this.publishHelpRequestAvailableOnDemand(call);
    }
    
    @ConsumeEvent(value = TwilioGatherTranscriptionCompleted.EVENTNAME, blocking = true)
    @Transactional
    public void onTwilioGatherTranscriptionCompleted(TwilioGatherTranscriptionCompleted event) {
        System.out.println("Processing gather transcription completed: " + event.getCallSid());

        TwilioCall call = repository.getOrCreate(event.getCallSid());
        call.transcription_text = event.getTranscriptionText();

        repository.persist(call);

        this.publishHelpRequestAvailableOnDemand(call);
    }

    @ConsumeEvent(value = TwilioRecordingCompleted.EVENTNAME, blocking = true)
    @Transactional
    public void onTwilioRecordingCompleted(TwilioRecordingCompleted event) {
        System.out.println("Processing recording completed: " + event.getCallSid());

        TwilioCall call = repository.getOrCreate(event.getCallSid());
        call.recording_sid = event.getRecordingSid();

        repository.persist(call);

        this.publishHelpRequestAvailableOnDemand(call);
    }

    private boolean publishHelpRequestAvailableOnDemand(TwilioCall twilioCall) {
        if (!twilioCall.isRecordingCompleted() || !twilioCall.isTranscriptionCompleted()) {
            return false;
        }

        System.out.println("Call complete: " + twilioCall.callsid);
        eventBus.publish(HelpRequestAvailable.EVENTNAME, new HelpRequestAvailable(twilioCall));

        return true;
    }
}
