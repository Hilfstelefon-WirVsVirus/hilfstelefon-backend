package de.hilfstelefon.backend.domain;

import javax.persistence.Entity;

import java.time.LocalDateTime;
import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class HelpRequest extends PanacheEntity {

    public UUID id;

    public LocalDateTime creationDate;

    public String city;

    public String zip;

    public String transcription;

    public Category category = Category.SUPPORT;

    public Status status = Status.OPEN;

    public HelpRequest() {
        this.id = UUID.randomUUID();
    }
}
