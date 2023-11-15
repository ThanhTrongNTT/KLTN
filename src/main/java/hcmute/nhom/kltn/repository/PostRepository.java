package hcmute.nhom.kltn.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import hcmute.nhom.kltn.model.Post;

/**
 * Class PostRepository.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
public interface PostRepository extends AbstractRepository<Post, UUID> {
    @Query(value = "SELECT * FROM T_POST p INNER JOIN T_USER u ON p.user_id = u.user_id WHERE u.user_name = :userName"
            , nativeQuery = true)
    List<Post> getPostsByUser(String userName);

    @Query(value =
            "SELECT * FROM T_POST p INNER JOIN T_USER u ON p.user_id = u.userId "
            + "WHERE u.user_name = :userName AND p.post_id = :id"
            , nativeQuery = true)
    Optional<Post> getPostsByUserAndId(UUID id, String userName);
}
