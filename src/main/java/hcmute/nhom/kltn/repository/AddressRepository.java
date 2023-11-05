package hcmute.nhom.kltn.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import hcmute.nhom.kltn.dto.AddressDTO;
import hcmute.nhom.kltn.model.Address;

/**
 * Class AddressRepository.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Repository
public interface AddressRepository extends AbstractRepository<Address, Long>, JpaRepository<Address, Long> {
    @Query(value = "SELECT * FROM address WHERE address_id = ?1", nativeQuery = true)
    List<AddressDTO> searchAddressList(AddressDTO addressDTO);
}
