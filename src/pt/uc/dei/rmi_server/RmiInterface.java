package pt.uc.dei.rmi_server;

import pt.uc.dei.tcp_server.Message;
import pt.uc.dei.tcp_server.TCPServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiInterface extends Remote {

    public Message createMeeting(Message mensagem) throws RemoteException;

    public Message postNewDiscussionMessage(Message mensagem) throws RemoteException;

    public Message inviteToMeeting(Message mensagem) throws RemoteException;

    public Message listUpcomingMeetings(Message mensagem) throws RemoteException;

    public Message listAllMeetings(Message mensagem) throws RemoteException;

    public Message meetingOverview(Message mensagem) throws RemoteException;

    public Message acceptMeeting(Message mensagem) throws RemoteException;

    public Message declineMeeting(Message mensagem) throws RemoteException;

    public Message addAgendaItem(Message mensagem) throws RemoteException;

    public Message modifyAgendaItem(Message mensagem) throws RemoteException;

    public Message deleteAgendaItem(Message mensagem) throws RemoteException;

    public Message addChatMessage(Message mensagem) throws RemoteException;

    public Message addKeyDecision(Message mensagem) throws RemoteException;

    public Message assignAction(Message mensagem) throws RemoteException;

    public Message showToDoList(Message mensagem) throws RemoteException;

    public Message completeAction(Message mensagem) throws RemoteException;

    public Message listMembers(Message mensagem) throws RemoteException;

    public Message register(Message mensagem) throws RemoteException;

    public Message login(Message mensagem) throws RemoteException;

    public Message getUsernameId(Message mensagem) throws RemoteException;

    public void ping() throws RemoteException;

    public void subscribe(TCPServer client) throws RemoteException;


}

