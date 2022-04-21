package pdp.uz.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pdp.uz.entity.enums.Permission;
import pdp.uz.entity.template.AbstractEntity;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Role extends AbstractEntity {


    @Column(nullable = false, unique = true)
    private String name;

    @ElementCollection
    @Enumerated(value = EnumType.STRING)
    private List<Permission> permissions;

    @Column(columnDefinition = "text")
    private String description;
}
