package hcmute.nhom.kltn.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import hcmute.nhom.kltn.dto.AddressDTO;
import hcmute.nhom.kltn.model.Address;

/**
 * Create by: IntelliJ IDEA
 *
 * @author : ThanhTrong
 * @mailto : ntt.thanhtrong@gmail.com
 * @created : 6/5/2023, Monday
 * @filename : AddressRepository
 **/
@Repository
public interface AddressRepository extends AbstractRepository<Address, String> {
    @Query(value = "SELECT * FROM address WHERE address_id = ?1", nativeQuery = true)
    List<AddressDTO> searchAddressList(AddressDTO addressDTO);
}
