package fayes.food.recipes.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    @Autowired
    private JwtService jwtService ;

    @Test
    public void generateAccessToken_ValidTokenTest(){
        String token = jwtService.generateAccessToken("example@email.com");
        Assertions.assertTrue(jwtService.validateToken(token,"example@email.com"));
    }

    @Test
    public void validateAccessToken_expiredTokenTest() {
        Long original = jwtService.getAccessTokenExpiration();
        try {
            ReflectionTestUtils.setField(jwtService, "accessTokenExpiration", -1000L);
            String token = jwtService.generateAccessToken("example@email.com");
            Assertions.assertThrows(
                    ExpiredJwtException.class,
                    ()->jwtService.validateToken(token, "example@email.com")
            );
        }finally {
            ReflectionTestUtils.setField(jwtService,"accessTokenExpiration", original);
        }
    }

    @Test
    public void validateAccessToken_differentUserTest(){
        String token = jwtService.generateAccessToken("example@email.com");
        Assertions.assertFalse(jwtService.validateToken(token,"new.example@email.com"));
    }

    @Test
    public void refreshAccessToken_validRefresh(){
        String refreshToken = jwtService.generateRefreshToken("example@email.com");
        Assertions.assertTrue(
                jwtService.validateToken(jwtService.refreshAccessToken(refreshToken),"example@email.com")
        );
    }
    @Test
    public void refreshAccessToken_expiredRefreshToken(){
        Long original = jwtService.getRefreshTokenExpiration();
        try {
            ReflectionTestUtils.setField(jwtService, "refreshTokenExpiration", -1000L);
            String refreshToken = jwtService.generateRefreshToken("example@email.com");
            Assertions.assertThrows(
                    ExpiredJwtException.class,
                    ()->jwtService.validateToken(jwtService.refreshAccessToken(refreshToken), "example@email.com")
            );
        }finally {
            ReflectionTestUtils.setField(jwtService,"refreshTokenExpiration", original);
        }
    }
    @Test
    public void refreshAccessToken_wrongTokenType(){
        String token = jwtService.generateAccessToken("example@email.com");
        Assertions.assertThrows(RuntimeException.class,() -> jwtService.refreshAccessToken(token));
    }
}
