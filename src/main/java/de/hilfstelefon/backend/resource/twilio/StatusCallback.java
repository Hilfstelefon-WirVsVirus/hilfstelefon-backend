package de.hilfstelefon.backend.resource.twilio;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/twilio/status")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_XML)
public class StatusCallback {

    @POST
    public void statusChanged(@FormParam("RecordingSid") String recordingSid) {
        System.out.println("Status changed!");
    }
}
