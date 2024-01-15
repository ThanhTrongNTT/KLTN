package hcmute.nhom.kltn.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
public interface UserRepository extends AbstractRepository<User, String> {
    @Query(value = "SELECT * FROM T_USER WHERE email = :email AND removal_flag = 0", nativeQuery = true)
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM T_USER WHERE user_name = :userName AND removal_flag = 0", nativeQuery = true)
    Optional<User> findByUserName(String userName);

    @Query(value = "SELECT * FROM T_USER WHERE LOWER(user_name) LIKE LOWER(:searchParam) AND removal_flag = 0",
            nativeQuery = true)
    List<User> findByUserNameContaining(@Param("searchParam") String searchParam);

    @Query(value = "SELECT USER_NAME FROM T_USER WHERE removal_flag = 0", nativeQuery = true)
    List<String> getAllUserName();
}
