package pt.uc.dei.actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import pt.uc.dei.models.InvitationBean;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;


/**
 * Created by bruno on 22/11/14.
 */
public class InvitationAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    public List<String> list;
    Boolean outcome;
    InvitationBean invitationBean = new InvitationBean();
    private String req = null;
    private String invitationID;
    private Map<String, Object> session;
    private String users = null;

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

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
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
        return SUCCESS;
    }

    public String declineInvitation() throws Exception {
        return SUCCESS;
    }


}
