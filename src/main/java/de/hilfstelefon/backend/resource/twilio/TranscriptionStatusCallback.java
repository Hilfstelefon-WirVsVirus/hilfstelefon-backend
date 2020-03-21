package de.hilfstelefon.backend.resource.twilio;

import de.hilfstelefon.backend.domain.TwilioCall;
import de.hilfstelefon.backend.events.TwilioCallCompleted;
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
        System.out.println("Transcription status updated");
    }
}
