package de.hilfstelefon.backend.resource;

import de.hilfstelefon.backend.repository.TwilioCallRepository;
import de.hilfstelefon.backend.resource.twilio.CallStatusCallback;
import de.hilfstelefon.backend.resource.twilio.IncomingWebhook;
import de.hilfstelefon.backend.resource.twilio.RecordingStatusCallback;
import de.hilfstelefon.backend.resource.twilio.TranscriptionStatusCallback;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static io.restassured.RestAssured.given;

@QuarkusTest
@Transactional
public class TwilioIntegrationTest {

    private static final String CallSid = "CA0d79906cec45b5ddaafa8a47da903b71";
    private static final String RecordingSid = "CA0d79906cec45b5ddaafa8a47da903b71";
    private static final String TranscriptionSid = "CA0d79906cec45b5ddaafa8a47da903b71";

    @Inject
    TwilioCallRepository twilioCallRepository;

    @Test
    public void testEverythingIsAwesome() {
        this.invokeIncommingWebhook();
        this.invokeCallStatusCallback();
        this.invokeRecordingStatusCallback();
        this.invokeTranscriptionStatusCallback();

        given()
                .when()
                .get("/" + HelpRequestResource.PATH)
                .then()
                .statusCode(200);
    }

    private void invokeIncommingWebhook() {
        given()
                .when()
                .param("CallSid", this.CallSid)
                .param("Caller", "0123456789")
                .param("FromCity", "Rotenburg")
                .param("FromZip", "27356")
                .post("/" + IncomingWebhook.PATH)
                .then()
                .statusCode(200);
    }

    private void invokeCallStatusCallback() {
        given()
                .when()
                .param("CallSid", this.CallSid)
                .param("Caller", "0123456789")
                .param("FromCity", "Rotenburg")
                .param("FromZip", "27356")
                .param("Timestamp", "Sat, 21 Mar 2020 20:55:35 +0000")
                .param("CallStatus", CallStatusCallback.STATUS_COMPLETED)
                .post("/" + CallStatusCallback.PATH)
                .then()
                .statusCode(204);
    }

    private void invokeRecordingStatusCallback() {
        given()
                .when()
                .param("CallSid", this.CallSid)
                .param("RecordingSid", this.RecordingSid)
                .post("/" + RecordingStatusCallback.PATH)
                .then()
                .statusCode(204);
    }

    private void invokeTranscriptionStatusCallback() {
        given()
                .when()
                .param("CallSid", this.CallSid)
                .param("TranscriptionSid", this.TranscriptionSid)
                .post("/" + TranscriptionStatusCallback.PATH)
                .then()
                .statusCode(204);
    }
}
