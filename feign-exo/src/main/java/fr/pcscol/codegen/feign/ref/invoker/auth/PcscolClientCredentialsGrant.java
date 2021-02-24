package fr.pcscol.codegen.feign.ref.invoker.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.scribejava.core.model.OAuth2AccessToken;
import feign.Feign;
import feign.form.FormEncoder;

public class PcscolClientCredentialsGrant extends OAuth {

    private CasAPI casAPI;
    private String username;
    private String password;

    public PcscolClientCredentialsGrant(String casHost, String username, String password) {
        super(null, null, null);
        casAPI = Feign.builder().encoder(new FormEncoder()).target(CasAPI.class, casHost);
        this.username = username;
        this.password = password;
    }

    @Override
    OAuth2AccessToken getOAuth2AccessToken() {
        System.out.println("Refresh Access Token...");
        //TODO implement
        return null;
    }

    @Override
    OAuthFlow getFlow() {
        return OAuthFlow.password;
    }
}
