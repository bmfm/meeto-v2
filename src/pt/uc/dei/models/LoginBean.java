package pt.uc.dei.models;


import pt.uc.dei.rmi_server.RmiInterface;
import pt.uc.dei.tcp_server.Message;

import java.rmi.RemoteException;

/**
 * Created by brunomartins on 21/11/14.
 */
public class LoginBean {

    private String userName;
    private String password;

    private UtilityBean utility = new UtilityBean();


    public Boolean validate(String userName, String password) throws RemoteException {


        RmiInterface c = utility.connectoToRmiServer();

        Message mensagem = new Message(userName, password, null, "log");
        mensagem = c.login(mensagem);


        return mensagem.result;


    }

    public Boolean logout(String userName) throws RemoteException {

        RmiInterface c = utility.connectoToRmiServer();

        Message mensagem = new Message(userName, null, null, "logout");

        mensagem = c.logout(mensagem);

            return mensagem.result;

    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}



