package fayes.food.recipes.security.jwt;

import fayes.food.recipes.user.UserPrincipal;
import fayes.food.recipes.user.UserPrincipalService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.IOException;

import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class JwtFilterTest {

    @Mock
    private HttpServletRequest request ;

    @Mock
    private HttpServletResponse response ;

    @Mock
    private FilterChain filterChain ;

    @Mock
    private UserPrincipalService userService ;

    @Mock
    private JwtService jwtService ;

    @InjectMocks
    private JwtFilter jwtFilter ;

    @BeforeEach
    public void clearSecurityContext(){
        SecurityContextHolder.clearContext();
    }

    @Test
    public void doFilterInternal_AuthenticationObjectNotNullTest() throws ServletException, IOException {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        "example@email.com",
                        null
                )
        );
        jwtFilter.doFilterInternal(request,response,filterChain);
        verify(filterChain).doFilter(request,response);
        verify(request,never()).getHeader(HttpHeaders.AUTHORIZATION);
    }


    @Test
    public void doFilterInternal_noHeaderTest() throws ServletException, IOException {
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);
        jwtFilter.doFilterInternal(request,response,filterChain);
        verify(jwtService,never()).validateToken(any(),any());
        verify(filterChain).doFilter(request,response);
    }
    @Test
    public void doFilterInternal_badHeaderStartsWithBearerTest() throws ServletException, IOException {
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer eqa57");
        when(jwtService.extractEmail("eqa57")).thenThrow(JwtException.class);
        Assertions.assertThrows(JwtException.class,() -> jwtFilter.doFilterInternal(request,response,filterChain));
        verify(filterChain,never()).doFilter(request,response);
    }
    @Test
    public void doFilterInternal_badHeaderTest() throws ServletException, IOException {
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(" eqa57");
        jwtFilter.doFilterInternal(request,response,filterChain);
        verify(filterChain).doFilter(request,response);
    }
    @Test
    public void doFilterInternal_userNotFoundTest() throws ServletException, IOException {
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer valid_token");
        when(jwtService.extractEmail("valid_token")).thenReturn("example@email.com");
        when(userService.loadUserByUsername("example@email.com")).thenThrow(UsernameNotFoundException.class);
        Assertions.assertThrows(UsernameNotFoundException.class,() -> jwtFilter.doFilterInternal(request,response,filterChain));

    }
    @Test
    public void doFilterInternal_validTokenTest() throws ServletException, IOException {
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer valid_token");
        when(jwtService.extractEmail("valid_token")).thenReturn("example@email.com");
        when(jwtService.validateToken("valid_token","example@email.com")).thenReturn(true);
        when(userService.loadUserByUsername("example@email.com")).thenReturn(
                new UserPrincipal("example","example@email.com","P@ssword123")
        );
        jwtFilter.doFilterInternal(request,response,filterChain);
        verify(filterChain).doFilter(request,response);

    }
}
