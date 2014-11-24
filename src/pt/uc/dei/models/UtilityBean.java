package pt.uc.dei.models;

import pt.uc.dei.rmi_server.RmiInterface;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Properties;

/**
 * Created by brunomartins on 24/11/14.
 */
public class UtilityBean {

    public RmiInterface connectoToRmiServer() {

        try {

            Properties props = new Properties();

            props.load(new FileInputStream("property"));


            System.getProperties().put("java.security.policy", "policy.all");
            System.setSecurityManager(new RMISecurityManager());

            return (RmiInterface) LocateRegistry.getRegistry(props.getProperty("rmiServerip"), Integer.parseInt(props.getProperty("rmiServerPort1"))).lookup("rmi://" + props.getProperty("rmiServerip") + "/core");

        } catch (NotBoundException | RemoteException e) {
            System.out.println("Error");
            return null;
        } catch (FileNotFoundException e) {
            System.out.println("Error");
            return null;
        } catch (IOException e) {
            System.out.println("Error");
            return null;
        }

    }
}
