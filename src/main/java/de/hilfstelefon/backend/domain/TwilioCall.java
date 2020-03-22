package de.hilfstelefon.backend.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class TwilioCall extends PanacheEntity {

    public String callsid;
    public String recording_sid;
    public String transcription_sid;

    public String phonenumber;
    public String city;
    public String zip;
    public String timestamp;

    public boolean isTranscriptionCompleted() {
        return this.transcription_sid != null && !this.transcription_sid.isEmpty();
    }

    public boolean isRecordingCompleted() {
        return this.recording_sid != null && !this.recording_sid.isEmpty();
    }
}
