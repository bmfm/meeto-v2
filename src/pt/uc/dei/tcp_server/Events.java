package pt.uc.dei.tcp_server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Enumeration;
import java.util.concurrent.LinkedBlockingQueue;

public class Events extends Thread {

    ObjectInputStream in;
    ObjectOutputStream out = null;
    Socket clientSocket;
    int thread_number;
    String username = null;
    LinkedBlockingQueue queue = new LinkedBlockingQueue();

    public Events(Socket aClientSocket, int numero) {
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

    public synchronized void sendMsg(Message msg) {


    }


    public void run() {


        //TODO Assim que recebe o username cria uma ligacao ao RMI server e pergunta se tem lá alguma coisa pendente em nome dele.


        while (true) {

            try {

                Message mensagemAux = (Message) in.readObject();

                switch (mensagemAux.getTipo()) {


                    case (Message.SENDTOHASH):
                        TCPServerImpl.membersonline.put(mensagemAux.username, this);
                        break;


                    case (Message.CHECKONLINE):
                        mensagemAux.data = "Members currently online:\n";
                        Enumeration e = TCPServerImpl.membersonline.keys();
                        while (e.hasMoreElements()) {

                            mensagemAux.data += e.nextElement();

                        }
                        sendOut(mensagemAux);
                        break;

                    case (Message.SENDINVITATIONS):


                        break;

                    case (Message.CHECKINVITATIONS):


                        break;

                    case (Message.CHECKCHATMESSAGES):

                        break;



                }


            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }


    }


}
