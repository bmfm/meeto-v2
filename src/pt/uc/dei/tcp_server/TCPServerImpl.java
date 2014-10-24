package pt.uc.dei.tcp_server;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

public class TCPServerImpl extends UnicastRemoteObject implements TCPServer {


    //Hashtable que vai conter os users online
    private Hashtable<String, Thread> membersonline = new Hashtable<>();
    private boolean master = false;

    public TCPServerImpl() throws RemoteException {
        super();
    }


    public static void main(String args[]) throws RemoteException {
        TCPServerImpl tcpimp = new TCPServerImpl();
        tcpimp.init(args);

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


    public synchronized Collection<Thread> values() {
        return membersonline.values();
    }

    public synchronized Enumeration<String> keys() {
        return membersonline.keys();
    }

    public synchronized Thread put(String key, Thread value) {
        return membersonline.put(key, value);
    }

    public synchronized Thread get(Object key) {
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

    public void ping() throws RemoteException {

    }


}
