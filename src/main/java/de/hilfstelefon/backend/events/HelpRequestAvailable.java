package de.hilfstelefon.backend.events;

import de.hilfstelefon.backend.domain.TwilioCall;

public class HelpRequestAvailable {

    public static final String EVENTNAME = "twilioCallImportCompletedEvent";

    // TODO should be interface instead of concrete entity... yolo
    private final TwilioCall twilioCall;

    public HelpRequestAvailable(TwilioCall twilioCall) {
        this.twilioCall = twilioCall;
    }

    public TwilioCall getCall() {
        return twilioCall;
    }
}
