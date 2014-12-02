package pt.uc.dei.models;

import pt.uc.dei.rmi_server.RmiInterface;
import pt.uc.dei.tcp_server.Message;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by bruno on 02/12/14.
 */
public class InvitationBean {
    private String meetingTitle;
    private String desiredOutcome;
    private String datetime;
    private String location;
    private String meetings;
    private UtilityBean utility = new UtilityBean();
    private String username;

    public String getMeetings() {
        return meetings;
    }

    public void setMeetings(String meetings) {
        this.meetings = meetings;
    }

    public void setDesiredOutcome(String desiredOutcome) {
        this.desiredOutcome = desiredOutcome;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public void setMeetingTitle(String meetingTitle) {
        this.meetingTitle = meetingTitle;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List getPendingInvitations() throws RemoteException {

        RmiInterface c = utility.connectoToRmiServer();

        return c.viewPendingInvitationsForDataStructure(username);


    }

    public Boolean acceptInvitation() throws RemoteException {

        RmiInterface c = utility.connectoToRmiServer();

        Message mensagem = new Message(username, null, null, "acceptMeetings");

        mensagem.list = meetings;
        mensagem = c.acceptMeeting(mensagem);


        return mensagem.result;


    }

    public Boolean declineInvitation() {
        return null;
    }


}
