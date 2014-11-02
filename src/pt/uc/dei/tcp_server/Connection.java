package pt.uc.dei.tcp_server;

import pt.uc.dei.rmi_server.RmiInterface;

import java.io.*;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.util.Properties;

public class Connection extends Thread {

    ObjectInputStream in;
    ObjectOutputStream out = null;
    Socket clientSocket;
    int thread_number;
    String username = null;

    public Connection(Socket aClientSocket, int numero) {
        this.thread_number = numero;

        try {
            this.clientSocket = aClientSocket;
            this.in = new ObjectInputStream(clientSocket.getInputStream());
            this.out = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    public void sendOut(Message mensagem) {
        try {
            synchronized (out) {

                out.writeObject(mensagem);
                out.flush();
                out.reset();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //=============================

    @Override
    public void run() {
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
            TCPServerImpl y = new TCPServerImpl();
            c.subscribe((TCPServer) y);


            while (true) {

                //espera por uma mensagem
                Message mensagem = (Message) in.readObject();

                switch (mensagem.getTipo()) {


                    //tratamento de um register
                    case (Message.REG):
                        mensagem = c.register(mensagem);
                        sendOut(mensagem);
                        break;


                    //tratamento de um login
                    case (Message.LOG):
                        mensagem = c.login(mensagem);
                        if (mensagem.result) {
                            this.username = mensagem.username;
                        }
                        sendOut(mensagem);
                        break;

                    //tratamento da criacao de uma meeting
                    case (Message.CREATEMEETING):
                        mensagem = c.createMeeting(mensagem);
                        sendOut(mensagem);
                        break;

                    //tratamento de user que ja se logou mas foi abaixo
                    case (Message.RECONNECT):
                        this.username = mensagem.username;
                        break;

                    case (Message.LOGOUT):
                        mensagem = c.logout(mensagem);
                        sendOut(mensagem);
                        break;


                    case (Message.LISTALLMEMBERS):
                        mensagem = c.listAllMembers(mensagem);
                        sendOut(mensagem);
                        break;

                    case (Message.LISTAGENDAITEMS):
                        mensagem = c.listAgendaItems(mensagem);
                        sendOut(mensagem);
                        break;


                    case (Message.LISTUPCOMINGMEETINGS):
                        mensagem = c.listUpcomingMeetings(mensagem);
                        sendOut(mensagem);
                        break;

                    case (Message.LISTPASTMEETINGS):
                        mensagem = c.listPastMeetings(mensagem);
                        sendOut(mensagem);
                        break;

                    case (Message.MEETINGOVERVIEW):
                        mensagem = c.meetingOverview(mensagem);
                        sendOut(mensagem);
                        break;

                    case (Message.LISTMYMEETINGS):
                        mensagem = c.listMyMeetings(mensagem);
                        sendOut(mensagem);
                        break;


                    case (Message.ACCEPTMEETING):
                        mensagem = c.acceptMeeting(mensagem);
                        sendOut(mensagem);
                        break;

                    case (Message.DECLINEMEETING):
                        mensagem = c.declineMeeting(mensagem);
                        sendOut(mensagem);
                        break;

                    case (Message.ADDAGENTAITEM):
                        mensagem = c.addAgendaItem(mensagem);
                        sendOut(mensagem);
                        break;

                    case (Message.MODIFYAGENDAITEM):
                        mensagem = c.modifyAgendaItem(mensagem);
                        sendOut(mensagem);
                        break;

                    case (Message.DELETEAGENDAITEM):
                        mensagem = c.deleteAgendaItem(mensagem);
                        sendOut(mensagem);
                        break;

                    case (Message.ADDCHATMESSAGE):
                        mensagem = c.addChatMessage(mensagem);
                        sendOut(mensagem);
                        break;

                    case (Message.ADDKEYDECISION):
                        mensagem = c.addKeyDecision(mensagem);
                        sendOut(mensagem);
                        break;


                    case (Message.ASSIGNACTION):
                        mensagem = c.assignAction(mensagem);
                        sendOut(mensagem);
                        break;

                    case (Message.SHOWTODOLIST):
                        mensagem = c.showToDoList(mensagem);
                        sendOut(mensagem);
                        break;

                    case (Message.COMPLETEACTION):
                        mensagem = c.completeAction(mensagem);
                        sendOut(mensagem);
                        break;

                    case (Message.LISTMEMBERS):
                        mensagem = c.listMembers(mensagem);
                        sendOut(mensagem);
                        break;

                    case (Message.VIEWPENDINGINVITATIONS):
                        mensagem = c.viewPendingInvitations(mensagem);
                        sendOut(mensagem);
                        break;

                    case (Message.CHECKONLINE):
                        mensagem = c.onlineUsers(mensagem);
                        sendOut(mensagem);
                        break;

                    case (Message.CHECKCHATMESSAGES):
                        mensagem = c.listChat(mensagem);
                        sendOut(mensagem);
                        break;

                }

            }

        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found!");
        } catch (EOFException e) {
            System.out.println("EOF error!");
        } catch (IOException e) {
            System.out.println("IO error!");
        } catch (NotBoundException e) {
            System.out.println("Not bound error!");
        } catch (NotMasterException e) {
            e.printStackTrace();
        }
    }


}
