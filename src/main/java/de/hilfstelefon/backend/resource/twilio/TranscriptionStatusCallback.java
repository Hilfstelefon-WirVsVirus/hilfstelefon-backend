package de.hilfstelefon.backend.resource.twilio;

import de.hilfstelefon.backend.domain.TwilioCall;
import de.hilfstelefon.backend.events.TwilioCallCompleted;
import de.hilfstelefon.backend.events.TwilioRecordingCompleted;
import de.hilfstelefon.backend.events.TwilioTranscriptionCompleted;
import io.vertx.core.eventbus.EventBus;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/twilio/status/transcription")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class TranscriptionStatusCallback {

    public static final String PATH = "/twilio/status/transcription";

    @Inject
    EventBus eventBus;

    @POST
    @Transactional
    public void statusChanged(
            @FormParam("CallSid") String callSid,
            @FormParam("RecordingSid") String recordingSid
    ) {
        eventBus.publish(TwilioTranscriptionCompleted.EVENTNAME, new TwilioTranscriptionCompleted(callSid, recordingSid));
    }
}
