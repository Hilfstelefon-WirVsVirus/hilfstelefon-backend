package de.hilfstelefon.backend.service;

import de.hilfstelefon.backend.domain.HelpRequest;
import de.hilfstelefon.backend.events.HelpRequestAdded;
import de.hilfstelefon.backend.events.HelpRequestAvailable;
import de.hilfstelefon.backend.repository.HelpRequestRepository;
import io.quarkus.vertx.ConsumeEvent;
import io.vertx.core.eventbus.EventBus;

import javax.inject.Inject;

public class HelpRequestEventListener {
    @Inject
    HelpRequestRepository helpRequestRepository;

    @Inject
    EventBus eventBus;

    @ConsumeEvent(value = HelpRequestAvailable.EVENTNAME)
    public void onHelpRequestAvailable(HelpRequestAvailable event) {
        HelpRequest helpRequest = new HelpRequest();

        helpRequest.location = (event.getCall().zip + " " + event.getCall().city).trim();

        // TODO fetch recording, transcription ...

        helpRequestRepository.persist(helpRequest);

        eventBus.publish(HelpRequestAdded.EVENTNAME, new HelpRequestAdded(helpRequest));

    }
}
