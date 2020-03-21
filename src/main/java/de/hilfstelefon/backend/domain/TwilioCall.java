package de.hilfstelefon.backend.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class TwilioCall extends PanacheEntity {

    public String callSid;
    public String caller;
    public String fromCity;
    public String fromZip;
    public String timestamp;
}
