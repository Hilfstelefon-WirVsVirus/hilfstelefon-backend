package de.hilfstelefon.backend.service;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import com.twilio.Twilio;
import com.twilio.exception.ApiConnectionException;
import com.twilio.exception.ApiException;
import com.twilio.exception.RestException;
import com.twilio.http.HttpMethod;
import com.twilio.http.Request;
import com.twilio.http.Response;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.Domains;
import com.twilio.rest.api.v2010.account.call.Recording;
import com.twilio.rest.api.v2010.account.call.RecordingFetcher;
import com.twilio.rest.api.v2010.account.recording.Transcription;
import com.twilio.rest.api.v2010.account.recording.TranscriptionFetcher;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import de.hilfstelefon.backend.domain.HelpRequest;
import de.hilfstelefon.backend.domain.TwilioCall;
import de.hilfstelefon.backend.events.HelpRequestAdded;
import de.hilfstelefon.backend.events.HelpRequestAvailable;
import de.hilfstelefon.backend.repository.HelpRequestRepository;
import io.quarkus.vertx.ConsumeEvent;
import io.vertx.core.eventbus.EventBus;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class HelpRequestEventListener {
    @Inject
    HelpRequestRepository helpRequestRepository;

    @Inject
    EventBus eventBus;

    
    @ConfigProperty(name = "twilio.password")
    String password;

    @ConfigProperty(name = "twilio.account-sid")
    String accountSid;

    @ConsumeEvent(value = HelpRequestAvailable.EVENTNAME)
    public void onHelpRequestAvailable(HelpRequestAvailable event) {
        HelpRequest helpRequest = new HelpRequest();

        helpRequest.zip = event.getCall().zip;
        helpRequest.city = event.getCall().city;

        helpRequest.audio = fetchRecordedCall(event.getCall());
        helpRequest.transcription = fetchTranscriptedCall(event.getCall());

        helpRequestRepository.persist(helpRequest);

        eventBus.publish(HelpRequestAdded.EVENTNAME, new HelpRequestAdded(helpRequest));
    }

    
    public byte[] fetchRecordedCall(final TwilioCall call) {
        Twilio.init(accountSid, password);
        final TwilioRestClient restClient = Twilio.getRestClient();
        final RecordingFetcher fetcher = new RecordingFetcher(call.callsid, call.recording_sid);
        final Recording recording = fetcher.fetch(restClient);

        final String recordingUri = recording.getUri();
        final String mp3Uri = recordingUri.replace(".json", ".mp3");

        final InputStream mp3Stream = download(restClient, mp3Uri);
        byte[] mp3 = null;
        try {
            mp3 = new byte[mp3Stream.available()];
            mp3Stream.read(mp3);
        } catch (final IOException ex) {
            ex.printStackTrace();
        }

        return mp3;
    }

    public String fetchTranscriptedCall(final TwilioCall call) {
        Twilio.init(accountSid, password);
        final TwilioRestClient restClient = Twilio.getRestClient();

        final TranscriptionFetcher fetcher = new TranscriptionFetcher(call.recording_sid, call.transcription_sid);
        Transcription trans = fetcher.fetch(restClient);
        return trans.getTranscriptionText();
    }
    
    private InputStream download(final TwilioRestClient restClient, final String uri) {
        final Request request = new Request(HttpMethod.GET, Domains.API.toString(), uri, restClient.getRegion());

        final Response response = restClient.request(request);

        if (response == null) {
            throw new ApiConnectionException("Recording fetch failed: Unable to connect to server");
        } else if (!TwilioRestClient.SUCCESS.apply(response.getStatusCode())) {
            final RestException restException = RestException.fromJson(response.getStream(),
                    restClient.getObjectMapper());
            if (restException == null) {
                throw new ApiException("Server Error, no content");
            }

            throw new ApiException(restException.getMessage(), restException.getCode(), restException.getMoreInfo(),
                    restException.getStatus(), null);
        }

        final InputStream mp3Stream = response.getStream();
        return mp3Stream;
    }
}
