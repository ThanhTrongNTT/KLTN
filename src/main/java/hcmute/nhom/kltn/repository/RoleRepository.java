package hcmute.nhom.kltn.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import hcmute.nhom.kltn.model.Role;

/**
 * Class RoleRepository.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Repository
public interface RoleRepository extends AbstractRepository<Role, String> {
    @Query(value = "SELECT * FROM T_ROLE r WHERE r.ROLE_NAME = :name AND removal_flag = 0", nativeQuery = true)
    Optional<Role> findByName(String name);
}
