package pdp.uz.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pdp.uz.entity.Post;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
}
