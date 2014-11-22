package pt.uc.dei.models;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import pt.uc.dei.rmi_server.RmiInterface;
import pt.uc.dei.tcp_server.Message;

import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Map;
import java.util.Properties;

/**
 * Created by brunomartins on 21/11/14.
 */
public class LoginBean {

    private String userName;
    private String password;


    public Boolean validate(String userName, String password) {

        try {

            Properties props = new Properties();

            try {
                props.load(new FileInputStream("support/property"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            System.getProperties().put("java.security.policy", "support/policy.all");
            System.setSecurityManager(new RMISecurityManager());

            //funciona apenas com lookup(core) mas torna-se um problema se o o cliente se tentar ligar ao servidor de backup
            RmiInterface c = (RmiInterface) LocateRegistry.getRegistry(props.getProperty("rmiServerip"), Integer.parseInt(props.getProperty("rmiServerPort1"))).lookup("rmi://" + props.getProperty("rmiServerip") + "/core");

            Message mensagem = new Message(userName, password, null, "log");
            mensagem = c.login(mensagem);

            return mensagem.result;


        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }


        return null;
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



