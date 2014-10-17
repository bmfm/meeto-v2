package pt.uc.dei.rmi_server;

import pt.uc.dei.tcp_server.Message;
import pt.uc.dei.tcp_server.TCPServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiInterface extends Remote {

    public Message createMeeting(Message mensagem) throws RemoteException;

    public  Message listMeetings(Message mensagem) throws RemoteException;

    public Message register(Message mensagem) throws RemoteException;

    public Message login(Message mensagem) throws RemoteException;

    public Message getUsernameId(Message mensagem) throws RemoteException;

    public void ping() throws RemoteException;

    public void subscribe(TCPServer client) throws RemoteException;


}

