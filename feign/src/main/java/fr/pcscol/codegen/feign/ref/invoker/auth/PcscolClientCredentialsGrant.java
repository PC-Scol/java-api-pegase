package fr.pcscol.codegen.feign.ref.invoker.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.scribejava.core.model.OAuth2AccessToken;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PcscolClientCredentialsGrant extends OAuth {

    private HttpClient httpClient;
    private HttpRequest httpRequest;

    public PcscolClientCredentialsGrant(String casUrl, String login, String password) {
        super(null, null, null);
        httpClient = HttpClient.newHttpClient();
        httpRequest = HttpRequest.newBuilder(URI.create(casUrl))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("username=" + login + "&password=" + password + "&token=true"))
                .build();
    }

    @Override
    OAuth2AccessToken getOAuth2AccessToken() {
        System.out.println("Refresh Access Token...");
        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            DecodedJWT decodedJWT = JWT.decode(response.body());
            System.out.println("Access Token : " + response.body());
            System.out.println("Access Token will be refreshed at : " + decodedJWT.getExpiresAt());
            int expireInSeconds = (int) (decodedJWT.getExpiresAt().getTime() - System.currentTimeMillis()) / 1000;
            return new OAuth2AccessToken(response.body(), "jwt", expireInSeconds, null, null, null);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    OAuthFlow getFlow() {
        return OAuthFlow.password;
    }
}
