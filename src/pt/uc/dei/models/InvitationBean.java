package pt.uc.dei.models;

import pt.uc.dei.rmi_server.RmiInterface;
import pt.uc.dei.tcp_server.Message;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by bruno on 02/12/14.
 */
public class InvitationBean {
    private String meetingTitle;
    private String desiredOutcome;
    private String datetime;
    private String location;
    private String users;

    private UtilityBean utility = new UtilityBean();
    private String username;

    public void setDesiredOutcome(String desiredOutcome) {
        this.desiredOutcome = desiredOutcome;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public void setMeetingTitle(String meetingTitle) {
        this.meetingTitle = meetingTitle;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getPendingInvitations() throws RemoteException {

        List<String> l = new ArrayList<>();

        RmiInterface c = utility.connectoToRmiServer();

        Message mensagem = new Message(username, null, null, null);

        mensagem = c.viewPendingInvitations(mensagem);

        String[] aux = mensagem.data.split("\n");

        Collections.addAll(l, aux);

        return l;
    }

    public String acceptInvitation() {
        return null;
    }

    public String declineInvitation() {
        return null;
    }


}
