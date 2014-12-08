package pt.uc.dei.actions;

import com.opensymphony.xwork2.ActionSupport;
import pt.uc.dei.models.ItemBean;

public class ItemAction extends ActionSupport {

    ItemBean itemBean = new ItemBean();
    private String agendaItemID;
    private Boolean outcome;
    private String req;
    private String meetingid;
    private String itemname;
    private String itemdescription;
    private String meetingidhidden;

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

    public String modifyItem() throws Exception {
        return SUCCESS;
    }

    public String addKeyDecision() throws Exception {
        return SUCCESS;
    }

    public String assignTask() throws Exception {
        return SUCCESS;
    }

    public String deleteItem() throws Exception {

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
}
