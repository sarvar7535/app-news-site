package pdp.uz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pdp.uz.entity.User;
import pdp.uz.exceptions.ResourceNotFoundException;
import pdp.uz.model.ApiResponse;
import pdp.uz.model.UserDto;
import pdp.uz.repostory.RoleRepo;
import pdp.uz.repostory.UserRepo;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepo roleRepo;


    public ApiResponse addUser(UserDto dto) {
        if (userRepo.existsByUsername(dto.getUsername())) {
            return new ApiResponse("User has already existed", false);
        }
        User user = new User(
                dto.getFullName(),
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()),
                roleRepo.findById(dto.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("role", "id", dto.getRoleId())),
                true
        );
        userRepo.save(user);
        return new ApiResponse("Created", true);
    }

    public ApiResponse get(Long id) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (!optionalUser.isPresent()) {
            throw new ResourceNotFoundException("user", "id", id);
        }
        return new ApiResponse("OK", true, optionalUser.get());
    }

    public ApiResponse get() {
        return new ApiResponse("OK", true, userRepo.findAll());
    }

    public ApiResponse edit(Long id, UserDto dto) {
        if (userRepo.existsByUsernameAndIdNot(dto.getUsername(), id)) {
            return new ApiResponse("Username has already used", false);
        }
        Optional<User> optionalUser = userRepo.findById(id);
        if (!optionalUser.isPresent()) {
            throw new ResourceNotFoundException("user", "id", id);
        }
        User user = optionalUser.get();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setFullName(dto.getFullName());
        user.setRole(roleRepo.findById(dto.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("role", "id", dto.getRoleId())));
        userRepo.save(user);
        return new ApiResponse("Updated", true);
    }

    public ApiResponse delete(Long id) {
        if (!userRepo.existsById(id)) {
            throw new ResourceNotFoundException("user", "id", id);
        }
        userRepo.deleteById(id);
        return new ApiResponse("Deleted", true);
    }
}
