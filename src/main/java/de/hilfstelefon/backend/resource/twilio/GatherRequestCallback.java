package de.hilfstelefon.backend.resource.twilio;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import de.hilfstelefon.backend.events.TwilioGatherTranscriptionCompleted;
import io.vertx.core.eventbus.EventBus;

@Path("/twilio/status/request")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class GatherRequestCallback {
    public static final String PATH = "/twilio/status/request";

    public static final String STATUS_COMPLETED = "completed";

    @Inject
    EventBus eventBus;

    @POST
    public void statusChanged(
            @FormParam("CallSid") String callSid,
            @FormParam("SpeechResult") String request
    ) {
        eventBus.publish(TwilioGatherTranscriptionCompleted.EVENTNAME_REQUEST, new TwilioGatherTranscriptionCompleted(callSid, request));
    }
}
