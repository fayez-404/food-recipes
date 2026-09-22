package fayes.food.recipes.user.oidc;

import fayes.food.recipes.security.authentication.response.AuthenticationResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
public class OidcSuccessHandler implements AuthenticationSuccessHandler {

    private OidcService oidcService ;
    private ObjectMapper objectMapper ;
    private Logger logger = LoggerFactory.getLogger(OidcSuccessHandler.class.getName());

    OidcSuccessHandler(OidcService oidcService,ObjectMapper objectMapper){
        this.oidcService = oidcService ;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
        AuthenticationResponse authenticationResponse = oidcService.authenticate(oidcUser);
        response.setStatus(200);
        response.getWriter().write(objectMapper.writeValueAsString(authenticationResponse));
    }
}
