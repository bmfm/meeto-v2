package pt.uc.dei.websockets;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;


@ServerEndpoint(value = "/ws", configurator = GetHttpSessionConfigurator.class)
public class WebSocketAnnotation {
    private static final AtomicInteger sequence = new AtomicInteger(1);
    private static final Set<WebSocketAnnotation> users = new CopyOnWriteArraySet<>();
    private String username;
    private Session session;

    public WebSocketAnnotation() {

    }

    @OnOpen
    public void start(Session session, EndpointConfig config) {

        this.session = session;
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.username = httpSession.getAttribute("username").toString();


        String message = "*" + username + "* connected.";
        sendMessage(message);
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

        sendMessage("[" + username + "] " + message);
    }

    @OnError
    public void handleError(Throwable t) {
        t.printStackTrace();
    }

    private void sendMessage(String text) {
        // uses *this* object's session to call sendText()
        try {
            for (WebSocketAnnotation user : users) {
                user.session.getBasicRemote().sendText(text);
            }
        } catch (IOException e) {
            // clean up once the WebSocket connection is closed
            try {
                this.session.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
