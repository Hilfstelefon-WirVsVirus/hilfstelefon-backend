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
import de.hilfstelefon.backend.repository.TwilioCallRepository;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/twilio/incoming")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_XML)
public class IncomingWebhook {

    public static final String PATH = "twilio/incoming";

    @ConfigProperty(name = "hilfstelefon.url")
    String callbackUrl;

    @ConfigProperty(name = "twilio.phone-number")
    String phoneNumber;

    @POST
    public String incomingCall(
            @FormParam("CallSid") String callSid,
            @FormParam("Caller") String caller,
            @FormParam("FromCity") String fromCity,
            @FormParam("FromZip") String fromZip
    ) {
        System.out.printf("Incoming call %s from %s %s!\n", callSid, fromZip, fromCity);

        return createResponse().toXml();
    }

    private VoiceResponse createResponse() {
        VoiceResponse.Builder builder = new VoiceResponse.Builder();

        Say sayGreeting = new Say.Builder("Hallo, du brauchst Hilfe?")
                .language(Say.Language.DE_DE)
                .build();

        // TODO: Gather only if ZIP code/city is not recognized
        Gather gatherZip = new Gather.Builder()
                .action(GatherZipCallback.PATH)
                .numDigits(5)
                .timeout(10)
                .speechTimeout("10")
                .language(Gather.Language.DE_DE)
                .inputs(Gather.Input.DTMF)
                .say(
                        new Say.Builder("Bitte gib uns deine Postleitzahl durch drücken der entsprechenden Tasten auf deinem Telefon.")
                                .language(Say.Language.DE_DE)
                                .build())
                .build();
        
        Gather gatherRequest = new Gather.Builder()
                .action(GatherRequestCallback.PATH)
                .timeout(120)
                .speechTimeout("120")
                .language(Gather.Language.DE_DE)
                .inputs(Gather.Input.SPEECH)
                .say(
                        new Say.Builder("Vielen Dank. Bitte nenn uns nun dein Anliegen.")
                                .language(Say.Language.DE_DE)
                                .build())
                .build();

        Number number = new Number.Builder(phoneNumber)
                .statusCallback(this.getCallbackUrl(CallStatusCallback.PATH))
                .statusCallbackMethod(HttpMethod.POST)
                .statusCallbackEvents(Collections.singletonList(Number.Event.COMPLETED))
                .build();

        Record record = new Record.Builder()
                .recordingStatusCallback(this.getCallbackUrl(RecordingStatusCallback.PATH))
                .recordingStatusCallbackMethod(HttpMethod.POST)
                .recordingStatusCallbackEvents(Collections.singletonList(Record.RecordingEvent.COMPLETED))
                .playBeep(false)
                .transcribe(false) //Only works with english
                //.transcribeCallback(this.getCallbackUrl(TranscriptionStatusCallback.PATH))
                .build();

        builder.say(sayGreeting)
                .gather(gatherZip)
                .record(record)
                .gather(gatherRequest)
                .dial(new Dial.Builder().number(number).build()) //TODO remove? useless?
                .hangup(new Hangup.Builder().build());

        return builder.build();
    }

    private String getCallbackUrl(String path) {
        return this.callbackUrl + "/" + path;
    }
}
