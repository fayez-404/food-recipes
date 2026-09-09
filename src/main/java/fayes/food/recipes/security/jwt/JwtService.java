package fayes.food.recipes.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private final PrivateKey privateKey ;
    private final PublicKey publicKey;
    @Value("${application.security.jwt.access_token.expiration}")
    private Long accessTokenExpiration;
    @Value("${application.security.jwt.refresh_token.expiration}")
    private Long refreshTokenExpiration;
    JwtService() throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.privateKey = KeyUtils.loadPrivateKey("keys/local/private.key");
        this.publicKey = KeyUtils.loadPublicKey("keys/local/public.key");
    }

    public Long getAccessTokenExpiration() {
        return accessTokenExpiration;
    }

    public Long getRefreshTokenExpiration() {
        return refreshTokenExpiration;
    }

    public String generateAccessToken(String email) {
        Map<String,Object> claims = new HashMap<>();
        claims.put("token_type","ACCESS_TOKEN");
        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .expiration(new Date(System.currentTimeMillis()+ accessTokenExpiration))
                .issuedAt(new Date())
                .signWith(privateKey)
                .compact();
    }
    public boolean validateToken(String token,String email){
        Claims claims = extractClaims(token);
        return "ACCESS_TOKEN".equals(claims.get("token_type",String.class))
                && claims.getSubject().equals(email)
                && claims.getExpiration().after(new Date());
    }
    public Claims extractClaims(String token){
        return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public String extractEmail(String token){
        return extractClaims(token).getSubject();
    }
    public String generateRefreshToken(String email){
        Map<String,Object> claims = new HashMap<>();
        claims.put("token_type","REFRESH_TOKEN");
        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .expiration(new Date(System.currentTimeMillis()+ refreshTokenExpiration))
                .issuedAt(new Date())
                .signWith(privateKey)
                .compact();
    }
    public String refreshAccessToken(String token){
        Claims claims = extractClaims(token);
        if (!"REFRESH_TOKEN".equals(claims.get("token_type",String.class))){
            throw new RuntimeException("Invalid refresh token");
        }
        if (!claims.getExpiration().after(new Date())){
            throw new RuntimeException("Invalid refresh token");
        }
        String email = claims.getSubject();
        return generateAccessToken(email);
    }

}
