package de.hilfstelefon.backend.service;

import de.hilfstelefon.backend.domain.TwilioCall;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class TwilioClientTest {

    @Test
    void fetchRecordedCall() {
        TwilioClient twilioClient = new TwilioClient();
        twilioClient.fetchRecordedCall(this.createTestCall());
    }

    private TwilioCall createTestCall() {
        TwilioCall call = new TwilioCall();
        call.callSid = "CAab570cc846ddd75aa77c62e24b826c25";
        call.accountSid = "ACb1d198ab96bb32bc506f9ee5878839b4";

        return call;
    }
}