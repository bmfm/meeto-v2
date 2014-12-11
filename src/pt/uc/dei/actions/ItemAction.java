package pt.uc.dei.actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import pt.uc.dei.models.ItemBean;
import pt.uc.dei.models.MeetingBean;

import java.util.List;
import java.util.Map;

public class ItemAction extends ActionSupport implements SessionAware {

    ItemBean itemBean = new ItemBean();
    MeetingBean meetingBean = new MeetingBean();
    private String agendaItemID;
    private Boolean outcome;
    private String req;
    private String meetingid;
    private String itemname;
    private String itemdescription;
    private String meetingidhidden;
    private String agendaitemidhidden;
    private String keydecision;
    private List list;
    private Map<String, Object> session;
    private String userToAssignAction;
    private String meetingidhiddenforaction;
    private String meetingidfromroomform;
    private String actionname;
    private String itemid;

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public String getMeetingidfromroomform() {
        return meetingidfromroomform;
    }

    public void setMeetingidfromroomform(String meetingidfromroomform) {
        this.meetingidfromroomform = meetingidfromroomform;
    }

    public String getActionname() {
        return actionname;
    }

    public void setActionname(String actionname) {
        this.actionname = actionname;
    }

    public String getKeydecision() {
        return keydecision;
    }

    public void setKeydecision(String keydecision) {
        this.keydecision = keydecision;
    }

    public String getItemdescription() {
        return itemdescription;
    }

    public void setItemdescription(String itemdescription) {
        this.itemdescription = itemdescription;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getMeetingid() {
        return meetingid;
    }

    public void setMeetingid(String meetingid) {
        this.meetingid = meetingid;
    }

    public String getAgendaItemID() {
        return agendaItemID;
    }

    public void setAgendaItemID(String agendaItemID) {
        this.agendaItemID = agendaItemID;
    }

    public String addItem() throws Exception {


        itemBean.setIdmeeting(Integer.parseInt(meetingidhidden));
        itemBean.setItemname(itemname);
        itemBean.setItemdescription(itemdescription);

        outcome = itemBean.addItem();

        if (outcome) {
            addActionMessage("Agenda item added");

        } else {
            addActionError("Not added, something went wrong. Please try again");
        }

        return SUCCESS;
    }

    public String addKeyDecision() throws Exception {

        itemBean.setAgendaItemID(agendaitemidhidden);

        itemBean.setKeydecision(keydecision);

        outcome = itemBean.addKeyDecision();

        if (outcome) {
            addActionMessage("Key decision added");

        } else {
            addActionError("Not added, something went wrong. Please try again");
        }

        return SUCCESS;
    }

    public String assignTask() throws Exception {

        String aux = userToAssignAction.split("\t\t")[0];

        itemBean.setUserToAssignAction(aux);
        itemBean.setActionname(actionname);
        itemBean.setIdmeeting(Integer.parseInt(meetingidhiddenforaction));

        outcome = itemBean.assignTask();


        if (outcome) {
            addActionMessage("Action assigned!");

        } else {
            addActionError("Not assigned, something went wrong. Please try again");
        }





        return SUCCESS;
    }

    public String deleteItem() throws Exception {

        itemBean.setAgendaItemID(agendaItemID);

        outcome = itemBean.deleteItem();

        if (outcome) {
            addActionMessage("Deleted!");
        } else {
            addActionError("Not deleted, something's wrong");
        }


        return SUCCESS;
    }

    public String getReq() {
        return req;
    }

    public void setReq(String req) {
        this.req = req;
    }

    public String getMeetingidhidden() {
        return meetingidhidden;
    }

    public void setMeetingidhidden(String meetingidhidden) {
        this.meetingidhidden = meetingidhidden;
    }

    public String getAgendaitemidhidden() {
        return agendaitemidhidden;
    }

    public void setAgendaitemidhidden(String agendaitemidhidden) {
        this.agendaitemidhidden = agendaitemidhidden;
    }

    public String openChat() throws Exception {
        return "success";
    }

    public String getMemberList() throws Exception {

        meetingBean.setUsername((String) session.get("username"));

        list = meetingBean.getInviteeList();

        return SUCCESS;
    }

    @Override
    public void setSession(Map<String, Object> stringObjectMap) {
        this.session = stringObjectMap;
    }

    public String getUserToAssignAction() {
        return userToAssignAction;
    }

    public void setUserToAssignAction(String userToAssignAction) {
        this.userToAssignAction = userToAssignAction;
    }

    public String getMeetingidhiddenforaction() {
        return meetingidhiddenforaction;
    }

    public void setMeetingidhiddenforaction(String meetingidhiddenforaction) {
        this.meetingidhiddenforaction = meetingidhiddenforaction;
    }

    public String modifyItemName() throws Exception {

        itemBean.setAgendaItemID(itemid);
        itemBean.setItemname(itemname);

        outcome = itemBean.modifyItemName();

        if (outcome) {
            addActionMessage("Modified! Go back to see the changes!");
        } else {
            addActionError("Not modified, something's wrong");
        }


        return SUCCESS;
    }

    public String modifyItemDescription() throws Exception {

        itemBean.setAgendaItemID(itemid);
        itemBean.setItemdescription(itemdescription);

        outcome = itemBean.modifyItemDescription();

        if (outcome) {
            addActionMessage("Modified! Go back to see the changes!");
        } else {
            addActionError("Not modified, something's wrong");
        }
        return SUCCESS;
    }

    public String openModifyItemName() throws Exception {
        return "success";
    }

    public String openModifyItemDescription() throws Exception {
        return "success";
    }
}
