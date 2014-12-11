package pt.uc.dei.websockets;

import org.h2.engine.Session;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;


@ServerEndpoint(value = "/notifications", configurator = GetHttpSessionConfigurator.class)
public class NotificationsWebSocket {
    public static final Set<NotificationsWebSocket> users = new CopyOnWriteArraySet<>();
    private static final AtomicInteger sequence = new AtomicInteger(1);
    private String username;
    private Session session;

    public NotificationsWebSocket() {

    }

    @OnOpen
    public void start(Session session, EndpointConfig config) {

        this.session = session;
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.username = httpSession.getAttribute("username").toString();
        users.add(this);
    }

    @OnClose
    public void end() {
        // clean up once the WebSocket connection is closed
        users.remove(this);
    }

    @OnMessage
    public void receiveMessage(String message) {
        // one should never trust the client, and sensitive HTML
        // characters should be replaced with &lt; &gt; &quot; &amp;
        sendMessage(message);
    }

    @OnError
    public void handleError(Throwable t) {
        t.printStackTrace();
    }

    private void sendMessage(String text) {
        // uses *this* object's session to call sendText()
        this.session.getBasicRemote().sendText(text);
    }
}
