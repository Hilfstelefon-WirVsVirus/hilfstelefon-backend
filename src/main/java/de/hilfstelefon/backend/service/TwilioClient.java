package de.hilfstelefon.backend.service;

import com.twilio.Twilio;
import com.twilio.http.TwilioRestClient;
import de.hilfstelefon.backend.domain.TwilioCall;
import de.hilfstelefon.backend.domain.TwilioConfig;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class TwilioClient {

    private Twilio twilio;

    public TwilioClient() {
        /*
        twilio = Twilio.init(
                TwilioConfig.USERNAME,
                TwilioConfig.PASSWORD,
                TwilioConfig.ACCOUNT_SID
        ).getRestClient();
         */
    }

    public byte[] fetchRecordedCall(TwilioCall call) {
        return null;
    }
}
