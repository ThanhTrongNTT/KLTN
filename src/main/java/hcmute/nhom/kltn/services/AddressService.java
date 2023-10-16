package hcmute.nhom.kltn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import hcmute.nhom.kltn.dto.AddressDTO;
import hcmute.nhom.kltn.model.Address;

/**
 * Create by: IntelliJ IDEA
 *
 * @author : ThanhTrong
 * @mailto : ntt.thanhtrong@gmail.com
 * @created : 6/5/2023, Monday
 * @filename : AddressService
 **/
public interface AddressService extends AbstractService<AddressDTO, Address>{
    Page<AddressDTO> searchAddressList(AddressDTO addressDTO, Pageable pageable);
}
