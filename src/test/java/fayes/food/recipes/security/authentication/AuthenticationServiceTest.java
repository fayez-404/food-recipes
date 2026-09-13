package fayes.food.recipes.security.authentication;


import fayes.food.recipes.exception.EmailAlreadyExistsException;
import fayes.food.recipes.security.authentication.request.LoginRequest;
import fayes.food.recipes.security.authentication.request.RefreshTokenRequest;
import fayes.food.recipes.security.authentication.request.RegisterRequest;
import fayes.food.recipes.security.authentication.response.AuthenticationResponse;
import fayes.food.recipes.security.jwt.JwtService;
import fayes.food.recipes.user.UserPrincipal;
import fayes.food.recipes.user.UserPrincipalService;
import fayes.food.recipes.user.UserRepository;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;


@SpringBootTest
@ActiveProfiles("test")
public class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserPrincipalService userPrincipalService ;
    @Autowired
    private UserRepository userRepository ;
    @Autowired
    private JwtService jwtService ;

    @BeforeEach
    public void addUser(){
        userRepository.deleteAll();
        userPrincipalService.registerUser(new RegisterRequest(
                "banana",
                "example@email.com",
                "P@ssword123"
                )
        );
    }


    // 1- valid authentication request
    @Test
    public void authenticateUserRequest_validRequestTest(){
        LoginRequest request = new LoginRequest("example@email.com","P@ssword123");

        AuthenticationResponse response = authenticationService.authenticateUserRequest(request);
        Assertions.assertTrue(response.getAccessToken() != null
                && response.getRefreshToken() != null
                && response.getTokenType().equals("Bearer"));
    }

    // 2- not valid authentication request user doesn't exist
    @Test
    public void authenticateUserRequest_notValidRequestUserNotFoundTest(){
        LoginRequest request = new LoginRequest("example15@email.com","P@ssword123");
        Assertions.assertThrows(BadCredentialsException.class,()->authenticationService.authenticateUserRequest(request));
    }

    // 3- not valid authentication request password is wrong
    @Test
    public void authenticateUserRequest_notValidRequestWrongPasswordTest(){
        LoginRequest request = new LoginRequest("example@email.com","P@sssword123");
        Assertions.assertThrows(BadCredentialsException.class,()->authenticationService.authenticateUserRequest(request));
    }

    // 5- valid register request
    @Test
    public void registerUserRequest_validRegisterRequestTest(){
        RegisterRequest request = new RegisterRequest("mango","big.mango@email.com","NewP@ssword123");
        userPrincipalService.registerUser(request);
        UserPrincipal userPrincipal = (UserPrincipal) userPrincipalService.loadUserByUsername("big.mango@email.com");
        Assertions.assertTrue(userPrincipal.getEmail().equals("big.mango@email.com")&&
                userPrincipal.getUsername().equals("mango"));
    }

    @Test
    public void registerUserRequest_notValidregisterRequestEmailAlreadyExistTest(){
        RegisterRequest request = new RegisterRequest("mango","example@email.com","NewP@ssword123");
        Assertions.assertThrows(EmailAlreadyExistsException.class,()->userPrincipalService.registerUser(request));
    }

    @Test
    public void refreshAccessToken_validRefreshTokenTest(){
        AuthenticationResponse response = authenticationService.authenticateUserRequest(
                new LoginRequest(
                        "example@email.com",
                        "P@ssword123"
                )
        );
        String token = authenticationService.refreshAccessToken(
                new RefreshTokenRequest(
                        response.getRefreshToken()
                )
        );
        Assertions.assertNotNull(token);
    }

    @Test
    public void refreshAccessToken_expiredRefreshTokenTest(){
        long originalValue = jwtService.getRefreshTokenExpiration();
        try {
            ReflectionTestUtils.setField(jwtService,"refreshTokenExpiration",-100L);
            AuthenticationResponse response = authenticationService.authenticateUserRequest(
                    new LoginRequest(
                            "example@email.com",
                            "P@ssword123"
                    )
            );
            Assertions.assertThrows(JwtException.class,()->
                    authenticationService.refreshAccessToken(
                        new RefreshTokenRequest(
                                response.getRefreshToken()
                        )
                    )
            );
        }finally {
            ReflectionTestUtils.setField(jwtService,"refreshTokenExpiration",originalValue);
        }
    }

    @Test
    public void refreshAccessToken_badRefreshTokenTest(){
        Assertions.assertThrows(JwtException.class,
                ()->authenticationService.refreshAccessToken(
                    new RefreshTokenRequest(
                            "kasgdiotguiobkjwgaeiotbas@#@#987as"
                    )
                )
        );
    }

}