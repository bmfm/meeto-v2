package pt.uc.dei.actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import pt.uc.dei.models.LoginBean;

import java.rmi.RemoteException;
import java.util.Map;

/**
 * Created by brunomartins on 21/11/14.
 */
public class LoginAction extends ActionSupport implements SessionAware {

    private static final long serialVersionUID = 4L;
    Boolean outcome;
    private String userName=null;
    private String password=null;
    private Map<String, Object> session;


    public String execute() {


        LoginBean loginBean = new LoginBean();

        this.getLoginBean().setPassword(password);
        this.getLoginBean().setUserName(userName);

        try {
            outcome = loginBean.validate(userName, password);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        if (outcome){

            session.put("username", userName);
            session.put("loggedin", true); // this marks the user as logged in


            return SUCCESS;

        }
        else return ERROR;


    }

    public String home() {
        return SUCCESS;
    }

    public String logout() throws RemoteException {

        LoginBean loginBean = new LoginBean();

        outcome = loginBean.logout((String) session.get("username"));

        ServletActionContext.getRequest().getSession().invalidate();

        return SUCCESS;

    }

    public LoginBean getLoginBean() {
        if(!session.containsKey("loginBean"))
            this.setLoginBean(new LoginBean());
        return (LoginBean) session.get("loginBean");
    }

    public void setLoginBean(LoginBean loginBean) {
        this.session.put("loginBean", loginBean);
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;

    }
}





