package de.hilfstelefon.backend.service;

import javax.enterprise.context.ApplicationScoped;

import com.twilio.Twilio;
import de.hilfstelefon.backend.domain.TwilioCall;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class TwilioClient {

    private Twilio twilio;

    @ConfigProperty(name = "twilio.username")
    String username;

    @ConfigProperty(name = "twilio.password")
    String password;

    @ConfigProperty(name = "twilio.account-sid")
    String accountSid;

    public TwilioClient() {
        /*
        twilio = Twilio.init(
                username,
                password,
                accountSid
        ).getRestClient();
         */
    }

    public byte[] fetchRecordedCall(TwilioCall call) {
        return null;
    }
}
