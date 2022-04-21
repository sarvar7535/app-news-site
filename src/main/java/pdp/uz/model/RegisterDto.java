package pdp.uz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    @NotNull(message = "Full name must not be empty")
    private String fullName;

    @NotNull(message = "Username must not be empty")
    private String username;

    @NotNull(message = "Password must not be empty")
    private String password;

    @NotNull(message = "pre password must not be empty")
    private String prePassword;
}
