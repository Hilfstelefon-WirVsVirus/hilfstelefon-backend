package de.hilfstelefon.backend.repository;

import javax.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import de.hilfstelefon.backend.domain.HelpRequest;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class HelpRequestRepository implements PanacheRepository<HelpRequest> {

    // A lot of useful default methods are automatically generated
    // TODO: Add specific non-generic methods

    public List<HelpRequest> getExamples() {
        List<HelpRequest> helpRequests = new ArrayList<>();
        helpRequests.add(createTask());
        helpRequests.add(createTask());
        helpRequests.add(createTask());

        return helpRequests;
    }

    private HelpRequest createTask() {
        HelpRequest helpRequest = new HelpRequest();
        helpRequest.creationDate = LocalDateTime.now();
        helpRequest.transcription = "Lorem Ipsum";
        helpRequest.location = "Hamburg";

        return helpRequest;
    }
}
