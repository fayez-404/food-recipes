package fayes.food.recipes.security.authentication.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank
    @Size(min = 8, max = 30, message = "Username must be between 8 and 30 characters")
    @Pattern(regexp = "^\\S+$", message = "Spaces are not allowed")
    @Schema(example = "ilovebread",
            description = "Username must be between 8 and 30 characters , and spaces are not allowed"
    )
    private String username ;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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
        return "RegisterRequest{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
