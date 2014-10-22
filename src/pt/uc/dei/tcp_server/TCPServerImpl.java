package pt.uc.dei.tcp_server;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
import java.util.Properties;

public class TCPServerImpl extends UnicastRemoteObject implements TCPServer {


    //Hashtable que vai conter os users online
    static Hashtable<String, Thread> membersonline = new Hashtable<>();

    public TCPServerImpl() throws RemoteException {
        super();
    }


    public static void main(String args[]) {

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
            System.out.println("TCP Server ready! Seconday socket à escuta no porto " + props.getProperty("tcpServerPortAux"));
            System.out.println("LISTEN SOCKET=" + listenAuxSocket);

            while (true) {

                Socket clientSocket = listenMainSocket.accept();
                System.out.println("CLIENT_SOCKET (created at accept())=" + clientSocket);
                numero++;
                Connection clientMain = new Connection(clientSocket, numero);

                clientMain.start();
                Socket clientAuxSocket = listenAuxSocket.accept();
                System.out.println("CLIENT_SOCKET (created at accept())=" + clientAuxSocket);
                Events clientEvents = new Events(clientAuxSocket, numero);

                clientEvents.start();

            }
        } catch (IOException e) {
            System.out.println("Listen:" + e.getMessage());

        }
    }


    public void mainSocket() {


    }

    public void secondarySocket() {




    }


    public void ping() throws RemoteException {

    }
}
