package pt.uc.dei.client;

import pt.uc.dei.tcp_server.Connection;
import pt.uc.dei.tcp_server.Message;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
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

    static EmbeddedHelper embDB = new EmbeddedHelper();

    public static void main(String[] args) throws InterruptedException, SQLException, ClassNotFoundException {
        TCPClient client = new TCPClient();
        try {

            embDB.startupEmbeddedDB();
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

            //thread que vai tratar de receber coisas que nao sao necessariamente requested pelo utilizador
            read.start();
            startUpOperations();


            while (true) {


                System.out.print("1-Create meeting\n2-List upcoming meetings\n3-View pending invitations\n4-View pending tasks\n6-Join Meetings\n7-List online users\n");
                op = sci.nextInt();

                //Create meeting
                if (op == 1) {
                    if (out != null && outAux != null) {
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
                            System.out.println("Meeting successfully created!");
                            Message invmsg = new Message(username_logged, null, null, "sendInvitations");
                            sendOutAux(invmsg);

                        } else {
                            System.out.println("Ocorreu um erro, por favor volte a tentar.");
                        }

                    } else {
                        System.out.println("Ligacao caiu..Estamos a trabalhar nisso...");
                    }
                }


                //List upcoming meetings and see their overview
                if (op == 2) {
                    if (out != null && outAux != null) {


                        Message mensagem = new Message(username_logged, null, null, "listupcomingmeetings");
                        sendOut(mensagem);
                        mensagem = (Message) in.readObject();
                        System.out.println(mensagem.data);
                        System.out.println("Do you wish to see the overview of any? (1)Yes (2)No");
                        int meetingop = sci.nextInt();
                        if (meetingop == 1) {
                            int overview;
                            Message meetingtocheck = new Message(username_logged, null, null, "meetingoverview");
                            System.out.println("Which one?:\n");
                            overview = scs.nextInt();
                            meetingtocheck.dataint = overview;
                            sendOut(meetingtocheck);
                            meetingtocheck = (Message) in.readObject();
                            System.out.println(meetingtocheck.data);

                        }




                    } else {
                        System.out.println("Ligacao caiu..Estamos a trabalhar nisso...");

                    }

                }

                //View pending invitations

                if (op == 3) {
                    if (out != null && outAux != null) {

                        int opnotifications;
                        String meetings;


                        Message mensagem = new Message(username_logged, null, null, "viewpendinginvitations");
                        sendOut(mensagem);
                        mensagem = (Message) in.readObject();
                        System.out.println(mensagem.data);
                        if (!mensagem.data.equalsIgnoreCase("No pending notifications!\n")) {
                            System.out.println("Do you wish to (1)Accept, (2)Decline or (3)Exit?\n");
                            opnotifications = sci.nextInt();
                            if (opnotifications == 1) {
                                System.out.println("Enter the id of the meetings you wish to accept, separated by commas:\n");
                                meetings = scs.nextLine();
                                Message acceptmsg = new Message(username_logged, null, null, "acceptmeeting");
                                acceptmsg.list = meetings;
                                sendOut(acceptmsg);
                                acceptmsg = (Message) in.readObject();
                                if (acceptmsg.result) {
                                    System.out.println("Meetings accepted.");
                                }

                            }
                            if (opnotifications == 2) {
                                System.out.println("Enter the id of the meetings you wish to decline, separated by commas:\n");
                                meetings = scs.nextLine();
                                Message declinemsg = new Message(username_logged, null, null, "declinemeeting");
                                declinemsg.list = meetings;
                                sendOut(declinemsg);
                                declinemsg = (Message) in.readObject();
                                if (declinemsg.result) {
                                    System.out.println("Meetings declined.");
                                }

                            }
                        }

                    } else {
                        System.out.println("Ligacao caiu..Estamos a trabalhar nisso...");

                    }

                }

                if (op == 4) {
                    if (out != null && outAux != null) {

                        Message mensagem = new Message(username_logged, null, null, "listupcomingmeetings");
                        sendOut(mensagem);
                        mensagem = (Message) in.readObject();
                        System.out.println(mensagem.data);

                    } else {
                        System.out.println("Ligacao caiu..Estamos a trabalhar nisso...");

                    }

                }

                if (op == 5) {
                    if (out != null && outAux != null) {

                        Message mensagem = new Message(username_logged, null, null, "listupcomingmeetings");
                        sendOut(mensagem);
                        mensagem = (Message) in.readObject();
                        System.out.println(mensagem.data);

                    } else {
                        System.out.println("Ligacao caiu..Estamos a trabalhar nisso...");

                    }

                }

                //List online users
                if (op == 6) {
                    if (out != null && outAux != null) {

                        Message mensagem = new Message(username_logged, null, null, "checkonline");
                        sendOutAux(mensagem);


                    } else {
                        System.out.println("Ligacao caiu..Estamos a trabalhar nisso...");

                    }

                }

                if (op == 7) {
                    if (out != null && outAux != null) {

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
            if (s != null && saux != null) {
                try {
                    s.close();
                    saux.close();
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

    public static void startUpOperations() throws IOException, ClassNotFoundException {

        //assim que o user faz login com sucesso, envia logo uma mensagem para a thread de events para esta guardar o username do member na Hashtable de users online (juntamente com a própria thread)
        Message msgtohash = new Message(username_logged, null, null, "sendtohash");
        sendOutAux(msgtohash);

        //verificar as invitations que possui
        Message checkmyinvitations = new Message(username_logged, null, null, "viewpendingnotifications");
        sendOutAux(checkmyinvitations);

        //TODO verificar se perdeu alguma CHAT msg enquanto estava offline

    }



    public static void sendOut(Message mensagem) {
        try {
            synchronized (out) {

                java.util.Date date = new java.util.Date();
                mensagem.setTimestamp(String.valueOf(date.getTime()));
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

    public static void saveToEmbeddedDB(Message mensagem) {

        //TODO tratar do delivery dos dados -> embedded e system_msg do lado do rmi

        embDB.doUpdate("INSERT INTO system_message (username,type,data,date,time,desiredoutcome,list,location,timestamp,delivered) VALUES ()");


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

                    //espera por uma mensagem do socket alternativo
                    Message mensagemAux = (Message) inAux.readObject();


                    if (mensagemAux.getTipo().equalsIgnoreCase("print")) {

                        System.out.println(mensagemAux.data);

                    }

                    if (mensagemAux.getTipo().equalsIgnoreCase("checkonline")) {


                        System.out.println(mensagemAux.data);
                    }

                    if (mensagemAux.getTipo().equalsIgnoreCase("viewpendingnotifications")) {
                        if (!mensagemAux.data.equalsIgnoreCase("No pending invitations!")) {
                            System.out.println("While you were away, you were invited to the following meetings:\n");
                            System.out.println(mensagemAux.data);
                            System.out.println("Please select 'View pending invitations' from the menu to accept or decline");

                        }

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
                Socket csaux = null;
                ObjectInputStream cinAux = null;
                ObjectOutputStream coutAux = null;
                while (true) {
                    try {

                        //dar 5 segundos ao server para voltar
                        ReadData.sleep(5000);
                        cs = new Socket(props.getProperty("tcpip1"), Integer.parseInt(props.getProperty("tcpServerPort")));
                        csaux = new Socket(props.getProperty("tcpip1"), Integer.parseInt(props.getProperty("tcpServerPortAux")));
                        cout = new ObjectOutputStream(cs.getOutputStream());
                        cin = new ObjectInputStream(cs.getInputStream());
                        coutAux = new ObjectOutputStream(cs.getOutputStream());
                        cinAux = new ObjectInputStream(cs.getInputStream());
                        TCPClient.setS(cs);
                        TCPClient.setOut(cout);
                        TCPClient.setIn(cin);
                        TCPClient.setSaux(csaux);
                        TCPClient.setInAux(cinAux);
                        TCPClient.setOutAux(coutAux);
                        this.in = cin;
                        this.inAux = cinAux;
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
