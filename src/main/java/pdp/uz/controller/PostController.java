package pdp.uz.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pdp.uz.aop.CheckPermission;
import pdp.uz.model.ApiResponse;
import pdp.uz.model.PostDto;
import pdp.uz.service.PostService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<?> get() {
        ApiResponse apiResponse = postService.get();
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        ApiResponse apiResponse = postService.get(id);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission(permission = "ADD_POST")
    @PostMapping
    public ResponseEntity<?> add(@RequestBody PostDto dto, HttpServletRequest request) {
        ApiResponse apiResponse = postService.add(dto, request);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission(permission = "EDIT_POST")
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody PostDto dto) {
        ApiResponse apiResponse = postService.edit(id,dto);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission(permission = "DELETE_POST")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        ApiResponse apiResponse = postService.delete(id);
        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 409).body(apiResponse);
    }
}
