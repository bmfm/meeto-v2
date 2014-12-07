package pt.uc.dei.actions;

import com.opensymphony.xwork2.ActionSupport;
import pt.uc.dei.models.ItemBean;

public class ItemAction extends ActionSupport {

    ItemBean itemBean = new ItemBean();
    private String agendaItemID;
    private Boolean outcome;

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

        outcome = itemBean.deleteItem();

        if (outcome) {
            addActionMessage("Deleted!");
        } else {
            addActionError("Not deleted, something's wrong");
        }


        return SUCCESS;
    }
}
