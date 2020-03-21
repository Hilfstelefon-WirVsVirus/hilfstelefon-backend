package de.hilfstelefon.backend.events;

public class TwilioRecordingCompleted {

    public static final String EVENTNAME = "twilioRecordingCompletedEvent";

    private final String callSid;
    private final String recordingSid;

    public TwilioRecordingCompleted(String callSid, String recordingSid) {
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
