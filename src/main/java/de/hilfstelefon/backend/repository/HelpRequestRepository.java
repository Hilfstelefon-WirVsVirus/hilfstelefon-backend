package de.hilfstelefon.backend.repository;

import javax.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import de.hilfstelefon.backend.domain.HelpRequest;
import de.hilfstelefon.backend.domain.Status;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class HelpRequestRepository implements PanacheRepository<HelpRequest> {

    public List<HelpRequest> find(String city, String zip, Status status) {
        Map<String, Object> parameters = new HashMap<>();
        if (city != null && !city.isEmpty()) parameters.put("city", city);
        if (zip != null && !zip.isEmpty()) parameters.put("zip", zip);
        if (status != null) parameters.put("status", status);

        Sort defaultSort = Sort.ascending("creationdate");
        if (parameters.isEmpty()) {
            return listAll(defaultSort);
        }

        String query = parameters.keySet().stream()
                .map(key -> key + " = :" + key)
                .collect(Collectors.joining(" and "));

        return list(query, defaultSort, parameters);
    }

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
        helpRequest.city = "Bremen";
        helpRequest.zip = "28203";

        return helpRequest;
    }
}
