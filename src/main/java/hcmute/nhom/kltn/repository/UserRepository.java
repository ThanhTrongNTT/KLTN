package hcmute.nhom.kltn.repository;

import java.util.Optional;
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
public interface UserRepository extends AbstractRepository<User, Long> {
    @Query(value = "SELECT * FROM T_USER WHERE email = :email", nativeQuery = true)
    Optional<User> findByEmail(String email);
}
