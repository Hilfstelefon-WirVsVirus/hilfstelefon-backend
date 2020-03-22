package de.hilfstelefon.backend.resource.twilio;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.hilfstelefon.backend.events.TwilioRecordingCompleted;
import io.vertx.core.eventbus.EventBus;

@Path("/twilio/status/recording")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class RecordingStatusCallback {

    public static final String PATH = "twilio/status/recording";

    @Inject
    EventBus eventBus;

    @POST
    public Response statusChanged(
            @FormParam("CallSid") String callSid,
            @FormParam("RecordingSid") String recordingSid
    ) {
        eventBus.publish(TwilioRecordingCompleted.EVENTNAME, new TwilioRecordingCompleted(callSid, recordingSid));
        return Response.ok().build();
    }
}
