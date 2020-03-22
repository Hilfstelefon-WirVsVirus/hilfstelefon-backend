package de.hilfstelefon.backend.resource.twilio;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/twilio/failure")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_XML)
public class FailureWebhook {
    @POST
    public Response failed()
    {
        System.out.println("Failed!");
        return Response.ok().build();
    }
}
