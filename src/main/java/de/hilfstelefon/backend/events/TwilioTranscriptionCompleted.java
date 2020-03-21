package de.hilfstelefon.backend.events;

public class TwilioTranscriptionCompleted {

    public static final String EVENTNAME = "twilioTranscriptionCompletedEvent";

    private final String callSid;
    private final String recordingSid;

    public TwilioTranscriptionCompleted(String callSid, String recordingSid) {
        this.callSid = callSid;
        this.recordingSid = recordingSid;
    }

    public String getCallSid() {
        return callSid;
    }

    public String getRecordingSid() {
        return recordingSid;
    }
}
