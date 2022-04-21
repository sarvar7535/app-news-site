package pdp.uz.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pdp.uz.aop.CheckPermission;
import pdp.uz.model.ApiResponse;
import pdp.uz.model.RoleDto;
import pdp.uz.service.RoleService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PreAuthorize("hasAuthority('ADD_ROLE')")
    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody RoleDto dto) {
        ApiResponse apiResponse = roleService.add(dto);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission(permission = "VIEW_ROLES")
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        ApiResponse apiResponse = roleService.get(id);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission(permission = "VIEW_ROLES")
    @GetMapping
    public ResponseEntity<?> get() {
        ApiResponse apiResponse = roleService.get();
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission(permission = "EDIT_ROLE")
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @Valid @RequestBody RoleDto dto) {
        ApiResponse apiResponse = roleService.edit(id,dto);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission(permission = "DELETE_ROLE")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        ApiResponse apiResponse = roleService.delete(id);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 409).body(apiResponse);
    }
}
