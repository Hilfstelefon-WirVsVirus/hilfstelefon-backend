package de.hilfstelefon.backend.resource;

import de.hilfstelefon.backend.domain.HelpRequest;
import de.hilfstelefon.backend.repository.HelpRequestRepository;

import javax.inject.Inject;
import javax.websocket.server.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/help-request/audio/{id}")
@Produces(MediaType.APPLICATION_OCTET_STREAM)
public class HelpRequestAudioResource {

    @Inject
    HelpRequestRepository helpRequestRepository;

    /*
    @Inject
    HelpRequestEventListener  helpRequestEventListener;
    */

    @GET
    public Response getAudio(@PathParam("id") Long helpRequestId) {
        Optional<HelpRequest> helpRequest = this.helpRequestRepository.find("id", helpRequestId).firstResultOptional();
        if (!helpRequest.isPresent()) {
            throw new NotFoundException();
        }

        /*
        TwilioCall call = new TwilioCall();
        call.callsid = "CAab570cc846ddd75aa77c62e24b826c25";
        call.recording_sid = "RE83d339e74e377ff8239b1eb4d2a2ec37";
        call.transcription_sid = "TR6c2dfab546e0c1fda1e9d2090ccacc4f";
        byte[] bytes = this.helpRequestEventListener.fetchRecordedCall(call);
        */

        byte[] bytes = helpRequest.get().audio;

        return Response.ok().entity(bytes).build();
    }
}
