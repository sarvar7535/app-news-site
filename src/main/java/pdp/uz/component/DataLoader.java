package pdp.uz.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pdp.uz.entity.Role;
import pdp.uz.entity.User;
import pdp.uz.entity.enums.Permission;
import pdp.uz.repostory.RoleRepo;
import pdp.uz.repostory.UserRepo;
import pdp.uz.utils.AppConstants;

import java.util.Arrays;

import static pdp.uz.entity.enums.Permission.*;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepo userRepo;

    private final RoleRepo roleRepo;

    private final PasswordEncoder passwordEncoder;

    @Value("${spring.sql.init.mode}")
    private String initMode;

    @Override
    public void run(String... args) throws Exception {
        if (initMode.equals("always")) {
            Permission[] permissions = Permission.values();
            Role admin = roleRepo.save(new Role(
                    AppConstants.ADMIN,
                    Arrays.asList(permissions),
                    "System owner"
            ));

            Role user = roleRepo.save(new Role(
                    AppConstants.USER,
                    Arrays.asList(ADD_COMMENT, EDIT_COMMENT, DELETE_MY_COMMENT),
                    "Simple user"
            ));

            userRepo.save(new User(
                    "Admin",
                    "admin",
                    passwordEncoder.encode("admin123"),
                    admin,
                    true
            ));

            userRepo.save(new User(
                    "User",
                    "user",
                    passwordEncoder.encode("user123"),
                    user,
                    true
            ));
        }
    }
}
