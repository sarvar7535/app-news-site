package pdp.uz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pdp.uz.entity.User;
import pdp.uz.exceptions.ResourceNotFoundException;
import pdp.uz.model.ApiResponse;
import pdp.uz.model.RegisterDto;
import pdp.uz.repostory.RoleRepo;
import pdp.uz.repostory.UserRepo;
import pdp.uz.utils.AppConstants;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ApiResponse register(RegisterDto dto) {
        if (!dto.getPassword().equals(dto.getPrePassword())) {
            return new ApiResponse("The password does not match", false);
        }
        if (userRepo.existsByUsername(dto.getUsername())) {
            return new ApiResponse("User already exists", false);
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setFullName(dto.getFullName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(roleRepo.findByName(AppConstants.USER).orElseThrow(() -> new ResourceNotFoundException("role", "name", AppConstants.USER)));
        user.setEnabled(true);
        userRepo.save(user);
        return new ApiResponse("Created", true, user);
    }

    public UserDetails loadUserByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public static User getCurrentUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

