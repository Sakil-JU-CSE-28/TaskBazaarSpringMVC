package dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.Transient;
import javax.validation.constraints.*;

@Data
@Getter
@Setter
@Component
@NoArgsConstructor
public class UserDto {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Please confirm your password")
    private String confirmPassword;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email")
    private String email;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotNull(message = "Role is required")
    private String role;

    @Transient
    @AssertTrue(message = "Passwords must match")
    public boolean isPasswordsMatch() {
        return password != null && password.equals(confirmPassword);
    }
}
