package de.hilfstelefon.backend.events;

public class TwilioTranscriptionCompleted {

    public static final String EVENTNAME = "twilioTranscriptionCompletedEvent";

    private final String callSid;
    private final String transcriptionSid;

    public TwilioTranscriptionCompleted(String callSid, String transcriptionSid) {
        this.callSid = callSid;
        this.transcriptionSid = transcriptionSid;
    }

    public String getCallSid() {
        return callSid;
    }

    public String getTranscriptionSid() {
        return transcriptionSid;
    }
}
