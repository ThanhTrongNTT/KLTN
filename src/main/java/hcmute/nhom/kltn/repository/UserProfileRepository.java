package hcmute.nhom.kltn.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import hcmute.nhom.kltn.model.UserProfile;

/**
 * Class UserProfileRepository.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
public interface UserProfileRepository extends AbstractRepository<UserProfile, String> {
    @Query(value = "SELECT * FROM t_user_profile up INNER JOIN T_USER u ON up.id = u.profile_id "
            + "WHERE u.email = :email AND removal_flag = 0", nativeQuery = true)
    UserProfile findByEmailUser(String email);
}
