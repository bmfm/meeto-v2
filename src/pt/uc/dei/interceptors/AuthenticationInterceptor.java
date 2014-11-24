package pt.uc.dei.interceptors;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import java.util.Map;

public class AuthenticationInterceptor extends AbstractInterceptor {


    public String intercept(ActionInvocation invocation) throws Exception {

        Map<String, Object> session = ActionContext.getContext().getSession();

        String user = (String) session.get("username");

        if (user == null) {
            return Action.LOGIN;
        } else {

            return invocation.invoke();

        }


    }


    public void init() {

    }

    public void destroy() {
    }

}
