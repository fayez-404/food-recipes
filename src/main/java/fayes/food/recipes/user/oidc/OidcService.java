package fayes.food.recipes.user.oidc;

import fayes.food.recipes.security.authentication.response.AuthenticationResponse;
import fayes.food.recipes.security.jwt.JwtService;
import fayes.food.recipes.user.UserPrincipal;
import fayes.food.recipes.user.UserRepository;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OidcService  {

    private OauthUserRepository oauthUserRepository ;
    private JwtService jwtService ;
    private UserRepository userRepository ;


    OidcService(OauthUserRepository oauthUserRepository,JwtService jwtService ,UserRepository userRepository ){
        this.oauthUserRepository = oauthUserRepository ;
        this.jwtService=jwtService;
        this.userRepository = userRepository ;
    }

    public AuthenticationResponse authenticate(OidcUser oidcUser){
        String provider = "google";
        String providerUserId = oidcUser.getSubject() ;
        Optional<OauthUser> queriedOauthUser = oauthUserRepository.findByProviderAndProviderUserId(provider,providerUserId);
        UserPrincipal user ;
        if (queriedOauthUser.isPresent()){
            user = queriedOauthUser.get().getUserPrincipal();
        }else {
            if(userRepository.existsByEmail(oidcUser.getEmail())){
                user = userRepository.findByEmail(oidcUser.getEmail()).get() ;
            }
            else{
                user = createUser(oidcUser);
                userRepository.save(user);
            }
            OauthUser oauthUser = new OauthUser(provider,providerUserId,user);
            oauthUserRepository.save(oauthUser);
        }
        return new AuthenticationResponse(
                jwtService.generateAccessToken(user.getEmail())
                ,jwtService.generateRefreshToken(user.getEmail())
                ,"Bearer"
        );
    }

    private UserPrincipal createUser(OidcUser oidcUser) {
        return new UserPrincipal(oidcUser.getPreferredUsername(),oidcUser.getEmail(),null);
    }

}
