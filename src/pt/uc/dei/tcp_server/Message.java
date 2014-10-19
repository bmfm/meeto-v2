package pt.uc.dei.tcp_server;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class Message implements Serializable {

    public static final String LOG = "log";
    public static final String REG = "reg";
    public static final String CREATEMEETING = "createmeeting";
    public static final String RECONNET = "reconnect";
    public static final String POSTDISCUSSIONMESSAGE = "postdiscussionmessage";
    public static final String INVITETOMEETING = "invitetomeeting";
    public static final String LISTUPCOMINGMEETINGS = "listupcommingmeetings";
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


    private String tipo = "";
    public String username = "";
    public String password = "";
    public String mail = "";
    public String data = "";
    public Date date;
    public Time time;
    public String desiredoutcome = "";
    public int iduser;
    public Boolean result;
    public String list;
    public String location = "";
    public int dataint = 0;

    public Message(String username, String password, String mail, String tipo) {
        setTipo(tipo);
        this.username = username;
        this.password = password;
        this.mail = mail;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        if (tipo != null)
            tipo = tipo.toLowerCase();
        this.tipo = tipo;
    }
}