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
    private String token;
    private UtilityBean utility = new UtilityBean();

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean validate() throws RemoteException {


        RmiInterface c = utility.connectoToRmiServer();

        Message mensagem = new Message(userName, password, null, "log");

        mensagem = c.login(mensagem);

        return mensagem.result;


    }


    public String[] getGoogleCredentials() throws RemoteException {

        RmiInterface c = utility.connectoToRmiServer();

        return c.getGoogleCredentials(userName);

    }

    public void updateGoogleToken() {

        RmiInterface c = utility.connectoToRmiServer();

        try {
            c.updateGoogleToken(userName, token);
        } catch (RemoteException e) {

        }

    }

    public Boolean logout(String userName) throws RemoteException {

        RmiInterface c = utility.connectoToRmiServer();

        Message mensagem = new Message(userName, null, null, "logout");

        mensagem = c.logout(mensagem);

            return mensagem.result;

    }

    public String verifyGoogleID(String id) throws RemoteException {

        RmiInterface c = utility.connectoToRmiServer();

        return c.verifyGoogleID(id);
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

    public boolean associateLogin(String normalUserLogin, String googleId, String email) throws RemoteException {

        RmiInterface c = utility.connectoToRmiServer();

        return c.associateLogin(normalUserLogin, googleId, email);
    }
}



