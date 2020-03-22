package de.hilfstelefon.backend.resource;

import static io.restassured.RestAssured.given;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;

import de.hilfstelefon.backend.resource.twilio.CallStatusCallback;
import de.hilfstelefon.backend.resource.twilio.IncomingWebhook;
import de.hilfstelefon.backend.resource.twilio.RecordingStatusCallback;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@Transactional
public class TwilioIntegrationTest {

    private static final String CallSid = "CA0d79906cec45b5ddaafa8a47da903b71";
    private static final String RecordingSid = "RE83d339e74e377ff8239b1eb4d2a2ec37";
    private static final String TranscriptionSid = "TR6c2dfab546e0c1fda1e9d2090ccacc4f";

    @Test
    public void testEverythingIsAwesome() {
        this.invokeIncommingWebhook();
        this.invokeCallStatusCallback();
        this.invokeRecordingStatusCallback();
        // this.invokeTranscriptionStatusCallback();

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

    // private void invokeTranscriptionStatusCallback() {
    //     given()
    //             .when()
    //             .param("CallSid", this.CallSid)
    //             .param("TranscriptionSid", this.TranscriptionSid)
    //             .post("/" + TranscriptionStatusCallback.PATH)
    //             .then()
    //             .statusCode(204);
    // }
}
