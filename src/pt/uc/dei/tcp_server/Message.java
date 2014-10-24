package pt.uc.dei.tcp_server;

import java.io.Serializable;
import java.net.Socket;

public class Message implements Serializable {

    public static final String LOG = "log";
    public static final String REG = "reg";
    public static final String CREATEMEETING = "createmeeting";
    public static final String RECONNET = "reconnect";
    public static final String POSTDISCUSSIONMESSAGE = "postdiscussionmessage";
    public static final String INVITETOMEETING = "invitetomeeting";
    public static final String LISTUPCOMINGMEETINGS = "listupcomingmeetings";
    public static final String MEETINGOVERVIEW = "meetingoverview";
    public static final String ACCEPTMEETING = "acceptmeeting";
    public static final String DECLINEMEETING = "declinemeeting";
    public static final String ADDAGENTAITEM = "addagendaitem";
    public static final String MODIFYAGENDAITEM = "modifyagendaitem";
    public static final String DELETEAGENDAITEM = "deleteagendaitem";
    public static final String ADDCHATMESSAGE = "addchatmessage";
    public static final String ADDKEYDECISION = "addkeydecision";
    public static final String ASSIGNACTION = "assignaction";
    public static final String SHOWTODOLIST = "showtodolist";
    public static final String COMPLETEACTION = "completeaction";
    public static final String LISTMEMBERS = "listmembers";
    public static final String LISTALLMEETINGS = "listallmeetings";
    public static final String SENDTOHASH = "sendtohash";
    public static final String CHECKONLINE = "checkonline";
    public static final String SENDINVITATIONS = "sendinvitations";
    public static final String CHECKINVITATIONS = "checkinvitations";
    public static final String CHECKCHATMESSAGES = "checkchatmessages";
    public static final String VIEWPENDINGINVITATIONS = "viewpendingninvitations";
    public static final String VERIFYMASTER = "verifymaster";


    private String tipo = "";
    public String username = "";
    public String password = "";
    public String mail = "";
    public String data = "";
    public String date;
    public String time;
    public String desiredoutcome = "";
    public int iduser;
    public Boolean result;
    public String list;
    public String location = "";
    public int dataint = 0;
    public String timestamp;
    public int delivered;
    public Socket socket;

    //mensagem para o user ser colocado na Hashtable (juntamente com o seu socket)
    public Message(String username, Socket socket, String tipo) {
        setTipo(tipo);
        this.username = username;
        this.socket = socket;


    }

    //mensagem geral para operacoes request-reply na thread connection
    public Message(String username, String password, String mail, String tipo) {
        setTipo(tipo);
        this.username = username;
        this.password = password;
        this.mail = mail;
    }


    //mensagem para colocar tudo o que o user introduziu na tabela system_message para efeitos de confirmação de recepção
    public Message(String username, String tipo, String data, String date, String time, String desiredoutcome, String list, String location) {


    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDesiredoutcome() {
        return desiredoutcome;
    }

    public void setDesiredoutcome(String desiredoutcome) {
        this.desiredoutcome = desiredoutcome;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getDataint() {
        return dataint;
    }

    public void setDataint(int dataint) {
        this.dataint = dataint;
    }


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        if (tipo != null)
            tipo = tipo.toLowerCase();
        this.tipo = tipo;
    }


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    public int getDelivered() {
        return delivered;
    }

    public void setDelivered(int delivered) {
        this.delivered = delivered;
    }
}