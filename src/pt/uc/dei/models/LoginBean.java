package pt.uc.dei.models;


import pt.uc.dei.rmi_server.RmiInterface;
import pt.uc.dei.tcp_server.Message;

import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
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

            //${pageContext.request.contextPath}

            props.load(new FileInputStream("property"));


            System.getProperties().put("java.security.policy", "policy.all");
            System.setSecurityManager(new RMISecurityManager());


            RmiInterface c = (RmiInterface) LocateRegistry.getRegistry(props.getProperty("rmiServerip"), Integer.parseInt(props.getProperty("rmiServerPort1"))).lookup("rmi://" + props.getProperty("rmiServerip") + "/core");

            System.out.println(userName);
            System.out.println(password);


            Message mensagem = new Message(userName, password, null, "log");
            mensagem = c.login(mensagem);

            return mensagem.result;




        } catch (NotBoundException | IOException e) {
            e.printStackTrace();
            return null;
        }

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



