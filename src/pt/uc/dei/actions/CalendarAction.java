package pt.uc.dei.actions;

import com.opensymphony.xwork2.ActionSupport;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.GoogleApi;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;

/**
 * Created by bruno on 07/12/14.
 */
public class CalendarAction extends ActionSupport {

    private static final String NETWORK_NAME = "Google";
    private static final String AUTHORIZE_URL = "https://www.google.com/accounts/OAuthAuthorizeToken?oauth_token=";
    //private static final String PROTECTED_RESOURCE_URL = "https://docs.google.com/feeds/default/private/full/";
    private static final String PROTECTED_RESOURCE_URL_GET_PROFILE = "https://www.googleapis.com/userinfo/v2/me";
    private static final String SCOPE = "https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/calendar";
    private static final Token EMPTY_TOKEN = null;
    private static final String CALLBACK_URL = "http://localhost:8080/meeto/finishGoogleLogin.action";
    private String authorizationUrl;
    private String code;

    private OAuthService service = new ServiceBuilder()
            .provider(GoogleApi.class)
            .apiKey("159167502512-9c66e9am5lj9lrq33v3dant27fjt5k4d.apps.googleusercontent.com")
            .apiSecret("jtsOyhazWTJUnQjf2FZklf1Q")
            .callback(CALLBACK_URL)
            .scope(SCOPE)
            .build();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String goToLogin() {
        try {

            System.out.println("=== " + NETWORK_NAME + "'s OAuth Workflow ===");
            System.out.println();

            System.out.println("Fetching the Authorization URL...");
            String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);
            System.out.println("Got the Authorization URL!");
            System.out.println("Now go and authorize Scribe here:");
            System.out.println(authorizationUrl);

            this.authorizationUrl = authorizationUrl;
            return SUCCESS;
        } catch (Exception ex) {
            return ERROR;
        }
    }

    public String getAuthorizationUrl() {
        return authorizationUrl;
    }

    public void setAuthorizationUrl(String authorizationUrl) {
        this.authorizationUrl = authorizationUrl;
    }

    public String finishGoogleLogin() throws Exception {
        Verifier verifier = null;
        // Verifier verifier = new Verifier(access_token);
        Token accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
        System.out.println(code);

        OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL_GET_PROFILE);
        service.signRequest(accessToken, request);
        Response response = request.send();


        //TODO obter registo do user que contem o email da google e marcar como online

        return "success";
    }
}
