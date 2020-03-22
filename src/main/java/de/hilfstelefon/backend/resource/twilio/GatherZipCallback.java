package de.hilfstelefon.backend.resource.twilio;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.hilfstelefon.backend.events.TwilioGatherTranscriptionCompleted;
import io.vertx.core.eventbus.EventBus;

@Path("/twilio/status/zip")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class GatherZipCallback {
    public static final String PATH = "/twilio/status/zip";

    public static final String STATUS_COMPLETED = "completed";

    @Inject
    EventBus eventBus;

    @POST
    public Response statusChanged(
            @FormParam("CallSid") String callSid,
            @FormParam("Digits") String zip
    ) {
        eventBus.publish(TwilioGatherTranscriptionCompleted.EVENTNAME_ZIP, new TwilioGatherTranscriptionCompleted(callSid, zip));
        return Response.ok().build();
    }
}
