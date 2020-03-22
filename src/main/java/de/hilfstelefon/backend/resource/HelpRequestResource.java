package de.hilfstelefon.backend.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

import de.hilfstelefon.backend.domain.HelpRequest;
import de.hilfstelefon.backend.domain.Status;
import de.hilfstelefon.backend.repository.HelpRequestRepository;
import de.hilfstelefon.backend.resource.request.HelpRequestPatch;

@Path("/help-request")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HelpRequestResource {

    @Inject
    HelpRequestRepository helpRequestRepository;

    @GET
    public List<HelpRequest> getAll(@QueryParam("city") String city,
                                    @QueryParam("zip") String zip,
                                    @QueryParam("status") Status status) {
        return helpRequestRepository.find(city, zip, status);
    }

    @PATCH
    @Path("/{request_id}")
    public Response patch(@PathParam("request_id") Long requestId,
                          HelpRequestPatch helpRequestPatch) {
        HelpRequest helpRequest = helpRequestRepository.findById(requestId);
        if (helpRequest == null) {
            return Response.status(404).build();
        }

        helpRequest.status = helpRequestPatch.getStatus();
        helpRequestRepository.persist(helpRequest);

        return Response.status(200).entity(helpRequest).build();
    }
}
