package pt.uc.dei.actions;

import com.opensymphony.xwork2.ActionSupport;

public class ItemAction extends ActionSupport {

    private String agendaItemID;

    public String getAgendaItemID() {
        return agendaItemID;
    }

    public void setAgendaItemID(String agendaItemID) {
        this.agendaItemID = agendaItemID;
    }

    public String addItem() throws Exception {
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
        return SUCCESS;
    }
}
