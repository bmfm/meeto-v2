package pt.uc.dei.actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import pt.uc.dei.models.MeetingBean;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * Created by bruno on 22/11/14.
 */
public class MeetingAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    public List<String> list;
    Boolean outcome;
    MeetingBean meetingBean = new MeetingBean();
    private String req = null;
    private String checkboxes;
    private String meetingTitle = null;
    private Map<String, Object> session;
    private String desiredOutcome = null;
    private String datetime = null;
    private String location = null;
    private String users = null;

    public String getReq() {
        return req;
    }

    public void setReq(String req) {
        this.req = req;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public String getCheckboxes() {
        return checkboxes;
    }

    public void setCheckboxes(String checkboxes) {
        this.checkboxes = checkboxes;
    }

    public void setMeetingTitle(String meetingTitle) {
        this.meetingTitle = meetingTitle;
    }


    public String execute() throws RemoteException {

        String membersToInvite = "";

        String aux = "";
        List<String> check = Arrays.asList(checkboxes.split(","));


        for (String checkbox : check) {
            aux = checkbox.split("\t\t")[0];
            membersToInvite += aux;
        }

        /*for (Map.Entry<Integer, String> entry : checkboxes.entrySet())
        {
            String aux = entry.getValue().split(" ")[0];
            membersToInvite += aux;

        }*/

        meetingBean.setMeetingTitle(meetingTitle);
        meetingBean.setDatetime(datetime);
        meetingBean.setLocation(location);
        meetingBean.setDesiredOutcome(desiredOutcome);
        meetingBean.setUsers(membersToInvite);
        meetingBean.setUsername((String) session.get("username"));

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

    public String getInviteeList() throws Exception {

        meetingBean.setUsername((String) session.get("username"));

        list = meetingBean.getInviteeList();

        return SUCCESS;
    }
}
