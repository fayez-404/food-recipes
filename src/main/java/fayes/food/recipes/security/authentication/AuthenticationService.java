package fayes.food.recipes.security.authentication;

import fayes.food.recipes.security.authentication.request.LoginRequest;
import fayes.food.recipes.security.authentication.request.RegisterRequest;
import fayes.food.recipes.security.authentication.response.AuthenticationResponse;
import fayes.food.recipes.security.jwt.JwtService;
import fayes.food.recipes.user.UserPrincipal;
import fayes.food.recipes.user.UserPrincipalService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private AuthenticationManager authenticationManager ;
    private JwtService jwtService ;
    private UserPrincipalService userService;

    public AuthenticationService(AuthenticationManager authenticationManager,JwtService jwtService,UserPrincipalService userService){
        this.authenticationManager= authenticationManager;
        this.jwtService = jwtService ;
        this.userService = userService ;
    }

    public AuthenticationResponse authenticateUserRequest(LoginRequest request){
        UserPrincipal userPrincipal = (UserPrincipal) authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        ).getPrincipal();
        String accessToken = jwtService.generateAccessToken(userPrincipal.getEmail());
        String refreshToken = jwtService.generateRefreshToken(userPrincipal.getEmail()) ;
        return new AuthenticationResponse(accessToken,refreshToken,"Bearer");
    }

    public void registerUserRequest(RegisterRequest request){
        userService.registerUser(request) ;
    }
}
