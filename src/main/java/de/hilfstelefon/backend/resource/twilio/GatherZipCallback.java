package de.hilfstelefon.backend.resource.twilio;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import de.hilfstelefon.backend.events.TwilioCallCompleted;
import de.hilfstelefon.backend.events.TwilioGatherTranscriptionCompleted;
import de.hilfstelefon.backend.events.TwilioTranscriptionCompleted;
import io.vertx.core.eventbus.EventBus;

@Path("/twilio/status/transcript")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class GatherZipCallback {
    public static final String PATH = "/twilio/status/transcript";

    public static final String STATUS_COMPLETED = "completed";

    @Inject
    EventBus eventBus;

    @POST
    @Transactional
    public void statusChanged(
            @FormParam("CallSid") String callSid,
            @FormParam("SpeechResult") String transcriptionText
    ) {
        eventBus.publish(TwilioGatherTranscriptionCompleted.EVENTNAME, new TwilioGatherTranscriptionCompleted(callSid, transcriptionText));
    }
}