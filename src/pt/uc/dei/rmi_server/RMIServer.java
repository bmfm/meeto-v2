package pt.uc.dei.rmi_server;

import pt.uc.dei.tcp_server.Message;
import pt.uc.dei.tcp_server.NotMasterException;
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
    static TCPServer tcpServer;
    List<Message> msgsToSend = new ArrayList<Message>();

    //TODO colocar array de users online o array na base de dados
    List<String> userToSend = new ArrayList<String>();
    HashSet<String> membersonline = new HashSet<>();
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
        //assim que o server começa,coloca o online status de todos os users a 0 para evitar ter users logados no sistema com a mesma conta
        sql.doUpdate("UPDATE member SET online=0");
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

        List<String> inviteeslist = Arrays.asList(mensagem.list.split(" "));
        mensagem.result = false;
        if ((sql.doUpdate("INSERT INTO meeting (title,objective,date,location) VALUES ('" + mensagem.data + "','" + mensagem.desiredoutcome + "','" + mensagem.date + " " + mensagem.time + "','" + mensagem.location + "');")) == 1) {

            ResultSet rs = sql.doQuery("SELECT MAX(idmeeting) FROM meeting;");
            try {
                rs.next();
                idmeeting = rs.getInt(1);

            } catch (SQLException e) {
                e.printStackTrace();
            }

            //TODO colocar o "all other business" no final de cada agenda

            //criar uma agenda vazia, só para gerar um idagenda
            sql.doUpdate("INSERT INTO agenda (idmeeting) VALUES ('" + idmeeting + "');");
            //colocar o criador da meeting na tabela meeting_member (como accepted naturalmente) antes de tratar dos outros
            sql.doUpdate("INSERT INTO meeting_member (idmeeting,idmember,accepted) VALUES ('" + idmeeting + "','" + mensagem.dataint + "','1');");

            mensagem.data = "You've been invited to a meeting!";
            //colocar os restantes na tabela meeting_member
            for (int i = 0; i < inviteeslist.size(); i++) {
                if ((sql.doUpdate("INSERT INTO meeting_member (idmeeting,idmember) VALUES ('" + idmeeting + "','" + inviteeslist.get(i) + "');")) == 1) {

                    mensagem.result = true;

                }

            }


            //TODO Distinguir entre chamada de método por Web e por CLI. Por web o RMI não tem ligação nenhuma a tcp servers nem threads de events

            /*List<String> usersList = new ArrayList<>();
            for (String user : inviteeslist) {
                usersList.add(getUserById(user));
            }
            String[] usersArray = new String[usersList.size()];

            try {
                mensagem.setTipo("checkinvitations");
                tcpServer.msgToMany(mensagem, usersList.toArray(usersArray));
            } catch (NotMasterException e) {
                e.printStackTrace();
            }
            return mensagem;*/


        }

        return mensagem;
    }


    public synchronized String getUserById(String id) throws RemoteException {
        ResultSet rs = sql.doQuery("SELECT username from member where idmember='" + id + "'");
        try {
            rs.next();
            return rs.getString(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public synchronized Message sendInvitations(Message mensagem) throws RemoteException {
        return null;
    }

    public synchronized String participantsInAMeeting(int idmeeting) {
        String participants = "Participants:\n";
        ResultSet rs = sql.doQuery("SELECT member.username FROM (member,meeting_member) where meeting_member.idmeeting='" + idmeeting + "' and member.idmember=meeting_member.idmember");
        try {
            while (rs.next()) {
                String member = rs.getString("username");
                participants += member + "\n";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return participants;
    }

    public synchronized String agendaItemsInAMeeting(int idmeeting) {
        String agendaitems = "Agenda Items\t\tDescription\t\tKeydecision\n";
        ResultSet rs = sql.doQuery("SELECT item.name,item.description,item.keydecision FROM (item,agenda) where agenda.idmeeting='" + idmeeting + "' and agenda.idagenda=item.idagenda");
        try {
            while (rs.next()) {
                String itemname = rs.getString("name");
                String itemdescription = rs.getString("description");
                String keydecision = rs.getString("keydecision");
                agendaitems += itemname + "\t\t" + itemdescription + "\t\t" + keydecision + "\n";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return agendaitems;
    }

    public synchronized String actionsInAMeeting(int idmeeting) {
        String actions = "Action description\t\tAsignee\t\tStatus\n";
        ResultSet rs = sql.doQuery("SELECT member.username,action.description,action.completed FROM (action,member,meeting) where action.idmeeting='" + idmeeting + "' and member.idmember=action.idmember and meeting.idmeeting=action.idmeeting");
        try {
            while (rs.next()) {
                String assignee = rs.getString("username");
                String actiondescription = rs.getString("description");
                String status = String.valueOf(rs.getInt("completed"));
                if ("0".equals(status)) {
                    status = "Not yet completed";
                } else {
                    status = "Closed";
                }
                actions += actiondescription + "\t\t" + assignee + "\t\t" + status + "\n";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return actions;
    }


    @Override
    public synchronized Message meetingOverview(Message mensagem) throws RemoteException {

        mensagem.data = participantsInAMeeting(mensagem.dataint);
        mensagem.data += ("\n\n");
        mensagem.data += agendaItemsInAMeeting(mensagem.dataint);
        mensagem.data += ("\n\n");
        mensagem.data += actionsInAMeeting(mensagem.dataint);
        return mensagem;

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

    public synchronized Message listMyMeetings(Message mensagem) throws RemoteException {
        Message msgid = getUsernameId(mensagem);
        mensagem.dataint = msgid.iduser;
        ResultSet rs = sql.doQuery("select distinct meeting.idmeeting,meeting.title,meeting.objective,meeting.date,meeting.location from (meeting,meeting_member,member) where meeting_member.idmeeting = meeting.idmeeting and meeting_member.idmember = '" + mensagem.dataint + "' and accepted=1");
        mensagem.data = "ID Meeeting\t\tMeeting Descrition\t\t\tObjective\t\t\tDate\t\t\tLocation\n ";
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
    public Message listAgendaItems(Message mensagem) throws RemoteException {
        mensagem.data = "ID:\t\tName:\t\tDescription:\t\tKey decision:\n";

        ResultSet rs = sql.doQuery("select item.iditem,item.name,item.description,item.keydecision from (agenda,item) where agenda.idagenda=item.idagenda and agenda.idmeeting='" + mensagem.dataint + "'");
        try {
            while (rs.next()) {
                int iditem = rs.getInt("iditem");
                String itemname = rs.getString("name");
                String itemdesc = rs.getString("description");
                String keydecision = rs.getString("keydecision");
                mensagem.data += iditem + "\t\t" + itemname + "\t\t" + itemdesc + "\t\t" + keydecision + "\n";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mensagem;


    }

    @Override
    public synchronized Message addAgendaItem(Message mensagem) throws RemoteException {
        int idagenda = getAgendaId(mensagem.dataint);
        mensagem.result = false;
        if ((sql.doUpdate("INSERT INTO item (idagenda,name,description) VALUES ('" + idagenda + "','" + mensagem.name + "','" + mensagem.description + "')")) == 1) {
            mensagem.result = true;
        }

        return mensagem;
    }

    @Override
    public synchronized Message modifyAgendaItem(Message mensagem) throws RemoteException {

        mensagem.result = false;
        //modificar o name do item
        if (mensagem.dataint2 == 1) {

            if ((sql.doUpdate("UPDATE item SET name='" + mensagem.data + "' where iditem='" + mensagem.dataint + "'")) == 1) {
                mensagem.result = true;

            }
            //modificar a descriçao
        } else if (mensagem.dataint2 == 2) {
            if ((sql.doUpdate("UPDATE item SET description='" + mensagem.data + "' where iditem='" + mensagem.dataint + "")) == 1) {
                mensagem.result = true;
            }

        }


        return mensagem;
    }

    @Override
    public synchronized Message deleteAgendaItem(Message mensagem) throws RemoteException {
        mensagem.result = false;
        if ((sql.doUpdate("DELETE FROM item where iditem='" + mensagem.dataint + "';")) == 1) {
            mensagem.result = true;
        }
        return mensagem;
    }

    @Override
    public synchronized Message addChatMessage(Message mensagem) throws RemoteException, NotMasterException {
        Message msgid = getUsernameId(mensagem);

        mensagem.result = false;
        int iduser = msgid.iduser;

        if ((sql.doUpdate("INSERT INTO log (iditem,idmember,line) VALUES ('" + mensagem.dataint + "','" + iduser + "','" + mensagem.data + "');")) == 1) {

            mensagem.result = true;
            //tcpServer.msgToMany(mensagem, mensagem.data);
            List<String> meetingMembers = Arrays.asList(getMeetingMembers(mensagem).data.split(" "));

            List<String> usersList = new ArrayList<>();
            for (String user : meetingMembers) {
                usersList.add(getUserById(user));
            }
            String[] usersArray = new String[usersList.size()];

            try {
                mensagem.setTipo("addchatmessage");
                tcpServer.msgToMany(mensagem, usersList.toArray(usersArray));
            } catch (NotMasterException e) {
                e.printStackTrace();
            }

        }

        return mensagem;

    }

    public synchronized Message listChat(Message mensagem) throws RemoteException {

        mensagem.data = "";

        ResultSet rs = sql.doQuery("select member.username,log.line from (member,log) where member.idmember = log.idmember and log.iditem='" + mensagem.dataint + "' order by log.idlog ASC");
        try {
            while (rs.next()) {

                String username = rs.getString("username");
                mensagem.data += "[" + username + "]:";
                String line = rs.getString("line");
                mensagem.data += line + "\n";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return mensagem;
    }

    @Override
    public synchronized Message addKeyDecision(Message mensagem) throws RemoteException {

        mensagem.result = false;

        if ((sql.doUpdate("UPDATE item SET keydecision='" + mensagem.keydecision + "' where iditem = '" + mensagem.dataint + "'")) == 1) {

            mensagem.result = true;
        }


        return mensagem;
    }

    @Override
    public synchronized Message assignAction(Message mensagem) throws RemoteException {

        mensagem.result = false;


        if ((sql.doUpdate("INSERT INTO action (idmeeting,idmember,description,completed) VALUES ('" + mensagem.dataint + "','" + mensagem.dataint2 + "','" + mensagem.data + "') where iditem = '" + mensagem.dataint + "'")) == 1) {

            mensagem.result = true;
        }


        return mensagem;

    }

    @Override
    public synchronized Message showToDoList(Message mensagem) throws RemoteException {

        Message msgid = getUsernameId(mensagem);
        mensagem.dataint = msgid.iduser;
        mensagem.data = "ID Action:\t\tDescription:\t\tCompleted:\n";
        ResultSet rs = sql.doQuery("select distinct action.idaction,action.description,action.completed from (action,meeting,member) where action.idmember ='" + mensagem.dataint + "' and action.idmember=member.idmember");
        try {
            while (rs.next()) {
                int idaction = rs.getInt("idaction");
                String actiondescription = rs.getString("description");
                String status = String.valueOf(rs.getInt("completed"));
                if ("0".equals(status)) {
                    status = "Not yet completed";
                } else {
                    status = "Closed";
                }
                mensagem.data += idaction + "\t\t" + actiondescription + "\t\t" + status + "\n";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return mensagem;

    }

    @Override
    public synchronized Message completeAction(Message mensagem) throws RemoteException {
        Message msgid = getUsernameId(mensagem);
        mensagem.dataint = msgid.iduser;

        List<String> completelist = Arrays.asList(mensagem.list.split(","));

        mensagem.result = false;
        //aceitar as meetings escolhidas
        for (int i = 0; i < completelist.size(); i++) {
            if ((sql.doUpdate("UPDATE action SET completed='1' where idmember='" + mensagem.dataint + "'and idmeeting='" + completelist.get(i) + "'")) == 1)

            {

                mensagem.result = true;
            }

        }


        return mensagem;
    }

    @Override
    public synchronized Message viewPendingInvitations(Message mensagem) throws RemoteException {
        int resultado;
        Message msgid = getUsernameId(mensagem);
        mensagem.dataint = msgid.iduser;

        ResultSet rs = sql.doQuery("select * from meeting_member where idmember ='" + mensagem.dataint + "' and accepted IS NULL");
        try {
            while (rs.next()) {

                resultado = rs.getInt(1);
                if (resultado != 1) {

                    mensagem.data = "My pending invitations:\n";

                    ResultSet rset = sql.doQuery("select distinct meeting.idmeeting,meeting.title,meeting.objective,meeting.date,meeting.location from (meeting,meeting_member,member) where meeting_member.idmeeting = meeting.idmeeting and meeting_member.idmember = '" + mensagem.dataint + "' and accepted IS NULL");
                    mensagem.data = "ID Meeeting\t\tMeeting Descrition\t\t\tObjective\t\t\tDate\t\t\tLocation\n ";
                    while (rset.next()) {

                        int meetingid = rset.getInt("idmeeting");
                        String meetingdesc = rset.getString("title");
                        String obj = rset.getString("objective");
                        String d = rset.getString("date");
                        String loc = rset.getString("location");
                        mensagem.data += meetingid + "\t\t\t" + meetingdesc + "\t\t\t" + obj + "\t\t\t" + d + "\t\t\t" + loc + "\n";
                    }

                } else {
                    mensagem.data = "0";

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mensagem;


    }

    // listar os membros de uma meeting
    public synchronized Message getMeetingMembers(Message mensagem) throws RemoteException {
        mensagem.data = "";
        ResultSet rs = sql.doQuery("select * from meeting_member where idmeeting = '" + mensagem.dataint2 + "'");
        try {
            while (rs.next()) {
                int idmember = rs.getInt("idmember");
                mensagem.data += idmember + " " + "\n";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mensagem;
    }

    //listar todos menos o actual que está a fazer o pedido. Usado essencialmente aquando da criação da meeting
    public synchronized Message listMembers(Message mensagem) throws RemoteException {

        mensagem.data = "";
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

    @Override
    public synchronized Message listAllMembers(Message mensagem) throws RemoteException {

        mensagem.data = "ID User" + "\t\t" + "Name\n";
        ResultSet rs = sql.doQuery("select * from member");
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
    public Message listPastMeetings(Message mensagem) throws RemoteException {

        mensagem.data = "ID Meeeting\t\tMeeting Descrition\t\t\tObjective\t\t\tDate\t\t\tLocation\n ";

        ResultSet rs = sql.doQuery("SELECT * FROM meeting where date < now();");
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
        ResultSet rs = sql.doQuery("select count(*) from member where username = '" + mensagem.username + "' and password = '" + mensagem.password + "' and online = 0");
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
        sql.doUpdate("UPDATE member SET online=1 where username='" + mensagem.username + "'");
        return mensagem;

    }

    public synchronized Message logout(Message mensagem) throws RemoteException {
        int idusr;
        Message msgid = getUsernameId(mensagem);
        idusr = msgid.iduser;

        System.out.println(idusr);
        sql.doUpdate("UPDATE member SET online=0 where idmember='" + idusr + "'");

        return mensagem;
    }

    @Override
    public Message onlineUsers(Message mensagem) throws RemoteException {
        mensagem.data = "Members currently online:\n";

        ResultSet rs = sql.doQuery("SELECT username FROM member where online=1;");
        try {
            while (rs.next()) {
                String member = rs.getString("username");
                mensagem.data += member + "\n";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public synchronized int getAgendaId(int idmeeting) throws RemoteException {

        int agendaid = 0;
        ResultSet rs = sql.doQuery("SELECT idagenda from agenda where idmeeting='" + idmeeting + "'");
        try {
            rs.next();
            agendaid = rs.getInt(1);
            return agendaid;


        } catch (SQLException e) {
            e.printStackTrace();

        }
        return agendaid;


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

