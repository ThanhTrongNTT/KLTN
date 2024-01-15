package hcmute.nhom.kltn.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import hcmute.nhom.kltn.model.Post;

/**
 * Class PostRepository.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
public interface PostRepository extends AbstractRepository<Post, String> {
    @Query(value = "SELECT * FROM T_POST p INNER JOIN T_USER u ON p.user_id = u.id WHERE u.user_name = :userName "
            + "AND p.removal_flag = 0 ORDER BY p.created_date DESC",
            nativeQuery = true)
    List<Post> getPostsByUser(String userName);

    @Query(value =
            "SELECT * FROM T_POST p INNER JOIN T_USER u ON p.user_id = u.id "
            + "WHERE u.user_name = :userName AND p.post_id = :id AND removal_flag = 0",
            nativeQuery = true)
    Optional<Post> getPostsByUserAndId(String id, String userName);

    @Query(value = "SELECT * FROM T_POST WHERE LOWER(content) LIKE LOWER(:content) AND removal_flag = 0",
            nativeQuery = true)
    List<Post> searchPost(String content);
}
