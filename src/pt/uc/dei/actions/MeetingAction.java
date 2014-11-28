package pt.uc.dei.actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import pt.uc.dei.models.MeetingBean;

import java.rmi.RemoteException;
import java.util.Map;


/**
 * Created by bruno on 22/11/14.
 */
public class MeetingAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    Boolean outcome;
    private String meetingTitle = null;
    private Map<String, Object> session;

    private String desiredOutcome = null;
    private String datetime = null;
    private String location = null;
    private String users = null;

    public void setMeetingTitle(String meetingTitle) {
        this.meetingTitle = meetingTitle;
    }


    public String execute() throws RemoteException {
        MeetingBean meetingBean = new MeetingBean();


        meetingBean.setMeetingTitle(meetingTitle);
        meetingBean.setDatetime(datetime);
        meetingBean.setLocation(location);
        meetingBean.setDesiredOutcome(desiredOutcome);
        meetingBean.setUsers(users);
        meetingBean.setUsername((String) session.get("username"));
        System.out.println((String) session.get("username"));

        outcome = meetingBean.createMeeting();

        if (outcome) {
            addActionMessage("Meeting created!");
        } else {
            addActionError("Meeting not created, something's wrong");
        }


        return SUCCESS;

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


    public void setSession(Map<String, Object> session) {
        this.session = session;

    }
}
