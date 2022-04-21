package pdp.uz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pdp.uz.entity.enums.Permission;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {

    @NotBlank
    private String name;

    private String description;

    @NotEmpty
    private List<Permission> permissions;
}
