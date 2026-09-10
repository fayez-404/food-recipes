package fayes.food.recipes.security.authentication;

import fayes.food.recipes.security.authentication.request.LoginRequest;
import fayes.food.recipes.security.authentication.request.RegisterRequest;
import fayes.food.recipes.security.authentication.response.AuthenticationResponse;
import fayes.food.recipes.user.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name= "Authectication"
        ,description = "user authentication and registeration and jwt management"
)
@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

    private AuthenticationService authenticationService;
    AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService ;
    }

    @Operation(
            summary = "authenticate user"
    )
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid LoginRequest request){
        AuthenticationResponse response = authenticationService.authenticateUserRequest(request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "register user"
    )
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterRequest request){
        authenticationService.registerUserRequest(request);
        return ResponseEntity.ok().build() ;
    }

}
