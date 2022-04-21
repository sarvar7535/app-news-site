package pdp.uz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pdp.uz.entity.Role;
import pdp.uz.exceptions.ResourceNotFoundException;
import pdp.uz.model.ApiResponse;
import pdp.uz.model.RoleDto;
import pdp.uz.repostory.RoleRepo;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepo roleRepo;

    public ApiResponse add(RoleDto dto) {
        if (roleRepo.existsByName(dto.getName())) {
            return new ApiResponse("Role already exists", false);
        }
        Role role = new Role(
                dto.getName(),
                dto.getPermissions(),
                dto.getDescription());
        roleRepo.save(role);
        return new ApiResponse("Created", true, role);
    }

    public ApiResponse get(Long id) {
        Optional<Role> optionalRole = roleRepo.findById(id);
        return new ApiResponse("OK", true, optionalRole.orElseThrow(() -> new ResourceNotFoundException("role", "id", id)));
    }

    public ApiResponse get() {
        return new ApiResponse("OK", true, roleRepo.findAll());
    }

    public ApiResponse edit(Long id, RoleDto dto) {
        if (roleRepo.existsByNameAndIdNot(dto.getName(),id)) {
            return new ApiResponse("Role has already existed", false);
        }
        Optional<Role> optionalRole = roleRepo.findById(id);
        if (!optionalRole.isPresent()) {
            throw new ResourceNotFoundException("role", "id", id);
        }
        Role role = optionalRole.get();
        role.setName(dto.getName());
        role.setDescription(dto.getDescription());
        role.setPermissions(dto.getPermissions());
        roleRepo.save(role);
        return new ApiResponse("Updated", true);
    }

    public ApiResponse delete(Long id) {
        if (!roleRepo.existsById(id)) {
            throw new ResourceNotFoundException("role", "id", id);
        }
        roleRepo.deleteById(id);
        return new ApiResponse("Deleted", true);
    }
}
