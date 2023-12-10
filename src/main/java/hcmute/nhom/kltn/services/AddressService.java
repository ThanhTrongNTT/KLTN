package hcmute.nhom.kltn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import hcmute.nhom.kltn.dto.AddressDTO;
import hcmute.nhom.kltn.model.Address;

/**
 * Create by: IntelliJ IDEA.
 *
 * @author : ThanhTrong
 **/
public interface AddressService extends AbstractService<AddressDTO, Address> {
    /**
     * searchAddressList.
     * @param pageable  : pageable
     * @return Page<AddressDTO>
     */
    Page<AddressDTO> getAllAddressPaging(Pageable pageable);

    /**
     * saveAddress.
     * @param addressId String
     * @param addressDTO AddressDTO
     * @return AddressDTO
     */
    AddressDTO updateAddress(String addressId, AddressDTO addressDTO);
}
