package org.wirvsvirushackathon.domain;

import javax.persistence.Entity;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Task extends PanacheEntity {

    public LocalDateTime creationDate;

    public String transcription;

    public String location;

    public Category category = Category.SUPPORT;

    public Status status = Status.OPEN;
}
