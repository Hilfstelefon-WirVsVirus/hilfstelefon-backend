package de.hilfstelefon.backend.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.hilfstelefon.backend.domain.HelpRequest;
import de.hilfstelefon.backend.domain.Status;
import de.hilfstelefon.backend.repository.HelpRequestRepository;

@Path("/help-request")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HelpRequestResource {

    @Inject
    HelpRequestRepository helpRequestRepository;

    @GET
    public java.util.List<HelpRequest> getAll(@QueryParam("city") String city,
                                              @QueryParam("zip") String zip,
                                              @QueryParam("status") Status status) {
        return helpRequestRepository.find(city, zip, status);
    }
}
