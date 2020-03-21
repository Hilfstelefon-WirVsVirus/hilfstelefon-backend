package de.hilfstelefon.backend.events;

public class TwilioCallCompleted {

    public static final String EVENTNAME = "twilioCallCompletedEvent";

    private final String callSid;
    private final String caller;
    private final String fromCity;
    private final String fromZip;
    private final String timestamp;

    public TwilioCallCompleted(String callSid,
                               String caller,
                               String fromCity,
                               String fromZip,
                               String timestamp) {
        this.callSid = callSid;
        this.caller = caller;
        this.fromCity = fromCity;
        this.fromZip = fromZip;
        this.timestamp = timestamp;
    }

    public String getCallSid() {
        return callSid;
    }

    public String getCaller() {
        return caller;
    }

    public String getFromCity() {
        return fromCity;
    }

    public String getFromZip() {
        return fromZip;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
