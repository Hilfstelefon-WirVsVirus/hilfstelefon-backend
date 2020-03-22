package de.hilfstelefon.backend.resource.twilio;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.Collections;

import com.twilio.http.HttpMethod;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Dial;
import com.twilio.twiml.voice.Gather;
import com.twilio.twiml.voice.Hangup;
import com.twilio.twiml.voice.Number;
import com.twilio.twiml.voice.Record;
import com.twilio.twiml.voice.Say;
import de.hilfstelefon.backend.events.TwilioGatherTranscriptionCompleted;
import io.vertx.core.eventbus.EventBus;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/twilio")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_XML)
public class IncomingWebhook {

    public static final String PATH = "twilio/incoming";

    @ConfigProperty(name = "hilfstelefon.url")
    String callbackUrl;

    @ConfigProperty(name = "twilio.phone-number")
    String phoneNumber;

    @Inject
    EventBus eventBus;

    private static final String GATHER_ZIP_PATH = "/twilio/status/zip";
    private static final String GATHER_REQUEST_PATH = "/twilio/status/request";

    @POST
    @Path("/incoming")
    public String incomingCall(@FormParam("CallSid") String callSid,
                               @FormParam("Caller") String caller,
                               @FormParam("FromCity") String fromCity,
                               @FormParam("FromZip") String fromZip) {
        VoiceResponse.Builder builder = new VoiceResponse.Builder();
        introduction(builder);
        gatherRequest(builder);
        endCall(builder);

        return builder.build().toXml();
    }

    @POST
    @Path("/status/zip")
    public String gatherZipCallback(@FormParam("CallSid") String callSid,
                                      @FormParam("Digits") String zip) {
        eventBus.publish(TwilioGatherTranscriptionCompleted.EVENTNAME_ZIP, new TwilioGatherTranscriptionCompleted(callSid, zip));

        VoiceResponse.Builder builder = new VoiceResponse.Builder();
        gatherRequest(builder);
        endCall(builder);

        return builder.build().toXml();
    }

    @POST
    @Path("/status/request")
    public String gatherRequestCallback(@FormParam("CallSid") String callSid,
                                          @FormParam("SpeechResult") String request) {
        eventBus.publish(TwilioGatherTranscriptionCompleted.EVENTNAME_REQUEST, new TwilioGatherTranscriptionCompleted(callSid, request));

        VoiceResponse.Builder builder = new VoiceResponse.Builder();
        endCall(builder);

        return builder.build().toXml();
    }

    private void introduction(VoiceResponse.Builder builder) {
        builder.say(new Say.Builder("Hallo, du brauchst Hilfe?")
                .language(Say.Language.DE_DE)
                .build());

        builder.gather(new Gather.Builder()
                .action(GATHER_ZIP_PATH)
                .numDigits(5)
                .timeout(10)
                .speechTimeout("10")
                .language(Gather.Language.DE_DE)
                .inputs(Gather.Input.DTMF)
                .say(
                        new Say.Builder("Bitte gib uns deine Postleitzahl durch drücken der entsprechenden Tasten auf deinem Telefon.")
                                .language(Say.Language.DE_DE)
                                .build())
                .build());
    }

    private void gatherRequest(VoiceResponse.Builder builder) {
        builder.record(new Record.Builder()
                .recordingStatusCallback(this.getCallbackUrl(RecordingStatusCallback.PATH))
                .recordingStatusCallbackMethod(HttpMethod.POST)
                .recordingStatusCallbackEvents(Collections.singletonList(Record.RecordingEvent.COMPLETED))
                .playBeep(false)
                .transcribe(false) //Only works with english
                //.transcribeCallback(this.getCallbackUrl(TranscriptionStatusCallback.PATH))
                .build());

        builder.gather(new Gather.Builder()
                .action(GATHER_REQUEST_PATH)
                .timeout(120)
                .speechTimeout("120")
                .language(Gather.Language.DE_DE)
                .inputs(Gather.Input.SPEECH)
                .say(
                        new Say.Builder("Vielen Dank. Bitte nenn uns nun dein Anliegen.")
                                .language(Say.Language.DE_DE)
                                .build())
                .build());
    }

    private void endCall(VoiceResponse.Builder builder) {
        //TODO remove? useless?
        builder.dial(
                new Dial.Builder().number(
                        new Number.Builder(phoneNumber)
                                .statusCallback(this.getCallbackUrl(CallStatusCallback.PATH))
                                .statusCallbackMethod(HttpMethod.POST)
                                .statusCallbackEvents(Collections.singletonList(Number.Event.COMPLETED))
                                .build()).build());

        builder.hangup(new Hangup.Builder().build());
    }

    private String getCallbackUrl(String path) {
        return this.callbackUrl + "/" + path;
    }
}
