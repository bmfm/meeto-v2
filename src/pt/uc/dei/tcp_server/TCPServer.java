package pt.uc.dei.tcp_server;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface TCPServer extends Remote {


    public void ping() throws RemoteException;

    public void sendMsg(Message[] messages, String[] usernames) throws RemoteException, NotMasterException;

    public void msgToMany(Message m, String... u) throws RemoteException, NotMasterException;

    public void msgsToOne(String u, Message... m) throws RemoteException, NotMasterException;


}
