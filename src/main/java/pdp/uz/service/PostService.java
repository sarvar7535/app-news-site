package pdp.uz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pdp.uz.entity.Post;
import pdp.uz.exceptions.ResourceNotFoundException;
import pdp.uz.model.ApiResponse;
import pdp.uz.model.PostDto;
import pdp.uz.repostory.PostRepo;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepo postRepo;

    public ApiResponse get() {
        return new ApiResponse("OK", true, postRepo.findAll());
    }

    public ApiResponse get(Long id) {
        return new ApiResponse("OK", true, postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id)));
    }

    public ApiResponse add(PostDto dto, HttpServletRequest request) {
        Post post = new Post();
        post.setText(dto.getText());
        post.setTitle(dto.getTitle());
        post.setUrl(request.getRequestURL().toString() + "/" + dto.getTitle().replace(" ", "-"));
        postRepo.save(post);
        return new ApiResponse("Created", true);
    }


    public ApiResponse edit(Long id, PostDto dto) {
        Optional<Post> optionalPost = postRepo.findById(id);
        if (!optionalPost.isPresent()) {
            throw new ResourceNotFoundException("post", "id", id);
        }
        Post post = optionalPost.get();
        String oldUrl = post.getUrl();
        post.setText(dto.getText());
        post.setTitle(dto.getTitle());
        post.setUrl(oldUrl.substring(0, oldUrl.lastIndexOf("/")) + dto.getTitle().replace(" ", "-"));
        postRepo.save(post);
        return new ApiResponse("Edited", true);
    }

    public ApiResponse delete(Long id) {
        if (!postRepo.existsById(id)) {
            throw new ResourceNotFoundException("post", "id", id);
        }
        postRepo.deleteById(id);
        return new ApiResponse("Deleted", true);
    }
}
