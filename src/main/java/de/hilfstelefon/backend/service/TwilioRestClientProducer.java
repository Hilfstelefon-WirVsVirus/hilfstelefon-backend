package de.hilfstelefon.backend.service;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import com.twilio.Twilio;
import com.twilio.http.TwilioRestClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class TwilioRestClientProducer {

    @ConfigProperty(name = "twilio.password")
    String password;

    @ConfigProperty(name = "twilio.account-sid")
    String accountSid;

    @Produces
    public TwilioRestClient produceTwilioRestClient() {
        Twilio.init(accountSid, password);

        return Twilio.getRestClient();
    }
}
