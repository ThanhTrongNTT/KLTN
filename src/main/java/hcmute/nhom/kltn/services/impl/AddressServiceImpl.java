package hcmute.nhom.kltn.services.impl;

import hcmute.nhom.kltn.dto.AddressDTO;
import hcmute.nhom.kltn.mapper.AddressMapper;
import hcmute.nhom.kltn.model.Address;
import hcmute.nhom.kltn.repository.AddressRepository;
import hcmute.nhom.kltn.services.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Create by: IntelliJ IDEA
 *
 * @author : ThanhTrong
 * @mailto : ntt.thanhtrong@gmail.com
 * @created : 6/5/2023, Monday
 * @filename : AddressServiceImpl
 **/
@Service
@RequiredArgsConstructor
public class AddressServiceImpl extends AbstractServiceImpl<AddressRepository, AddressMapper, AddressDTO, Address>
        implements AddressService {
    private final AddressRepository addressRepository;
    @Override
    public Page<AddressDTO> searchAddressList(AddressDTO addressDTO, Pageable pageable) {
        List<AddressDTO> list = addressRepository.searchAddressList(addressDTO);
        return new PageImpl<>(list, pageable, list.size());
    }
}
