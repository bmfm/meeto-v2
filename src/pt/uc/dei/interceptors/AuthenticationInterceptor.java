package pt.uc.dei.interceptors;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class AuthenticationInterceptor extends AbstractInterceptor {

    HttpServletRequest request;


    public String intercept(ActionInvocation invocation) throws Exception {


        // hipotese 1

        ActionConfig config = invocation.getProxy().getConfig();
        Map parameters = config.getParams();
        String req1 = (String) parameters.get("req");

        //hipotese 2
        Map params = invocation.getInvocationContext().getParameters();
        String req2 = String.valueOf(params.get("req"));


        Map<String, Object> session = ActionContext.getContext().getSession();

        String user = (String) session.get("username");


        if (user == null) {
            return Action.LOGIN;


        }


        //to protect against online users trying to perform action from the url
        if (req1 == null && req2 == null) {
            addActionError(invocation, "You can't perform that action");
            return Action.ERROR;

        }


        return invocation.invoke();
    }

    private void addActionError(ActionInvocation invocation, String message) {
        Object action = invocation.getAction();
        if (action instanceof ValidationAware) {
            ((ValidationAware) action).addActionError(message);
        }
    }

    public void init() {

    }

    public void destroy() {
    }

    public HttpServletRequest getServletRequest() {
        return this.request;
    }

    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

}
