package pt.uc.dei.actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import pt.uc.dei.models.LoginBean;

import java.util.Map;

/**
 * Created by brunomartins on 21/11/14.
 */
public class LoginAction extends ActionSupport implements SessionAware {

    private static final long serialVersionUID = 4L;

    private String userName=null;
    private String password=null;



    private Map<String, Object> session;


    public String execute() {

        Boolean outcome;

        LoginBean loginBean = new LoginBean();

        this.getLoginBean().setPassword(password);
        this.getLoginBean().setUserName(userName);


        outcome = loginBean.validate(userName, password);
        if (outcome){

            session.put("username", userName);
            session.put("loggedin", true); // this marks the user as logged in


            return SUCCESS;
        }
        else return ERROR;


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





