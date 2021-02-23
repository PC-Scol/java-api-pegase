package fr.pcscol.codegen.feign.ref.invoker.auth;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface CasAPI {

    @RequestLine("POST /cas/v1/tickets")
    @Headers({
            "Content-Type: application/x-www-form-urlencoded"
    })
    String getAccessToken(@Param("username") String username, @Param("password") String password, @Param("token") Boolean token);

}
