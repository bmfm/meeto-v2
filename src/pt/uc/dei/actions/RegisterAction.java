package pt.uc.dei.actions;

import com.opensymphony.xwork2.ActionSupport;
import pt.uc.dei.models.RegisterBean;

import java.rmi.RemoteException;


/**
 * Created by bruno on 22/11/14.
 */
public class RegisterAction extends ActionSupport {
    private static final long serialVersionUID = 4L;
    Boolean outcome;
    private String userName = null;
    private String password = null;
    private String repeatpassword = null;
    private String mail = null;
    private String req;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setRepeatpassword(String repeatpassword) {
        this.repeatpassword = repeatpassword;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String execute() {

        RegisterBean regBean = new RegisterBean();

        regBean.setPassword(password);
        regBean.setUserName(userName);
        regBean.setMail(mail);

        try {
            if (password.equalsIgnoreCase(repeatpassword)) {

                outcome = regBean.register();

            } else return ERROR;

        } catch (RemoteException e) {
            e.printStackTrace();
        }

        if (outcome) {

            return LOGIN;

        } else return ERROR;


    }

    public String getReq() {
        return req;
    }

    public void setReq(String req) {
        this.req = req;
    }
}
