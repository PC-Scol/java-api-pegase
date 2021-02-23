package fr.pcscol.codegen.nativ;

import fr.pcscol.codegen.nativ.ref.api.StructureApi;
import fr.pcscol.codegen.nativ.ref.invoker.ApiClient;
import fr.pcscol.codegen.nativ.ref.invoker.ApiException;
import fr.pcscol.codegen.nativ.ref.model.Structure;

import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class JavaNativeClientApiInvocation {

    /**
     * API HOST
     */
    public static final String API_HOST = "ref.rdd.pc-scol.fr";

    /**
     * CAS URL
     */
    public static final String CAS_URL = "https://authn-app.rdd.pc-scol.fr/cas/v1/tickets";

    /**
     * Compte de service LOGIN
     */
    public static final String SVC_ACCOUNT_LOGIN = "svc-api";

    /**
     * Compte de service PASSWORD
     */
    public static final String SVC_ACCOUNT_PASSWORD = "OZipqX2Zx2J3tp88UEqF";


    public static void main(String[] args) {
        System.out.println("Exemple d'appel à une api depuis un client Java");

        //0 configuration du client d'api
        ApiClient apiClient = configureApiClient();;


        //1 appel d'api sans token
        invokeNoBearerToken(apiClient);

        //2 appel d'api avec token
        invokeWithBearerToken(apiClient);
    }

    private static void invokeNoBearerToken(ApiClient apiClient) {
        System.out.println("Appel de l'api StructureApi (No Access Token)...");
        try {
            StructureApi structureApi = new StructureApi(apiClient);
            Structure structure = structureApi.lire("ETAB00");
            System.out.println(structure.toString());
        } catch (ApiException e) {
            System.out.println("Erreur lors de l'invocation de l'api : ");
            System.out.println("Http code:" + e.getCode());
            System.out.println("Http response body:" + e.getResponseBody());
        }
    }

    private static void invokeWithBearerToken(ApiClient apiClient) {
        System.out.println("Appel de l'api StructureApi (Avec Access Token)...");
        try {
            apiClient.setRequestInterceptor(builder -> {
                builder.setHeader("Authorization", "Bearer " + getAccessToken());
            });
            StructureApi structureApi = new StructureApi(apiClient);
            Structure structure = structureApi.lire("ETAB00");
            System.out.println(structure.toString());
        } catch (ApiException e) {
            System.out.println("Erreur lors de l'invocation de l'api : ");
            System.out.println("Http code:" + e.getCode());
            System.out.println("Http response body:" + e.getResponseBody());
        }
    }

    private static ApiClient configureApiClient() {
        ApiClient apiClient = new ApiClient();
        apiClient.setScheme("https");
        apiClient.setHost(API_HOST);
        apiClient.setBasePath("/api/v1/ref");
        return apiClient;
    }

    private static String getAccessToken() {
        System.out.println("Récupération de l'Access Token ...");
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder(URI.create(CAS_URL))
                .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("username=" + SVC_ACCOUNT_LOGIN + "&password=" + SVC_ACCOUNT_PASSWORD + "&token=true"))
                .build();
        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("Access Token : "+response.body());
            return response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
