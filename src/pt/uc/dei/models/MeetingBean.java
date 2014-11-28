package pt.uc.dei.models;

import pt.uc.dei.rmi_server.RmiInterface;
import pt.uc.dei.tcp_server.Message;

import java.rmi.RemoteException;

/**
 * Created by brunomartins on 27/11/14.
 */
public class MeetingBean {

    private String meetingTitle;
    private String desiredOutcome;
    private String datetime;
    private String location;
    private String users;
    private String time;
    private String date;
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

    public Boolean createMeeting() throws RemoteException {

        date = (datetime.split(" "))[1];

        RmiInterface c = utility.connectoToRmiServer();

        Message mensagem = new Message(username, null, null, "createMeeting");
        mensagem.desiredoutcome = desiredOutcome;
        mensagem.location = location;
        mensagem.date = (datetime.split(" "))[0];
        mensagem.time = (datetime.split(" "))[1];
        mensagem.data = meetingTitle;
        //TODO receber e passar lsita de users como deve ser
        mensagem.list = "1,2,3";
        mensagem = c.createMeeting(mensagem);

        return mensagem.result;
    }
}
