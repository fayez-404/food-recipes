package fayes.food.recipes.security.authentication.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class RefreshTokenRequest {

    @Schema(example = "mEo7A800KCKqbrBNynlz4hXIf7c9zkaCbunOC25ZYb7NYpemOsJzaA")
    @NotBlank
    private String refreshToken ;

    public RefreshTokenRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "RefreshTokenRequest{" +
                "refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
