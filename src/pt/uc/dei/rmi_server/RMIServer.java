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
import java.util.Properties;

public class RMIServer extends UnicastRemoteObject implements RmiInterface, Runnable {

    static TCPServer x;
    MySQL sql = new MySQL();
    //Vector<String> online_users;

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
        //this.online_users = new Vector<String>();
    }

    public static void main(String[] args) {

        try {
            RMIServer myServer = new RMIServer();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Message createMeeting(Message mensagem) throws RemoteException {

        mensagem.result = true;


        return mensagem;

    }


    public Message listMeetings(Message mensagem) throws RemoteException {
        mensagem.data = "ID Meeeting" + "\t" + "Meeting Name\n";

        ResultSet rs = sql.doQuery("SELECT * FROM meeting;");
        try {
            while (rs.next()) {
                int meetingid = rs.getInt("idmeeting");
                String meetingdesc = rs.getString("title");
                mensagem.data += meetingid + "\t\t\t" + meetingdesc + "\n";
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
        //TODO colocar este user como online usando hashmap
        mensagem.result = true;
        return mensagem;

    }


    public synchronized Message getUsernameId(Message mensagem) throws RemoteException {

        ResultSet rs = sql.doQuery("SELECT idmember from members where username='" + mensagem.username + "'");
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

    public void subscribe(TCPServer client) throws RemoteException {
        x = client;
        //new RMIServer();

    }

    public void run() {

        while (true)
            try {
                if (x != null) {
                    x.ping();
                    System.out.println("Ligado ao servidor TCP. Pingando...");
                    Thread.sleep(3000);
                }


            } catch (InterruptedException e) {
            } catch (RemoteException e) {
                e.printStackTrace();
            }
    }
}

