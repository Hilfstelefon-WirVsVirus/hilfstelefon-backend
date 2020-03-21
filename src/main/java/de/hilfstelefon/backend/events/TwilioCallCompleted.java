package de.hilfstelefon.backend.events;

import de.hilfstelefon.backend.domain.TwilioCall;

public class TwilioCallCompleted {
    public static final String EVENTNAME = "twilioCallCompletedEvent";

    private TwilioCall twilioCall;

    public TwilioCallCompleted(TwilioCall twilioCall) {
        this.twilioCall = twilioCall;
    }

    public TwilioCall getCall() {
        return twilioCall;
    }
}
