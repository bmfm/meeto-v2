package pt.uc.dei.tcp_server;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface TCPServer extends Remote {


    public void ping() throws RemoteException;


}
