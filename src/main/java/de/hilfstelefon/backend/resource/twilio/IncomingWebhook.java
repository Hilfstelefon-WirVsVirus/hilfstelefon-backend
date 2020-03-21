package de.hilfstelefon.backend.resource.twilio;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Gather;
import com.twilio.twiml.voice.Hangup;
import com.twilio.twiml.voice.Record;
import com.twilio.twiml.voice.Say;
import de.hilfstelefon.backend.domain.TwilioConfig;
import com.twilio.twiml.voice.Dial;
import com.twilio.twiml.voice.Number;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.TwiMLException;
import com.twilio.http.HttpMethod;
import java.util.Arrays;

@Path("/twilio/incoming")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_XML)
public class IncomingWebhook {

    @POST
    public String incomingCall(@FormParam("FromCity") String city, @FormParam("FromZip") String zip) {
        VoiceResponse.Builder builder = new VoiceResponse.Builder();

        System.out.println("Incoming call!");

        builder.say(new Say.Builder("Hallo, du brauchst Hilfe? Hinterlasse uns dein Anliegen gleich nach dem Piepton.")
                .language(Say.Language.DE_DE)
                .build());

        // TODO: Gather only if ZIP code/city is not recognized
        Gather gather = new Gather.Builder()
                .numDigits(5)
                .say(new Say.Builder("Bitte tippen Sie Ihre Postleitzahl ein.")
                        .language(Say.Language.DE_DE)
                        .build())
                .build();

        // TODO: url?
        Number number = new Number.Builder(TwilioConfig.PHONE_NUMBER)
                .statusCallback("https://www.hilfstelefon.de/twilio/status")
                .statusCallbackMethod(HttpMethod.POST)
                .statusCallbackEvents(Arrays.asList(Number.Event.COMPLETED)).build();

        builder.record(new Record.Builder().build())
                .gather(gather)
                .dial(new Dial.Builder().number(number).build())
                .hangup(new Hangup.Builder().build());


        return builder.build().toXml();
    }
}
