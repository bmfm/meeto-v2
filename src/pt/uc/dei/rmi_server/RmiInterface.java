package pt.uc.dei.rmi_server;

import pt.uc.dei.tcp_server.Message;
import pt.uc.dei.tcp_server.NotMasterException;
import pt.uc.dei.tcp_server.TCPServer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RmiInterface extends Remote {

    public Message createMeeting(Message mensagem) throws RemoteException;


    public Message listUpcomingMeetings(Message mensagem) throws RemoteException;

    public Message listPastMeetings(Message mensagem) throws RemoteException;

    public Message listMyMeetings(Message mensagem) throws RemoteException;

    public List viewPendingInvitationsForDataStructure(String username) throws RemoteException;

    public List meetingOverviewForWeb(int meetingID) throws RemoteException;

    public Message sendInvitations(Message mensagem) throws RemoteException;

    public Message meetingOverview(Message mensagem) throws RemoteException;

    public Message acceptMeeting(Message mensagem) throws RemoteException;

    public Message declineMeeting(Message mensagem) throws RemoteException;

    public Message listAgendaItems(Message mensagem) throws RemoteException;

    public Message addAgendaItem(Message mensagem) throws RemoteException;

    public Message modifyAgendaItem(Message mensagem) throws RemoteException;

    public Message deleteAgendaItem(Message mensagem) throws RemoteException;

    public Message addChatMessage(Message mensagem) throws RemoteException, NotMasterException;

    public Message listChat(Message mensagem) throws RemoteException;

    public Message addKeyDecision(Message mensagem) throws RemoteException;

    public Message assignAction(Message mensagem) throws RemoteException;

    public Message showToDoList(Message mensagem) throws RemoteException;

    public Message completeAction(Message mensagem) throws RemoteException;

    public Message viewPendingInvitations(Message mensagem) throws RemoteException;

    public Message listMembers(Message mensagem) throws RemoteException;

    public Message listAllMembers(Message mensagem) throws RemoteException;

    public Message register(Message mensagem) throws RemoteException;

    public Message login(Message mensagem) throws RemoteException;

    public void ping() throws RemoteException;

    public void subscribe(TCPServer client) throws RemoteException;

    public Message logout(Message mensagem) throws RemoteException;

    public Message onlineUsers(Message mensagem) throws RemoteException;


}

