package pt.uc.dei.actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import pt.uc.dei.models.ItemBean;
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
    public List participantsList;
    public List agendaItemsList;
    public List actionsList;
    Boolean outcome;
    MeetingBean meetingBean = new MeetingBean();
    ItemBean itemBean = new ItemBean();

    private String req = null;
    private String checkboxes;
    private String meetingTitle = null;
    private Map<String, Object> session;
    private String desiredOutcome = null;
    private String datetime = null;
    private String location = null;
    private String users = null;
    private String meetingOverviewID;
    private String joinmymeetingid;
    private List allMeetings;
    private String idmeeting = null;
    private List myMeetingsList;


    public String getJoinmymeetingid() {
        return joinmymeetingid;
    }

    public void setJoinmymeetingid(String joinmymeetingid) {
        this.joinmymeetingid = joinmymeetingid;
    }

    public List getMyMeetingsList() {
        return myMeetingsList;
    }

    public void setMyMeetingsList(List myMeetingsList) {
        this.myMeetingsList = myMeetingsList;
    }

    public List getAllMeetings() {
        return allMeetings;
    }

    public void setAllMeetings(List allMeetings) {
        this.allMeetings = allMeetings;
    }

    public String getIdmeeting() {
        return idmeeting;
    }

    public void setIdmeeting(String idmeeting) {
        this.idmeeting = idmeeting;
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

    public String getCheckboxes() {
        return checkboxes;
    }

    public void setCheckboxes(String checkboxes) {
        this.checkboxes = checkboxes;
    }

    public void setMeetingTitle(String meetingTitle) {
        this.meetingTitle = meetingTitle;
    }

    public List getParticipantsList() {
        return participantsList;
    }

    public void setParticipantsList(List participantsList) {
        this.participantsList = participantsList;
    }

    public List getAgendaItemsList() {
        return agendaItemsList;
    }

    public void setAgendaItemsList(List agendaItemsList) {
        this.agendaItemsList = agendaItemsList;
    }

    public List getActionsList() {
        return actionsList;
    }

    public void setActionsList(List actionsList) {
        this.actionsList = actionsList;
    }

    public String execute() throws RemoteException {


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

    public String create() throws Exception {

        String membersToInvite = "";

        String aux = "";
        List<String> check = Arrays.asList(checkboxes.split(","));


        for (String checkbox : check) {
            aux = checkbox.split("\t\t")[0];
            membersToInvite += aux;
        }

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


        return "success";
    }


    public String meetingsoverview() throws Exception {

        meetingBean.setUsername((String) session.get("username"));
        //meetingBean.setIdmeeting(Integer.parseInt(idmeeting));

        allMeetings = meetingBean.allMeetingsList();

        return SUCCESS;
    }

    public String meetingdetails() throws Exception {

        actionsList = meetingBean.actionList(Integer.parseInt(meetingOverviewID));

        agendaItemsList = meetingBean.agendaList(Integer.parseInt(meetingOverviewID));

        participantsList = meetingBean.participantsList(Integer.parseInt(meetingOverviewID));

        return SUCCESS;
    }

    public String getMeetingOverviewID() {
        return meetingOverviewID;
    }

    public void setMeetingOverviewID(String meetingOverviewID) {
        this.meetingOverviewID = meetingOverviewID;
    }

    public String mymeetings() throws Exception {

        meetingBean.setUsername((String) session.get("username"));

        myMeetingsList = meetingBean.myMeetingsList();


        return SUCCESS;
    }

    public String meetingroom() throws Exception {


        agendaItemsList = itemBean.agendaList(Integer.parseInt(joinmymeetingid));

        return SUCCESS;
    }
}
