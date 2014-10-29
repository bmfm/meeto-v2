package pt.uc.dei.tcp_server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPServerImpl extends UnicastRemoteObject implements TCPServer {

//TODO FAZER EXPORT DO CLASS DIAGRAM E METER NO RELATÓRIO


    //Hashtable que vai guardar a relação de users com as suas threads
    private Hashtable<String, Events> membersonline = new Hashtable<>();
    private boolean master = false;

    public TCPServerImpl() throws RemoteException {
        super();
    }


    public static void main(String args[]) throws RemoteException {
        TCPServerImpl tcpimp = new TCPServerImpl();
        tcpimp.init(args);
        UDPSender udps = new UDPSender(tcpimp);
        udps.start();


    }


    private void validateIfMaster() throws NotMasterException {
        if (!master) {
            throw new NotMasterException();
        }
    }



    public void init(String args[]) {


        if (args.length > 0) {
            if ("1".equals(args[0])) {
                master = true;
            }

        }


        int numero = 0;
        Properties props = new Properties();

        try {
            props.load(new FileInputStream("support/property"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {

            ServerSocket listenMainSocket;
            ServerSocket listenAuxSocket;
            listenMainSocket = new ServerSocket(Integer.parseInt(props.getProperty("tcpServerPort")));
            System.out.println("TCP Server ready! Main socket à escuta no porto " + props.getProperty("tcpServerPort"));
            System.out.println("LISTEN SOCKET=" + listenMainSocket);
            listenAuxSocket = new ServerSocket(Integer.parseInt(props.getProperty("tcpServerPortAux")));
            System.out.println("TCP Server ready! Secondary socket à escuta no porto " + props.getProperty("tcpServerPortAux"));
            System.out.println("LISTEN SOCKET=" + listenAuxSocket);

            while (true) {

                Socket clientSocket = listenMainSocket.accept();
                System.out.println("CLIENT_SOCKET (created at accept())=" + clientSocket);
                numero++;
                Connection clientMain = new Connection(clientSocket, numero);

                clientMain.start();
                Socket clientAuxSocket = listenAuxSocket.accept();
                System.out.println("CLIENT_SOCKET (created at accept())=" + clientAuxSocket);
                Events clientEvents = new Events(clientAuxSocket, numero, this);

                clientEvents.start();

            }
        } catch (IOException e) {
            System.out.println("Listen:" + e.getMessage());

        }
    }


    public synchronized Collection<Events> values() {
        return membersonline.values();
    }

    public synchronized Enumeration<String> keys() {
        return membersonline.keys();
    }

    public synchronized Events put(String key, Events value) {
        return membersonline.put(key, value);
    }

    public synchronized Events get(Object key) {
        return membersonline.get(key);
    }

    public synchronized boolean contains(Object value) {
        return membersonline.contains(value);
    }

    public synchronized boolean containsKey(Object key) {
        return membersonline.containsKey(key);
    }

    public synchronized boolean containsValue(Object value) {
        return membersonline.containsValue(value);
    }

    public synchronized void ping() throws RemoteException {

    }

    //metodo chamado pelo RMI para enviar uma mensagem para varios utilizadores ou várias mensagens para um só utilizador.

    public synchronized void sendMsg(Message[] messages, String[] usernames) throws RemoteException, NotMasterException {
        validateIfMaster();
        for (Message m : messages) {
            for (String u : usernames) {

                Events threadEvent = membersonline.get(u);
                threadEvent.putMsgIntoQueue(m);
            }

        }

    }

    //mandar uma msg para diversos utilizadores
    public synchronized void msgToMany(Message m, String... u) throws RemoteException, NotMasterException {
        Message[] msgs = new Message[1];
        msgs[0] = m;
        this.sendMsg(msgs, u);

    }


    //mandar varias msgs para o memso utilizador
    public void msgsToOne(String u, Message... m) throws RemoteException, NotMasterException {
        String[] user = new String[1];
        user[0] = u;
        this.sendMsg(m, user);
    }

    public void switchToMaster(boolean isMaster) {
        master = isMaster;

    }

    private void printHash() {
        Enumeration e = this.keys();
        while (e.hasMoreElements()) {

            System.out.println(e.nextElement());

        }
    }


}

class UDPSender extends Thread {

    TCPServerImpl tcpServer = null;


    public UDPSender(TCPServerImpl tcpServer) {

        this.tcpServer = tcpServer;


    }

    public void run() {

        Properties props = new Properties();

        try {
            props.load(new FileInputStream("property"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        DatagramSocket uSocket;
        try {
            uSocket = new DatagramSocket();
            //TODO em vez de ser com I AM ALive, verificar primeiro o estado de master e enviar um ping consoante isso "I AM MASTER", "I AM SLAVE"
            byte[] m = "I AM ALIVE".getBytes();
            //TODO mudar para ir trocando de ip constantemente
            InetAddress aHost = InetAddress.getByName(props.getProperty("tcpip2"));
            DatagramPacket msg = new DatagramPacket(m, m.length, aHost, Integer.parseInt(props.getProperty("udpPort")));
            while (true) {
                uSocket.send(msg);
                //envia pings de 3 em 3 segundos
                this.currentThread().sleep(3000);

            }
        } catch (SocketException e) {
            tcpServer.switchToMaster(true);
        } catch (IOException e) {
            tcpServer.switchToMaster(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class UDPReceiver extends Thread {

    static DatagramSocket uSocket;

    static Properties props = new Properties();

    public UDPReceiver() {

    }

    public void run() {

        try {

            props.load(new FileInputStream("support/property"));


            byte[] buffer = new byte[1024];
            uSocket = null;

            uSocket = new DatagramSocket(Integer.parseInt(props.getProperty("udpPort")));
            //Esperar 10 segundos pelo server tcp principal para lhe dar tempo para voltar up
            uSocket.setSoTimeout(10000);
            DatagramPacket request;


            while (true) {

                try {
                    request = new DatagramPacket(buffer, buffer.length);

                    uSocket.receive(request);
                    System.out.println("Backup server waiting for his turn...");

                } catch (IOException ex) {
                    break;
                }

            }
        } catch (SocketException ex) {
            Logger.getLogger(UDPReceiver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("[Primary TCP server has crashed!]");
        System.out.println("[Backup TCP Server has just started!]");
        uSocket.close();

    }


}
