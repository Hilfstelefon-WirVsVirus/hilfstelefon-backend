package de.hilfstelefon.backend.events;

import de.hilfstelefon.backend.domain.HelpRequest;
import org.mvel2.sh.command.basic.Help;

public class HelpRequestAdded {
    public static final String EVENTNAME = "helpRequestAddedEvent";

    HelpRequest helpRequest;

    public HelpRequestAdded (HelpRequest helpRequest) {
        this.helpRequest = helpRequest;
    }

    public HelpRequest getHelpRequest() {
        return this.helpRequest;
    }
}
