package hcmute.nhom.kltn.repository;

import java.util.List;
import java.util.UUID;
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
public interface AddressRepository extends AbstractRepository<Address, UUID> {
    @Query(value = "SELECT * FROM address WHERE address_id = ?1", nativeQuery = true)
    List<AddressDTO> searchAddressList(AddressDTO addressDTO);
}
