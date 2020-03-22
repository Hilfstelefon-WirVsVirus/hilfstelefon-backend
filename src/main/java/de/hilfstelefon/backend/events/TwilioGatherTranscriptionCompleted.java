package de.hilfstelefon.backend.events;

public class TwilioGatherTranscriptionCompleted {
	
    public static final String EVENTNAME = "twilioTranscriptionCompletedEvent";

    private final String callSid;
    private final String transcriptionText;

    public TwilioGatherTranscriptionCompleted(String callSid, String transcriptionText) {
        this.callSid = callSid;
        this.transcriptionText = transcriptionText;
    }

    public String getCallSid() {
        return callSid;
    }

    public String getTranscriptionText() {
        return transcriptionText;
    }
}
