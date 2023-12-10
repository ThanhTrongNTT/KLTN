package hcmute.nhom.kltn.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import hcmute.nhom.kltn.dto.AddressDTO;
import hcmute.nhom.kltn.model.Address;

/**
 * Class AddressRepository.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
public interface AddressRepository extends AbstractRepository<Address, String> {
    @Query(value = "SELECT * FROM address WHERE id = :addressDTO AND removal_flag = 0", nativeQuery = true)
    List<AddressDTO> searchAddressList(AddressDTO addressDTO);
}
