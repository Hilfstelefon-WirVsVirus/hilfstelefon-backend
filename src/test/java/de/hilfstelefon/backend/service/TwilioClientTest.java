package de.hilfstelefon.backend.service;

import de.hilfstelefon.backend.domain.TwilioCall;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@QuarkusTest
class TwilioClientTest {

    @Test
    void fetchRecordedCall() {
        TwilioClient twilioClient = new TwilioClient();
        twilioClient.fetchRecordedCall(this.createTestCall());
    }

    private TwilioCall createTestCall() {
        TwilioCall call = new TwilioCall();
        call.callsid = "CAab570cc846ddd75aa77c62e24b826c25";

        return call;
    }
}