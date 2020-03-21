package de.hilfstelefon.backend.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class TwilioCall extends PanacheEntity {

    public String callsid;
    public String phonenumber;
    public String city;
    public String zip;
    public String timestamp;
}
