package pt.uc.dei.client;

import pt.uc.dei.tcp_server.Connection;
import pt.uc.dei.tcp_server.Message;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPClient {

    static String username_logged;
    static Socket s = null;
    static Socket saux = null;
    static ObjectInputStream in = null;
    static ObjectOutputStream out = null;
    static ObjectInputStream inAux = null;
    static ObjectOutputStream outAux = null;

    ReadData read = null;

    public static void main(String[] args) throws InterruptedException {
        TCPClient client = new TCPClient();
        try {
            client.startupClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startupClient() throws IOException, InterruptedException {
        System.out.println("TCP Client");
        Scanner sci = new Scanner(System.in);
        Scanner scs = new Scanner(System.in);

        int op;

        String username, password, mail;


        Properties props = new Properties();

        try {

            props.load(new FileInputStream("support/property"));

            Socket mainSocket = connect(props, Integer.parseInt(props.getProperty("tcpServerPort")));
            setS(mainSocket);

            System.out.println("ligou-se ao main socket");


            setOut(new ObjectOutputStream(s.getOutputStream()));
            setIn(new ObjectInputStream(s.getInputStream()));

            Socket secondarySocket = connect(props, Integer.parseInt(props.getProperty("tcpServerPortAux")));

            setSaux(secondarySocket);

            setOutAux(new ObjectOutputStream(saux.getOutputStream()));
            setInAux(new ObjectInputStream(saux.getInputStream()));

            System.out.println("ligou-se ao segundo");


            //thread que trata de receber mensagens
            read = new ReadData(in, inAux);

            //ciclo com menus de register e login
            System.out.println("Ligado ao Servidor...");


            while (true) {
                System.out.print("1 - Register\n2 - Login\n>");
                op = sci.nextInt();

                //register
                if (op == 1) {
                    System.out.println("Insira username: ");
                    username = scs.nextLine();
                    System.out.println("Insira password: ");
                    password = scs.nextLine();
                    System.out.println("Insira mail: ");
                    mail = scs.nextLine();
                    Message mensagem = new Message(username, password, mail, "reg");
                    sendOut(mensagem);
                    mensagem = (Message) in.readObject();
                    if (mensagem.result) {
                        System.out.println("Utilizador inserido com sucesso na base de dados.");

                    } else {
                        System.out.println("ERRO! Username ja se encontra registado.");
                    }

                    //login
                } else if (op == 2) {
                    System.out.println("Insira username: ");
                    username = scs.nextLine();
                    System.out.println("Insira password: ");
                    password = scs.nextLine();
                    Message mensagem = new Message(username, password, null, "log");
                    sendOut(mensagem);
                    mensagem = (Message) in.readObject();

                    if (mensagem.result) {

                        System.out.println("Login efectuado com sucesso.");
                        TCPClient.username_logged = username;
                        break;
                    } else {
                        System.out.println("ERRO! Username ou Password incorrectos.");
                        System.out.println("Se tem a certeza que os dados estão correctos, então o user já se encontra logado.");
                    }
                }
            }


            //ciclo com menu principal


            read.start();

            while (true) {


                System.out.print("1-Create meeting\n2-List upcoming meetings\n3-View pending invitations\n4-View pending tasks\n5-Get this user Id\n>");
                op = sci.nextInt();
                //Create meeting
                if (op == 1) {
                    if (out != null) {
                        Message mensagem = new Message(username_logged, null, null, "createMeeting");

                        System.out.println("Meeting title:");
                        String title = scs.nextLine();
                        mensagem.data = title;
                        System.out.println("Desired outcome:");
                        String desoutcome = scs.nextLine();
                        mensagem.desiredoutcome = desoutcome;
                        System.out.println("Date (YYYY-MM-DD):");
                        //TODO proteger a insercao de datas e horas. com try catch torna-se mais simpatico? exception que dá é: IllegalArgument
                        String d = scs.nextLine();
                        mensagem.date = d;
                        System.out.println("Time (HH:MM):");
                        String t = scs.nextLine();
                        mensagem.time = t;
                        System.out.println("Location:");
                        String loc = scs.nextLine();
                        mensagem.location = loc;
                        //TODO parar com o tipo de martelada que vem a seguir (mensagem2....)
                        System.out.println("Select members to invite (separate id's with a comma)");
                        Message mensagem2 = new Message(username_logged, null, null, "listMembers");
                        sendOut(mensagem2);
                        mensagem2 = (Message) in.readObject();
                        System.out.println(mensagem2.data);
                        String invitees = scs.nextLine();
                        mensagem.list = invitees;
                        sendOut(mensagem);
                        mensagem = (Message) in.readObject();
                        if (mensagem.result) {
                            System.out.println("Meeting criado com sucesso!");
                        } else {
                            System.out.println("Ocorreu um erro, por favor volte a tentar.");
                        }

                    } else {
                        System.out.println("Ligacao caiu..Estamos a trabalhar nisso...");
                    }
                }


                //List upcoming meetings
                if (op == 2) {
                    if (out != null) {

                        Message mensagem = new Message(username_logged, null, null, "listupcomingmeetings");
                        sendOut(mensagem);
                        mensagem = (Message) in.readObject();
                        System.out.println(mensagem.data);

                    } else {
                        System.out.println("Ligacao caiu..Estamos a trabalhar nisso...");

                    }

                }


            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    System.out.println("close:" + e.getMessage());
                }
            }
        }
    }

    public static Socket connect(Properties props, int port) throws InterruptedException {
        //tentativa de se ligar a qualquer um dos servers tcp disponiveis

        Socket socket = null;
        List<String> lstIp = new ArrayList<String>(2);
        lstIp.add(props.getProperty("tcpip1"));
        lstIp.add(props.getProperty("tcpip2"));

        //TODO não tenho de percorrer a porta também?.
        // Creio que percebi. Parte-se do principio que independentemente do IP, a porta onde eles se ligam é a mesma. Certo?
        //
        //


        while (socket == null) {

            for (String ip : lstIp) {
                try {
                    socket = (new Socket(ip, port));
                } catch (Exception ex) {

                }
                if (socket != null)
                    break;

                Thread.sleep(2000);
            }
        }

        return socket;
    }

    public static void setS(Socket aS) {
        s = aS;
    }

    public static void setSaux(Socket aS) {
        saux = aS;
    }

    public static void setIn(ObjectInputStream aIn) {
        in = aIn;
    }

    public static void setOut(ObjectOutputStream aOut) {
        out = aOut;
    }

    public static void setInAux(ObjectInputStream aIn) {
        inAux = aIn;
    }

    public static void setOutAux(ObjectOutputStream aOut) {
        outAux = aOut;
    }



    public static void sendOut(Message mensagem) {
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

    public static void sendOutAux(Message mensagem) {

        try {
            synchronized (outAux) {

                outAux.writeObject(mensagem);
                outAux.flush();
                outAux.reset();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}

class ReadData extends Thread {

    ObjectInputStream in;
    ObjectInputStream inAux;


    public ReadData(ObjectInputStream in, ObjectInputStream inAux) {
        this.in = in;
        this.inAux = inAux;

    }

    //=============================

    @Override
    public void run() {
        while (true) {
            try {
                while (true) {

                    //espera por uma mensagem
                    Message mensagemAux = (Message) inAux.readObject();

                    //print pedido request de credito
                    if (mensagemAux.getTipo().equalsIgnoreCase("print")) {

                        System.out.println(mensagemAux.data);

                    }



                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            } catch (EOFException e) {

                Properties props = new Properties();
                try {
                    props.load(new FileInputStream("support/property"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                TCPClient.out = null;
                ObjectInputStream cin = null;
                ObjectOutputStream cout = null;
                Socket cs = null;
                while (true) {
                    try {

                        //dar 5 segundos ao server para voltar
                        ReadData.sleep(5000);
                        cs = new Socket(props.getProperty("tcpip1"), Integer.parseInt(props.getProperty("tcpserverPort")));
                        cout = new ObjectOutputStream(cs.getOutputStream());
                        cin = new ObjectInputStream(cs.getInputStream());
                        TCPClient.setS(cs);
                        TCPClient.setOut(cout);
                        TCPClient.setIn(cin);
                        this.in = cin;
                        Message mensagem = new Message(TCPClient.username_logged, null, null, "reconnect");
                        cout.writeObject(mensagem);

                        break;
                    } catch (UnknownHostException ex) {
                        System.out.println("BOOM1");
                    } catch (IOException ex) {
                        System.out.println("BOOM2");
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
