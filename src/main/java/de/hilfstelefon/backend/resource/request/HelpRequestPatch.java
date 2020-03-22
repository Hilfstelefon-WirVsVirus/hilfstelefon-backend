package de.hilfstelefon.backend.resource.request;

import de.hilfstelefon.backend.domain.Status;

public class HelpRequestPatch {

    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
