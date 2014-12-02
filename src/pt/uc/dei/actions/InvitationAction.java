package pt.uc.dei.actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import pt.uc.dei.models.InvitationBean;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * Created by bruno on 22/11/14.
 */
public class InvitationAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    public List list;
    Boolean outcome;
    InvitationBean invitationBean = new InvitationBean();
    private String[] check;
    private String req = null;
    private String invitationID;
    private Map<String, Object> session;
    private String users = null;

    public String[] getCheck() {
        return check;
    }

    public void setCheck(String[] check) {
        this.check = check;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public String getInvitationID() {
        return invitationID;
    }

    public void setInvitationID(String invitationID) {
        this.invitationID = invitationID;
    }

    public String getReq() {
        return req;
    }

    public void setReq(String req) {
        this.req = req;
    }



    public String execute() throws RemoteException {


        return SUCCESS;

    }

    public void setSession(Map<String, Object> session) {
        this.session = session;

    }

    public String getPendingInvitations() throws Exception {

        invitationBean.setUsername((String) session.get("username"));

        list = invitationBean.getPendingInvitations();


        return SUCCESS;
    }

    public String acceptInvitation() throws Exception {

        invitationBean.setUsername((String) session.get("username"));

        invitationBean.setMeetings(Arrays.toString(check));

        outcome = invitationBean.acceptInvitation();

        if (outcome) {
            addActionMessage("Meetings accepted!");
        } else {
            addActionError("Meeting not accepted, something's wrong");
        }

        return SUCCESS;

    }

    public String declineInvitation() throws Exception {

        invitationBean.setUsername((String) session.get("username"));

        invitationBean.setMeetings(Arrays.toString(check));

        outcome = invitationBean.declineInvitation();

        if (outcome) {
            addActionMessage("Meetings declined!");
        } else {
            addActionError("Meeting not declined, something's wrong");
        }


        return SUCCESS;
    }


}
