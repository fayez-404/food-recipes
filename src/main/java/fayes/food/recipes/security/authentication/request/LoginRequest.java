package fayes.food.recipes.security.authentication.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class LoginRequest {

    @NotBlank
    @Email
    @Schema(example = "food.recipes@example.com")
    private String email ;
    @NotBlank
    @Size(min = 8, max = 30, message = "Password must be between 8 and 30 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"
            ,message = "Password must contain at least one digit, one lowercase, one uppercase, one special character, and no spaces.")
    @Schema(example = "P@ssord123"
            ,description = "Password must contain at least one digit" +
            ", one lowercase, one uppercase" +
            ", one special character" +
            ", no spaces" +
            ",and must be between 8 and 30 characters")
    private String password ;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
