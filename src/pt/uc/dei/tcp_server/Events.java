package pt.uc.dei.tcp_server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class Events extends Thread {

    ObjectInputStream in;
    ObjectOutputStream out = null;
    Socket clientSocket;
    int thread_number;
    String username = null;
    LinkedBlockingQueue<Message> queue = new LinkedBlockingQueue<>();
    TCPServerImpl tcpServer;

    public Events(Socket aClientSocket, int numero, TCPServerImpl tcpServer) {
        this.thread_number = numero;
        this.tcpServer = tcpServer;


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

    public void putMsgIntoQueue(Message msg) {
        queue.offer(msg);


    }

    //Não terei de ter uma 'thread só para ler o conteúdo da blocking queue?
    //Possivelmente o trabalho desta thread será só mesmo
    //estar a olhar para a LinkedBlokingQueue e assim que lá tiver alguma coisa enviar para o cliente respectivo.
    //Tudo o resto poderá ir para a connection



    public void run() {

        try {

            /*Properties props = new Properties();


            props.load(new FileInputStream("support/property"));


            System.getProperties().put("java.security.policy", "support/policy.all");
            System.setSecurityManager(new RMISecurityManager());

            //funciona apenas com lookup(core) mas torna-se um problema se o o cliente se tentar ligar ao servidor de backup
            RmiInterface c = (RmiInterface) LocateRegistry.getRegistry(props.getProperty("rmiServerip"), Integer.parseInt(props.getProperty("rmiServerPort1"))).lookup("rmi://" + props.getProperty("rmiServerip") + "/core");
            TCPServerImpl y = new TCPServerImpl();
            c.subscribe((TCPServer) y);

*/


            Message mensagemAux = (Message) in.readObject();
            this.username = mensagemAux.username;
            tcpServer.put(mensagemAux.username, this);
            System.out.println("Passou para a hashtabel");
            //Message checkmyinvitations = (Message) in.readObject();
            //checkmyinvitations = c.viewPendingInvitations(checkmyinvitations);
            //sendOut(checkmyinvitations);

            //testes apenas
           /*     Enumeration e = tcpServer.keys();
                while (e.hasMoreElements()) {

                    System.out.println(e.nextElement());

                }*/

            while (true) {

                System.out.println("entrou no consumer da queue");

                Message msg = queue.take();

                switch (msg.getTipo()) {

                    case (Message.ADDCHATMESSAGE):

                        break;

                }


            }


        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }



       /* while (true) {

            try {

                Message mensagemAux = (Message) in.readObject();

                switch (mensagemAux.getTipo()) {


                    case (Message.SENDTOHASH):
                        //assim que envia a mensagem para o tcpserver para o user ser colocado na Hashtable de user online, é logo definido o username da thread em si, para mais tarde utilizar o sendMsg()

                        this.username = mensagemAux.username;
                        tcpServer.put(mensagemAux.username, this);
                        break;

                    //TODO passar isto para a connection possivelmente
                    case (Message.CHECKONLINE):
                        mensagemAux.data = "Members currently online:\n";
                        Enumeration e = tcpServer.keys();
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


            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }*/


    }


}
