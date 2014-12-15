package pt.uc.dei.models;

import pt.uc.dei.rmi_server.RmiInterface;
import pt.uc.dei.tcp_server.Message;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by brunomartins on 27/11/14.
 */
public class MeetingBean {

    private String meetingTitle;
    private String desiredOutcome;
    private String datetime;
    private String location;
    private String users;
    private int idmeeting;
    private UtilityBean utility = new UtilityBean();
    private String username;

    public int getIdmeeting() {
        return idmeeting;
    }

    public void setIdmeeting(int idmeeting) {
        this.idmeeting = idmeeting;
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

    public void setUsers(String users) {
        this.users = users;
    }

    public void setMeetingTitle(String meetingTitle) {
        this.meetingTitle = meetingTitle;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean createMeeting() throws RemoteException {


        RmiInterface c = utility.connectoToRmiServer();

        Message mensagem = new Message(username, null, null, "createMeeting");
        mensagem.desiredoutcome = desiredOutcome;
        mensagem.location = location;
        mensagem.date = (datetime.split(" "))[0];
        mensagem.time = (datetime.split(" "))[1];
        mensagem.data = meetingTitle;
        mensagem.list = users;
        mensagem = c.createMeeting(mensagem);

        return mensagem.result;
    }

    public List<String> getInviteeList() throws RemoteException {

        List<String> l = new ArrayList<>();

        RmiInterface c = utility.connectoToRmiServer();

        Message mensagem = new Message(username, null, null, null);

        mensagem = c.listMembers(mensagem);

        String[] aux = mensagem.data.split("\n");

        Collections.addAll(l, aux);

        return l;
    }


    public List actionList(int idmeeting) throws RemoteException {
        RmiInterface c = utility.connectoToRmiServer();

        return c.actionsInAMeetingForWeb(idmeeting);


    }

    public List participantsList(int idmeeting) throws RemoteException {
        RmiInterface c = utility.connectoToRmiServer();

        return c.participantsInAMeetingForWeb(idmeeting);


    }

    public List agendaList(int idmeeting) throws RemoteException {

        RmiInterface c = utility.connectoToRmiServer();

        return c.agendaItemsInAMeetingForWeb(idmeeting);


    }


    public List allMeetingsList() throws RemoteException {

        RmiInterface c = utility.connectoToRmiServer();

        return c.listUpcomingMeetingsForWeb();


    }

    public List myMeetingsList() throws RemoteException {

        RmiInterface c = utility.connectoToRmiServer();
        return c.listMyMeetingsForWeb(username);
    }


    public List getTodoList() throws RemoteException {
        RmiInterface c = utility.connectoToRmiServer();

        return c.showToDoListForWeb(username);
    }

    public Boolean completeTask() throws RemoteException {

        RmiInterface c = utility.connectoToRmiServer();

        String idm = String.valueOf(idmeeting);

        return c.completeActionForWeb(username, idm);


    }


}

