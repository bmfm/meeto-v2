package pt.uc.dei.models;

import pt.uc.dei.rmi_server.RmiInterface;
import pt.uc.dei.tcp_server.Message;
import pt.uc.dei.tcp_server.NotMasterException;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by bruno on 06/12/14.
 */
public class ItemBean {

    private UtilityBean utility = new UtilityBean();
    private int idmeeting;
    private String itemname;
    private String itemdescription;
    private String username;
    private int idagenda;
    private String keydecision;
    private String actionname;
    private String agendaItemID;
    private String userToAssignAction;
    private String chatline;

    public String getChatline() {
        return chatline;
    }

    public void setChatline(String chatline) {
        this.chatline = chatline;
    }

    public String getUserToAssignAction() {
        return userToAssignAction;
    }

    public void setUserToAssignAction(String userToAssignAction) {
        this.userToAssignAction = userToAssignAction;
    }

    public String getActionname() {
        return actionname;
    }

    public void setActionname(String actionname) {
        this.actionname = actionname;
    }

    public String getAgendaItemID() {
        return agendaItemID;
    }

    public void setAgendaItemID(String agendaItemID) {
        this.agendaItemID = agendaItemID;
    }

    public String getKeydecision() {
        return keydecision;
    }

    public void setKeydecision(String keydecision) {
        this.keydecision = keydecision;
    }

    public int getIdagenda() {
        return idagenda;
    }

    public void setIdagenda(int idagenda) {
        this.idagenda = idagenda;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public int getIdmeeting() {
        return idmeeting;
    }

    public void setIdmeeting(int idmeeting) {
        this.idmeeting = idmeeting;
    }

    public List agendaList() throws RemoteException {

        RmiInterface c = utility.connectoToRmiServer();

        return c.listAgendaItemsForWeb(idmeeting);

    }

    public Boolean deleteItem() throws RemoteException {

        RmiInterface c = utility.connectoToRmiServer();

        Message mensagem = new Message(username, null, null, "deleteItem");

        mensagem.dataint = Integer.parseInt(agendaItemID);

        mensagem = c.deleteAgendaItem(mensagem);

        return mensagem.result;
    }


    public Boolean addItem() throws RemoteException {

        RmiInterface c = utility.connectoToRmiServer();

        Message mensagem = new Message(username, null, null, "addItem");

        mensagem.name = itemname;
        mensagem.description = itemdescription;
        mensagem.dataint = idmeeting;

        mensagem = c.addAgendaItem(mensagem);

        return mensagem.result;
    }


    public Boolean addKeyDecision() throws RemoteException {

        RmiInterface c = utility.connectoToRmiServer();

        Message mensagem = new Message(username, null, null, "addKeyDecision");

        mensagem.keydecision = keydecision;

        mensagem.dataint = Integer.parseInt(agendaItemID);

        mensagem = c.addKeyDecision(mensagem);

        return mensagem.result;
    }

    public Boolean assignTask() throws RemoteException {

        RmiInterface c = utility.connectoToRmiServer();

        Message mensagem = new Message(username, null, null, "assignAction");

        mensagem.dataint = idmeeting;
        mensagem.dataint2 = Integer.parseInt(userToAssignAction);
        mensagem.data = actionname;

        mensagem = c.assignAction(mensagem);

        return mensagem.result;


    }

    public Boolean modifyItemName() throws RemoteException {

        RmiInterface c = utility.connectoToRmiServer();

        Message mensagem = new Message(username, null, null, "modifyItem");

        mensagem.dataint = Integer.parseInt(agendaItemID);
        mensagem.dataint2 = 1;
        mensagem.data = itemname;

        mensagem = c.modifyAgendaItem(mensagem);

        return mensagem.result;


    }

    public Boolean modifyItemDescription() throws RemoteException {

        RmiInterface c = utility.connectoToRmiServer();

        Message mensagem = new Message(username, null, null, "modifyItem");

        mensagem.data = itemdescription;
        mensagem.dataint = Integer.parseInt(agendaItemID);
        mensagem.dataint2 = 2;

        mensagem = c.modifyAgendaItem(mensagem);

        return mensagem.result;

    }

    public String getChatLog() throws RemoteException {

        RmiInterface c = utility.connectoToRmiServer();

        Message mensagem = new Message(username, null, null, "listChat");

        mensagem.dataint = (idagenda);

        mensagem = c.listChat(mensagem);

        return mensagem.data;
    }

    public void addToChatLog() throws RemoteException, NotMasterException {


        RmiInterface c = utility.connectoToRmiServer();

        Message mensagem = new Message(username, null, null, "addChat");

        mensagem.dataint = (idagenda);
        mensagem.data = chatline;

        mensagem = c.addChatMessage(mensagem);


    }
}
