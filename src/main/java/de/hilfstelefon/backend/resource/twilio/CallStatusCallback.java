package de.hilfstelefon.backend.resource.twilio;

import de.hilfstelefon.backend.domain.TwilioCall;
import de.hilfstelefon.backend.events.TwilioCallCompleted;
import io.vertx.core.eventbus.EventBus;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/twilio/status/call")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class CallStatusCallback {

    public static final String PATH = "/twilio/status/call";

    private static final String STATUS_COMPLETED = "completed";

    @Inject
    EventBus eventBus;

    @POST
    @Transactional
    public void statusChanged(
            @FormParam("CallSid") String callSid,
            @FormParam("AccountSid") String accountSid,
            @FormParam("Caller") String caller,
            @FormParam("FromCity") String fromCity,
            @FormParam("FromZip") String fromZip,
            @FormParam("Timestamp") String timestamp,
            @FormParam("CallStatus") String callStatus
    ) {
        if (! callStatus.equals(CallStatusCallback.STATUS_COMPLETED)) {
            System.out.println("Unexpected status update: " + callStatus);
            return;
        }

        TwilioCall call = new TwilioCall();
        call.callSid = callSid;
        call.accountSid = accountSid;
        call.caller = caller;
        call.fromCity = fromCity;
        call.fromZip = fromZip;
        call.timestamp = timestamp;

        call.persist();

        eventBus.publish(TwilioCallCompleted.EVENTNAME, new TwilioCallCompleted(call));
    }
}
