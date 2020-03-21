package de.hilfstelefon.backend.resource.twilio;

//import static spark.Spark.post;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/*import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Gather;
import com.twilio.twiml.voice.Hangup;
import com.twilio.twiml.voice.Record;
import com.twilio.twiml.voice.Say;*/

@Path("/twilio/incoming")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class IncomingWebhook {

    @POST
    public void incomingCall() {
/*
        post("/answer", (req, res) -> {
        	Say instructions = new Say.Builder("Hello. Please leave a message after the beep.").build();

            // Use <Record> to record the caller's message
            Record record = new Record.Builder().build();
            
            Gather gather = new Gather.Builder()
            .numDigits(5)
            .say(new Say.Builder("Please enter your zip code.").build())
            .build();

            // End the call with <Hangup>
            Hangup hangup = new Hangup.Builder().build();
            

            // Create a TwiML builder object
            VoiceResponse twiml = new VoiceResponse.Builder()
                .say(instructions)
                .gather(gather)
                .record(record)
                .hangup(hangup)
                .build();

            System.out.println(twiml.toXml());

            return twiml.toXml();
        });
*/
    }
}
