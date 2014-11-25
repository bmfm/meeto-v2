package pt.uc.dei.models;

import pt.uc.dei.rmi_server.RmiInterface;
import pt.uc.dei.tcp_server.Message;

import java.rmi.RemoteException;

/**
 * Created by bruno on 25/11/14.
 */
public class RegisterBean {


    UtilityBean utility = new UtilityBean();

    private String userName;

    private String password;

    private String mail;


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void setMail(String mail) {
        this.mail = mail;
    }


    public Boolean register() throws RemoteException {

        RmiInterface c = utility.connectoToRmiServer();

        Message mensagem = new Message(userName, password, mail, "reg");
        mensagem = c.register(mensagem);

        return mensagem.result;


    }


}
