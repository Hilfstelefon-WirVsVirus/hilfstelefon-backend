package de.hilfstelefon.backend.repository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import java.time.LocalDateTime;
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

    @PostConstruct
    @Transactional
    public void init() {
        // Persist some dummy data to the database for the development phase
        persist(
                createHelpRequest("Moin. Ich bräuchte Hilfe beim Einkauf.", "Bremen", "28203"),
                createHelpRequest("Hallo. Kann mir jemand frisches Obst bringen?", "Hamburg", "20146")
        );
    }

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

    @Transactional
    public HelpRequest updateStatus(HelpRequest helpRequest, Status status) {
        helpRequest.status = status;
        update("status = ?1 WHERE id = ?2", helpRequest.status, helpRequest.id);

        return helpRequest;
    }

    private HelpRequest createHelpRequest(String transcription, String city, String zip) {
        HelpRequest helpRequest = new HelpRequest();
        helpRequest.creationDate = LocalDateTime.now();
        helpRequest.transcription = transcription;
        helpRequest.city = city;
        helpRequest.zip = zip;

        return helpRequest;
    }
}
