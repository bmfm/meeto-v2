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

            return SUCCESS;

        } else return ERROR;


    }


}
