package hcmute.nhom.kltn.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import hcmute.nhom.kltn.model.User;

/**
 * Class UserRepository.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Repository
public interface UserRepository extends AbstractRepository<User, UUID> {
    @Query(value = "SELECT * FROM T_USER WHERE email = :email", nativeQuery = true)
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM T_USER WHERE user_name = :userName", nativeQuery = true)
    Optional<User> findByUserName(String userName);
}
