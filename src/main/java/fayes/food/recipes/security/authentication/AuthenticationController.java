package fayes.food.recipes.security.authentication;

import fayes.food.recipes.security.authentication.request.LoginRequest;
import fayes.food.recipes.security.authentication.request.RefreshTokenRequest;
import fayes.food.recipes.security.authentication.request.RegisterRequest;
import fayes.food.recipes.security.authentication.response.AuthenticationResponse;
import fayes.food.recipes.user.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

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
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "user authenticated successfully"),
            @ApiResponse(responseCode = "401", description = "user unauthenticated Bad Credentials"),
            @ApiResponse(responseCode = "400", description = "user unauthenticated Bad Request")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid LoginRequest request){
        AuthenticationResponse response = authenticationService.authenticateUserRequest(request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "register user"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "user created successfully"),
            @ApiResponse(responseCode = "409", description = "email already exist ,conflict"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterRequest request){
        authenticationService.registerUserRequest(request);
        return ResponseEntity.ok().build() ;
    }

    @PostMapping("/refresh-token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "access token refreshed successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid refresh token"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<AuthenticationResponse> refreshAccessToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest ){
        String accessToken = authenticationService.refreshAccessToken(refreshTokenRequest);
        AuthenticationResponse response = new AuthenticationResponse(accessToken,refreshTokenRequest.getRefreshToken(),"Bearer");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<OidcUser> me(@AuthenticationPrincipal OidcUser oidcUser){
        return ResponseEntity.ok(oidcUser) ;
    }

}
