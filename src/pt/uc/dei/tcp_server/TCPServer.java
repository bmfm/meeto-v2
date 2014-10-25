package pt.uc.dei.tcp_server;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface TCPServer extends Remote {


    //TODO será mesmo necessário este ping? Eu usava para verificar se a ligação ao tcp se mantinha viva e, caso contrário, mudava de porto.
    //Creio que quando tentava fazer rebind para o mesmo porto ele dava bode
    //Possivelmente não estava era a fechar como deve ser quando ele caía.

    public void ping() throws RemoteException;

    public void sendMsg(Message[] messages, String[] usernames) throws RemoteException, NotMasterException;

    public void msgToMany(Message m, String... u) throws RemoteException, NotMasterException;

    public void msgsToOne(String u, Message... m) throws RemoteException, NotMasterException;


}
