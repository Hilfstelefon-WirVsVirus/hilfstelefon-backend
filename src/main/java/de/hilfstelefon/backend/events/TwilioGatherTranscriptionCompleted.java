package de.hilfstelefon.backend.events;

public class TwilioGatherTranscriptionCompleted {
	
    private static final String EVENTNAME = "twilioTranscriptionCompletedEvent";

    public static final String EVENTNAME_ZIP = EVENTNAME + "Zip";
    public static final String EVENTNAME_REQUEST = EVENTNAME + "Request";

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
