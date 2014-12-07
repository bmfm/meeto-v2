package pt.uc.dei.google;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.GoogleApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;


public class GoogleLogin {

    private static final String NETWORK_NAME = "Google";
    private static final String AUTHORIZE_URL = "https://www.google.com/accounts/OAuthAuthorizeToken?oauth_token=";
    private static final String PROTECTED_RESOURCE_URL = "https://docs.google.com/feeds/default/private/full/";
    private static final String SCOPE = "https://docs.google.com/feeds/";
    private static final Token EMPTY_TOKEN = null;
    private static final String CALLBACK_URL = "http://localhost:8080/meeto/calendar.jsp";


    public String goToLogin(){
        OAuthService service = new ServiceBuilder()
                .provider(GoogleApi.class)
                .apiKey("159167502512-9c66e9am5lj9lrq33v3dant27fjt5k4d.apps.googleusercontent.com")
                .apiSecret("jtsOyhazWTJUnQjf2FZklf1Q")
                .callback(CALLBACK_URL)
                .scope(SCOPE)
                .build();


        System.out.println("=== " + NETWORK_NAME + "'s OAuth Workflow ===");
        System.out.println();

        System.out.println("Fetching the Authorization URL...");
        String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);
        System.out.println("Got the Authorization URL!");
        System.out.println("Now go and authorize Scribe here:");
        System.out.println(authorizationUrl);

        return authorizationUrl;
    }
}
