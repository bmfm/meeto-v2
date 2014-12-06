package pt.uc.dei.models;

import pt.uc.dei.rmi_server.RmiInterface;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by bruno on 06/12/14.
 */
public class ItemBean {

    UtilityBean utility = new UtilityBean();


    public List agendaList(int idmeeting) throws RemoteException {

        RmiInterface c = utility.connectoToRmiServer();

        return c.listAgendaItemsForWeb(idmeeting);


    }


}
