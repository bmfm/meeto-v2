package pt.uc.dei.google;

/**
 * Created by bruno on 07/12/14.
 */

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.GoogleApi;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;

import java.util.Scanner;

public class GoogleExample {
    private static final String NETWORK_NAME = "Google";
    private static final String AUTHORIZE_URL = "https://www.google.com/accounts/OAuthAuthorizeToken?oauth_token=";
    private static final String PROTECTED_RESOURCE_URL = "https://docs.google.com/feeds/default/private/full/";
    private static final String SCOPE = "https://docs.google.com/feeds/";
    private static final Token EMPTY_TOKEN = null;
    private static final String CALLBACK_URL = "http://localhost:8080/meeto/calendar.jsp";

    public static void main(String[] args) {
        OAuthService service = new ServiceBuilder()
                .provider(GoogleApi.class)
                .apiKey("159167502512-9c66e9am5lj9lrq33v3dant27fjt5k4d.apps.googleusercontent.com")
                .apiSecret("jtsOyhazWTJUnQjf2FZklf1Q")
                .callback(CALLBACK_URL)
                .scope(SCOPE)
                .build();
        Scanner in = new Scanner(System.in);

        System.out.println("=== " + NETWORK_NAME + "'s OAuth Workflow ===");
        System.out.println();

        System.out.println("Fetching the Authorization URL...");
        String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);
        System.out.println("Got the Authorization URL!");
        System.out.println("Now go and authorize Scribe here:");
        System.out.println(authorizationUrl);
        System.out.println("And paste the authorization code here");
        System.out.print(">>");
        Verifier verifier = new Verifier(in.nextLine());
        System.out.println();

        // Trade the Request Token and Verfier for the Access Token
        System.out.println("Trading the Request Token for an Access Token...");
        Token accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
        System.out.println("Got the Access Token!");
        System.out.println("(if your curious it looks like this: " + accessToken + " )");
        System.out.println();

        // Now let's go and ask for a protected resource!
        System.out.println("Now we're going to access a protected resource...");
        OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        service.signRequest(accessToken, request);
        Response response = request.send();
        System.out.println("Got it! Lets see what we found...");
        System.out.println();
        System.out.println(response.getCode());
        System.out.println(response.getBody());


        System.out.println();
        System.out.println("Thats it man! Go and build something awesome with Scribe! :)");

    }
}