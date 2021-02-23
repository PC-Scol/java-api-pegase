package fr.pcscol.codegen.feign;

import fr.pcscol.codegen.feign.ref.api.StructureApi;
import fr.pcscol.codegen.feign.ref.invoker.ApiClient;
import fr.pcscol.codegen.feign.ref.invoker.auth.PcscolClientCredentialsGrant;
import fr.pcscol.codegen.feign.ref.model.Structure;

public class JavaFeignClientApiInvocation {

    /**
     * API HOST
     */
    public static final String API_BASE_PATH = "https://ref.rdd.pc-scol.fr/api/v1/ref";

    /**
     * CAS HOST URL
     */
    public static final String CAS_HOST = "https://authn-app.rdd.pc-scol.fr";

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
        ApiClient apiClient = configureApiClient();

        //1 appel d'api avec token récupéré implicitement
        invokeWithRefreshBearerToken(apiClient);
    }

    private static void invokeWithRefreshBearerToken(ApiClient apiClient) {
        //1er appel cherche le token
        System.out.println("Appel de l'api StructureApi...");
        StructureApi structureApi = apiClient.buildClient(StructureApi.class);
        Structure structure = structureApi.lire("ETAB00");
        System.out.println(structure.toString());

        //2eme appel utilise le même token
        System.out.println("Appel de l'api StructureApi...");
        structureApi.lire("ETAB00");
        System.out.println(structure.toString());
    }

    private static ApiClient configureApiClient() {

        PcscolClientCredentialsGrant oauth = new PcscolClientCredentialsGrant(CAS_HOST, SVC_ACCOUNT_LOGIN, SVC_ACCOUNT_PASSWORD);
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(API_BASE_PATH);
        apiClient.addAuthorization("idTokenAuth", oauth);
        return apiClient;
    }
}
