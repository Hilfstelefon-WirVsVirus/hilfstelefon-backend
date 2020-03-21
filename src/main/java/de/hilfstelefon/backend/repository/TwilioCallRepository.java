package de.hilfstelefon.backend.repository;

import javax.enterprise.context.ApplicationScoped;

import de.hilfstelefon.backend.domain.TwilioCall;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class TwilioCallRepository implements PanacheRepository<TwilioCall> {

    public TwilioCall getOrCreate(String callSid) {
        TwilioCall twilioCall = find("callsid", callSid).firstResult();
        if (twilioCall != null) {
            return twilioCall;
        }

        twilioCall = new TwilioCall();
        twilioCall.callSid = callSid;

        return twilioCall;
    }
}
