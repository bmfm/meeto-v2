package pt.uc.dei.tcp_server;

import java.io.Serializable;

public class Message implements Serializable {

    public static final String LOG = "log";
    public static final String REG = "reg";
    public static final String CREATEMEETING = "createmeeting";



    private String tipo="";
    public String username="";
    public String password="";
    public String mail="";
    public String data="";
    public int iduser;
    public Boolean result;


    //geral
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
        if(tipo != null)
            tipo = tipo.toLowerCase();
        this.tipo = tipo;
    }
}