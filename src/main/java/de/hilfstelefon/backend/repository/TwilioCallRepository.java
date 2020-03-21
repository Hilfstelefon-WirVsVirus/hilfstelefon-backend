package de.hilfstelefon.backend.repository;

import javax.enterprise.context.ApplicationScoped;

import de.hilfstelefon.backend.domain.TwilioCall;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class TwilioCallRepository implements PanacheRepository<TwilioCall> {

    public TwilioCall getOrCreate(String callSid) {
        TwilioCall twilioCall = this.get(callSid);
        if (twilioCall != null) {
            return twilioCall;
        }

        twilioCall = new TwilioCall();
        twilioCall.callsid = callSid;

        try {
            this.persistAndFlush(twilioCall);
        } catch (Exception e) {
            twilioCall = this.get(callSid);
        }

        return twilioCall;
    }

    private TwilioCall get(String callSid) {
        return find("callsid", callSid).firstResult();
    }
}
