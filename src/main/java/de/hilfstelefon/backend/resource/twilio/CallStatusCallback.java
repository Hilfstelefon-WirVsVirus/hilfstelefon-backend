package de.hilfstelefon.backend.resource.twilio;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.hilfstelefon.backend.events.TwilioCallCompleted;
import io.vertx.core.eventbus.EventBus;

@Path("/twilio/status/call")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class CallStatusCallback {

    public static final String PATH = "twilio/status/call";

    public static final String STATUS_COMPLETED = "completed";

    @Inject
    EventBus eventBus;

    @POST
    public Response statusChanged(
            @FormParam("CallSid") String callSid,
            @FormParam("Caller") String caller,
            @FormParam("FromCity") String fromCity,
            @FormParam("FromZip") String fromZip,
            @FormParam("Timestamp") String timestamp,
            @FormParam("CallStatus") String callStatus
    ) {
        if (callStatus.equals(CallStatusCallback.STATUS_COMPLETED)) {
            eventBus.publish(TwilioCallCompleted.EVENTNAME, new TwilioCallCompleted(
                    callSid,
                    caller,
                    fromCity,
                    fromZip,
                    timestamp));
        }

        return Response.ok().build();
    }
}
