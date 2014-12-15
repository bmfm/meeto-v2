package pt.uc.dei.websockets;


import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
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

    public static void sendInvitations(List<String> u) {

        for (NotificationsWebSocket userWebSocket : users) {
            for (String s : u) {
                if (userWebSocket.username.equals(s)) {
                    try {
                        userWebSocket.getSession().getBasicRemote().sendText("You have been invited! Check you pending invitations"); //fazer get do text da mensagem que é para enviar
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }

        }


    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
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
        try {
            this.session.getBasicRemote().sendText(text);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
