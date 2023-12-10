package hcmute.nhom.kltn.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import hcmute.nhom.kltn.model.Comment;

/**
 * Class CommentRepository.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
public interface CommentRepository extends AbstractRepository<Comment, String> {
    @Query(value = "SELECT * FROM T_COMMENT WHERE post_id = :postId AND removal_flag = 0", nativeQuery = true)
    List<Comment> getCommentsByPostId(UUID postId);
}
