package pdp.uz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import pdp.uz.entity.Comment;
import pdp.uz.entity.Post;
import pdp.uz.entity.User;
import pdp.uz.exceptions.ForbiddenException;
import pdp.uz.exceptions.ResourceNotFoundException;
import pdp.uz.model.ApiResponse;
import pdp.uz.model.CommentDto;
import pdp.uz.repostory.CommentRepo;
import pdp.uz.repostory.PostRepo;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepo commentRepo;

    private final PostRepo postRepo;


    public ApiResponse get() {
        return new ApiResponse("OK", true, commentRepo.findAll());
    }


    public ApiResponse get(Long id) {
        return new ApiResponse("OK", true, commentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("comment", "id", id)));
    }


    public ApiResponse add(CommentDto dto) {
        Optional<Post> optionalPost = postRepo.findById(dto.getPostId());
        if (!optionalPost.isPresent()) {
            throw new ResourceNotFoundException("post", "id", dto.getPostId());
        }
        Post post = optionalPost.get();
        Comment comment = new Comment();
        comment.setText(dto.getText());
        comment.setPost(post);
        commentRepo.save(comment);
        return new ApiResponse("Created", true);
    }


    public ApiResponse edit(Long id, CommentDto dto) {
        Optional<Comment> optionalComment = commentRepo.findById(id);
        if (!optionalComment.isPresent()) {
            throw new ResourceNotFoundException("comment", "id", id);
        }
        Comment comment = optionalComment.get();
        if (!AuthService.getCurrentUser().getUsername().equals(comment.getCreatedBy().getUsername())) {
            throw new ForbiddenException("Access", "You do not have access to edit this comment");
        }
        Optional<Post> optionalPost = postRepo.findById(dto.getPostId());
        if (!optionalPost.isPresent()) {
            throw new ResourceNotFoundException("post", "id", dto.getPostId());
        }
        Post post = optionalPost.get();
        comment.setText(dto.getText());
        comment.setPost(post);
        commentRepo.save(comment);
        return new ApiResponse("Edited", true);
    }


    public ApiResponse delete(Long id) {
        Optional<Comment> optionalComment = commentRepo.findById(id);
        if (!optionalComment.isPresent())
            throw new ResourceNotFoundException("comment", "id", id);
        Comment comment = optionalComment.get();
        if (!AuthService.getCurrentUser().getUsername().equals(comment.getCreatedBy().getUsername())
                && !AuthService.getCurrentUser()
                .getAuthorities().contains(new SimpleGrantedAuthority("DELETE_COMMENT"))) {
            throw new ForbiddenException("Access", "You do not have access to edit this comment");
        }
        commentRepo.deleteById(id);
        return new ApiResponse("Deleted", true);
    }
}
