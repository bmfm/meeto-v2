package pt.uc.dei.tcp_server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Events extends Thread {

    ObjectInputStream in;
    ObjectOutputStream out = null;
    Socket clientSocket;
    int thread_number;
    String username = null;

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


    public void run() {

        //TODO uma vez que esta thread é criada assim que um user se liga, pode ficar logo à espera de uma mensagem do user. User envia logo o seu username.
        //TODO Assim que recebe o username cria uma ligacao ao RMI server e pergunta se tem lá alguma coisa pendente em nome dele.
        //TODO Implementar a lógica do user online : hashmaps com o user como key e socket como value

        Message mensagem = new Message(null, null, null, "print");
        mensagem.data = "Mensagem enviada a partir do events";
        try {
            Thread.sleep(10000);
            sendOut(mensagem);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


}
