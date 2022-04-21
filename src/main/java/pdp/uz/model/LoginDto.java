package pdp.uz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @NotNull(message = "Username must not be empty")
    private String username;

    @NotNull(message = "Password must not be empty")
    private String password;
}
