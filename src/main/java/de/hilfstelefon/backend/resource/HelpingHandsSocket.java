package de.hilfstelefon.backend.resource;

import de.hilfstelefon.backend.domain.HelpRequest;
import de.hilfstelefon.backend.events.HelpRequestAdded;
import de.hilfstelefon.backend.repository.HelpRequestRepository;
import io.quarkus.vertx.ConsumeEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/helping-hands/{username}")
@ApplicationScoped
public class HelpingHandsSocket {
    Map<String, Session> sessions = new ConcurrentHashMap<>();

    @Inject
    HelpRequestRepository helpRequestRepository;

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        sessions.put(username, session);
    }

    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        sessions.remove(username);
    }

    @OnError
    public void onError(Session session, @PathParam("username") String username, Throwable throwable) {
        sessions.remove(username);
    }

    @ConsumeEvent(HelpRequestAdded.EVENTNAME)
    private void broadcast(HelpRequest helpRequest) {

        System.out.println("Time to party!!!!!");

        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(helpRequest, result ->  {
                if (result.getException() != null) {
                    System.out.println("Unable to send message: " + result.getException());
                }
            });
        });
    }
}
