package pdp.uz.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pdp.uz.entity.Comment;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
}
