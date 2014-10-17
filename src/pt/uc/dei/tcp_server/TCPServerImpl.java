package pt.uc.dei.tcp_server; /**
 * Created with IntelliJ IDEA.
 * User: brunomartins
 * Date: 10/8/13
 * Time: 8:53 PM
 * To change this template use File | Settings | File Templates.
 */


import java.io.FileInputStream;
import java.io.IOException;
import java.net.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;

public class TCPServerImpl extends UnicastRemoteObject implements TCPServer {


    //static Vector<pt.uc.dei.tcp_server.Connection> clients = new Vector<pt.uc.dei.tcp_server.Connection>();



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

            ServerSocket listenSocket;
            listenSocket = new ServerSocket(Integer.parseInt(props.getProperty("tcpserverPort")));
            System.out.println("TCP Server ready! A Escuta no porto " + props.getProperty("tcpserverPort"));
            System.out.println("LISTEN SOCKET=" + listenSocket);


            while (true) {

                Socket clientSocket = listenSocket.accept(); // BLOQUEANTE
                System.out.println("CLIENT_SOCKET (created at accept())=" + clientSocket);
                numero++;
                Connection client = new Connection(clientSocket, numero);
                //clients.add(client);
                client.start();
            }
        } catch (IOException e) {
            System.out.println("Listen:" + e.getMessage());

        }
    }


    public void ping() throws RemoteException {

    }
}
