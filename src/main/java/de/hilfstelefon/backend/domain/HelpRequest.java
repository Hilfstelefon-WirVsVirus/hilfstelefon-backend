package de.hilfstelefon.backend.domain;

import javax.persistence.Entity;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class HelpRequest extends PanacheEntity {

    public LocalDateTime creationDate;

    public String city;

    public String zip;

    public String transcription;

    public byte[] audio;

    public Category category = Category.SUPPORT;

    public Status status = Status.OPEN;
}
