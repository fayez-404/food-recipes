package fayes.food.recipes.security.authentication;

import fayes.food.recipes.security.authentication.request.LoginRequest;
import fayes.food.recipes.security.authentication.request.RefreshTokenRequest;
import fayes.food.recipes.security.authentication.request.RegisterRequest;
import fayes.food.recipes.security.authentication.response.AuthenticationResponse;
import fayes.food.recipes.security.jwt.JwtService;
import fayes.food.recipes.user.UserPrincipalService;
import fayes.food.recipes.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper ;

    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private JwtService jwtService ;

    @Autowired
    private UserPrincipalService userPrincipalService;

    @BeforeEach
    public void setUpTest(){
        userRepository.deleteAll();
        userPrincipalService.registerUser(
                new RegisterRequest("banana","example@email.com","P@ssword123")
        );
    }

    @Test
    public void loginRequest_validRequestTest() throws Exception {
        LoginRequest request = new LoginRequest("example@email.com","P@ssword123");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.access_token").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.refresh_token").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token_type").value("Bearer"));
    }

    @Test
    public void loginRequest_badLoginRequestTest() throws Exception {
        LoginRequest request = new LoginRequest("exampleemail.com","P@ssword123");
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void loginRequest_badCredentialsEmailNotFoundTest() throws Exception {
        LoginRequest request = new LoginRequest("example15@email.com", "P@ssword123");
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().is(401));
    }
    @Test
    public void loginRequest_badCredentialsWrongPasswordTest() throws Exception {
        LoginRequest request = new LoginRequest("example@email.com", "P@sswosrd123");
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().is(401));
    }

    @Test
    public void registerRequest_validRegisterationRequestTest() throws Exception {
        RegisterRequest request = new RegisterRequest("bananaJoe","big.mango@email.com","P@ssword123");
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void registerRequest_emailAlreadyExistRequestTest() throws Exception {
        RegisterRequest request = new RegisterRequest("bananaJoe","example@email.com","P@ssword123");
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }
    @Test
    public void registerRequest_badRequestTest() throws Exception {
        RegisterRequest request = new RegisterRequest("baa","exampleemail.com","Pssword123");
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void refreshAccessToken_validRefreshToken() throws Exception{
        LoginRequest loginRequest = new LoginRequest("example@email.com","P@ssword123");
        AuthenticationResponse response = objectMapper.readValue(mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest))
        ).andReturn().getResponse().getContentAsString(), AuthenticationResponse.class);
        RefreshTokenRequest request = new RefreshTokenRequest(response.getRefreshToken());
        System.out.println("refresh token :"+request.getRefreshToken());
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/refresh-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.access_token").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.refresh_token").value(response.getRefreshToken()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.token_type").value("Bearer"));
    }
    @Test
    public void refreshAccessToken_badRefreshToken() throws Exception{
        RefreshTokenRequest request = new RefreshTokenRequest("lkasydibutuo2327985jkb ");
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/refresh-token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
    @Test
    public void refreshAccessToken_expiredRefreshToken() throws Exception {
        Long original = jwtService.getRefreshTokenExpiration() ;
        try {
            ReflectionTestUtils.setField(jwtService,"refreshTokenExpiration",-100L);
            LoginRequest loginRequest = new LoginRequest("example@email.com", "P@ssword123");
            AuthenticationResponse response = objectMapper.readValue(mockMvc.perform(
                    MockMvcRequestBuilders.post("/api/v1/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(loginRequest))
            ).andReturn().getResponse().getContentAsString(), AuthenticationResponse.class);
            RefreshTokenRequest request = new RefreshTokenRequest(response.getRefreshToken());
            mockMvc.perform(
                            MockMvcRequestBuilders.post("/api/v1/refresh-token")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request))
                    )
                    .andExpect(MockMvcResultMatchers.status().isUnauthorized());

        }finally {
            ReflectionTestUtils.setField(jwtService,"refreshTokenExpiration",original);
        }
    }
}

//todos
//8- valid refresh token
//9- bad refresh token
//10- expired refresh token
