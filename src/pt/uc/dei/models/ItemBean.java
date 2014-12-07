package pt.uc.dei.models;

import pt.uc.dei.rmi_server.RmiInterface;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by bruno on 06/12/14.
 */
public class ItemBean {

    private UtilityBean utility = new UtilityBean();
    private int idmeeting;

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


    public Boolean deleteItem() {

        return null;
    }
}
