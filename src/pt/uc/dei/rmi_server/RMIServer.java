package pt.uc.dei.rmi_server;

import pt.uc.dei.tcp_server.Message;
import pt.uc.dei.tcp_server.TCPServer;

import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class RMIServer extends UnicastRemoteObject implements RmiInterface, Runnable {
    List<Message> msgsToSend = new ArrayList<Message>();
    List<String> userToSend = new ArrayList<String>();

    //TODO será melhor colocar um HashSet com os users online ou fazer essa operação na base de dados com um campo apenas?

    HashSet<String> membersonline = new HashSet<>();


    static TCPServer tcpServer;
    MySQL sql = new MySQL();


    public RMIServer() throws RemoteException {
        super();
        sql.connect();

        Properties props = new Properties();

        try {
            props.load(new FileInputStream("support/property"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }


        System.getProperties().put("java.security.policy", "support/policy.all");
        System.setSecurityManager(new RMISecurityManager());
        System.setProperty("java.rmi.server.hostname", props.getProperty("rmiServerip"));


        Registry r = LocateRegistry.createRegistry(Integer.parseInt(props.getProperty("rmiServerPort1")));
        r.rebind("rmi://" + props.getProperty("rmiServerip") + "/core", this);

        System.out.println("RMI Server ready.");


        new Thread(this).start();
    }

    public static void main(String[] args) {

        try {
            RMIServer myServer = new RMIServer();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public synchronized void setSysMsg(Message mensagem) {


    }


    public synchronized Message createMeeting(Message mensagem) throws RemoteException {
        List<Message> msgToSend = new ArrayList<>();
        //saber o id do user que esta a criar a meeting
        Message msgid = getUsernameId(mensagem);
        mensagem.dataint = msgid.iduser;
        int idmeeting = 0;

        List<String> inviteeslist = Arrays.asList(mensagem.list.split(","));
        mensagem.result = false;
        if ((sql.doUpdate("INSERT INTO meeting (title,objective,date,location) VALUES ('" + mensagem.data + "','" + mensagem.desiredoutcome + "','" + mensagem.date + " " + mensagem.time + "','" + mensagem.location + "');")) == 1) {

            ResultSet rs = sql.doQuery("SELECT MAX(idmeeting) FROM meeting;");
            try {
                rs.next();
                idmeeting = rs.getInt(1);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            //colocar o criador da meeting na tabela meeting_member (como accepted naturalmente) antes de tratar dos outros
            sql.doUpdate("INSERT INTO meeting_member (idmeeting,idmember,accepted) VALUES ('" + idmeeting + "','" + mensagem.dataint + "'+'1');");

            //colocar os restantes na tabela meeting_member
            for (int i = 0; i < inviteeslist.size(); i++) {
                if ((sql.doUpdate("INSERT INTO meeting_member (idmeeting,idmember) VALUES ('" + idmeeting + "','" + inviteeslist.get(i) + "');")) == 1) {
                    //TODO ADICIONAR AS MSGS A LISTA E ENVIAR COM O METODO DO TCPSERVER
                    mensagem.result = true;

                }

            }

            //TODO enviar mensagem para TCPSERVER
            return mensagem;


        }

        return mensagem;
    }


    @Override
    public synchronized Message postNewDiscussionMessage(Message mensagem) throws RemoteException {
        return null;
    }


    @Override
    public synchronized Message inviteToMeeting(Message mensagem) throws RemoteException {
        return null;
    }

    @Override
    public synchronized Message sendInvitations(Message mensagem) throws RemoteException {
        return null;
    }


    @Override
    public synchronized Message meetingOverview(Message mensagem) throws RemoteException {
        return null;
    }


    @Override
    public synchronized Message acceptMeeting(Message mensagem) throws RemoteException {
        Message msgid = getUsernameId(mensagem);
        mensagem.dataint = msgid.iduser;

        List<String> acceptlist = Arrays.asList(mensagem.list.split(","));

        mensagem.result = false;
        //aceitar as meetings escolhidas
        for (int i = 0; i < acceptlist.size(); i++) {
            if ((sql.doUpdate("UPDATE meeting_member SET accepted='1' where idmember='" + mensagem.dataint + "'and idmeeting='" + acceptlist.get(i) + "'")) == 1)

            {

                mensagem.result = true;
            }

        }
        return mensagem;
    }

    @Override
    public synchronized Message declineMeeting(Message mensagem) throws RemoteException {

        Message msgid = getUsernameId(mensagem);
        mensagem.dataint = msgid.iduser;

        List<String> declinelist = Arrays.asList(mensagem.list.split(","));

        mensagem.result = false;
        //recusar as meetings escolhidas
        for (int i = 0; i < declinelist.size(); i++) {
            if ((sql.doUpdate("UPDATE meeting_member SET accepted='0' where idmember='" + mensagem.dataint + "'and idmeeting='" + declinelist.get(i) + "'")) == 1)

            {
                mensagem.result = true;
            }

        }
        return mensagem;

    }

    @Override
    public synchronized Message addAgendaItem(Message mensagem) throws RemoteException {
        return null;
    }

    @Override
    public synchronized Message modifyAgendaItem(Message mensagem) throws RemoteException {
        return null;
    }

    @Override
    public synchronized Message deleteAgendaItem(Message mensagem) throws RemoteException {
        return null;
    }

    @Override
    public synchronized Message addChatMessage(Message mensagem) throws RemoteException {
        return null;
    }

    @Override
    public synchronized Message addKeyDecision(Message mensagem) throws RemoteException {
        return null;
    }

    @Override
    public synchronized Message assignAction(Message mensagem) throws RemoteException {
        return null;
    }

    @Override
    public synchronized Message showToDoList(Message mensagem) throws RemoteException {

        return mensagem;

    }

    @Override
    public synchronized Message completeAction(Message mensagem) throws RemoteException {
        return null;
    }

    @Override
    public synchronized Message viewPendingInvitations(Message mensagem) throws RemoteException {
        int resultado;
        Message msgid = getUsernameId(mensagem);
        mensagem.dataint = msgid.iduser;
        ResultSet rs = sql.doQuery("select * from meeting_member where idmember =" + mensagem.dataint + "' and accepted==NULL");
        try {
            while (rs.next()) {

                resultado = rs.getInt(1);
                if (resultado != 1) {

                    ResultSet rset = sql.doQuery("select (idmeeting.meeting,title.meeting,objective.meeting,date.meeting,location.meeting) from (meeting,meeting_member,member) where meeting_member.idmeeting = meeting.idmeeting and meeting_member.idmember = " + mensagem.dataint + "' and accepted==NULL");
                    mensagem.data = "ID Meeeting\t\tMeeting Descrition\t\t\tObjective\t\t\tDate\t\t\tLocation\n ";
                    while (rset.next()) {

                        int meetingid = rs.getInt("idmeeting");
                        String meetingdesc = rs.getString("title");
                        String obj = rs.getString("objective");
                        String d = rs.getString("date");
                        String loc = rs.getString("location");
                        mensagem.data += meetingid + "\t\t\t" + meetingdesc + "\t\t\t" + obj + "\t\t\t" + d + "\t\t\t" + loc + "\n";
                    }

                } else {
                    mensagem.data = "No pending invitations!";

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mensagem;


    }


    public synchronized Message listMembers(Message mensagem) throws RemoteException {

        mensagem.data = "ID User" + "\t\t" + "Name\n";
        ResultSet rs = sql.doQuery("select * from member where username NOT LIKE '" + mensagem.username + "'");
        try {
            while (rs.next()) {
                int idmember = rs.getInt("idmember");
                String user = rs.getString("username");
                mensagem.data += idmember + "\t\t" + user + "\n";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mensagem;
    }

    public synchronized Message listUpcomingMeetings(Message mensagem) throws RemoteException {
        mensagem.data = "ID Meeeting\t\tMeeting Descrition\t\t\tObjective\t\t\tDate\t\t\tLocation\n ";

        ResultSet rs = sql.doQuery("SELECT * FROM meeting where date > now();");
        try {
            while (rs.next()) {
                int meetingid = rs.getInt("idmeeting");
                String meetingdesc = rs.getString("title");
                String obj = rs.getString("objective");
                String d = rs.getString("date");
                String loc = rs.getString("location");
                mensagem.data += meetingid + "\t\t\t" + meetingdesc + "\t\t\t" + obj + "\t\t\t" + d + "\t\t\t" + loc + "\n";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mensagem;

    }

    @Override
    public Message listAllMeetings(Message mensagem) throws RemoteException {

        mensagem.data = "ID Meeeting\t\tMeeting Descrition\t\t\tObjective\t\t\tDate\t\t\tLocation\n ";

        ResultSet rs = sql.doQuery("SELECT * FROM meeting;");
        try {
            while (rs.next()) {
                int meetingid = rs.getInt("idmeeting");
                String meetingdesc = rs.getString("title");
                String obj = rs.getString("objective");
                String d = rs.getString("date");

                String loc = rs.getString("location");
                mensagem.data += meetingid + "\t\t\t" + meetingdesc + "\t\t\t" + obj + "\t\t\t" + d + "\t\t\t" + loc;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mensagem;


    }

    public synchronized Message register(Message mensagem) throws RemoteException {

        mensagem.result = true;

        String query = "INSERT INTO member (username,password,email) VALUES ('" + mensagem.username + "','" + mensagem.password + "','" + mensagem.mail + "');";
        System.out.println(query);
        if (sql.doUpdate(query) == 1) {

            return mensagem;
        }

        mensagem.result = false;

        return mensagem;


    }

    public synchronized Message login(Message mensagem) throws RemoteException {
        int resultado;
        mensagem.result = false;
        ResultSet rs = sql.doQuery("select count(*) from member where username = '" + mensagem.username + "' and password = '" + mensagem.password + "'");
        try {
            rs.next();
            resultado = rs.getInt(1);
            if (resultado != 1) {
                return mensagem;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mensagem.result = true;
        return mensagem;

    }


    public synchronized Message getUsernameId(Message mensagem) throws RemoteException {

        ResultSet rs = sql.doQuery("SELECT idmember from member where username='" + mensagem.username + "'");
        try {
            rs.next();
            mensagem.iduser = rs.getInt(1);
            return mensagem;

        } catch (SQLException e) {
            e.printStackTrace();
            return mensagem;
        }

    }


    public void ping() throws RemoteException {

    }

    //
    public void subscribe(TCPServer client) throws RemoteException {
        tcpServer = client;
        //new RMIServer();

    }

    public void run() {

        while (true)
            try {
                if (tcpServer != null) {
                    tcpServer.ping();
                    System.out.println("Ligado ao servidor TCP. Pingando...");
                    Thread.sleep(3000);
                }


            } catch (InterruptedException | RemoteException e) {
                e.printStackTrace();
            }
    }
}

