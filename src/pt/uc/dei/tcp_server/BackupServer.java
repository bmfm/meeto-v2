package pt.uc.dei.tcp_server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BackupServer {


    //vector com clientes online

    static Vector<Connection> clients = new Vector<Connection>();

    static DatagramSocket uSocket;

    static Properties props = new Properties();


    public static void main(String[] args) {
        try {

            props.load(new FileInputStream("support/property"));


            byte[] buffer = new byte[1024];
            uSocket = null;

            uSocket = new DatagramSocket(Integer.parseInt(props.getProperty("udpPort")));
            //Esperar 10 segundos pelo server tcp principal para lhe dar tempo para voltar up
            uSocket.setSoTimeout(5000);
            DatagramPacket request;

            System.out.println("Entrou no ");

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
            Logger.getLogger(BackupServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("[Primary TCP server has crashed!]");
        System.out.println("[Backup Server has just started!]");
        uSocket.close();


        int numero = 0;

        try {

            ServerSocket listenSocket;
            listenSocket = new ServerSocket(Integer.parseInt(props.getProperty("tcpbserverPort")));
            System.out.println("TCP Backup Server ready! A Escuta no porto " + props.getProperty("tcpbserverPort"));
            System.out.println("LISTEN SOCKET=" + listenSocket);

            while (true) {
                Socket clientSocket = listenSocket.accept(); // BLOQUEANTE
                System.out.println("CLIENT_SOCKET (created at accept())=" + clientSocket);
                numero++;
                Connection client = new Connection(clientSocket, numero);
                clients.add(client);
                client.start();
            }
        } catch (IOException e) {
            System.out.println("Listen:" + e.getMessage());

        }
    }
}

