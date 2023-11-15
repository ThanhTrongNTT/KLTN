package hcmute.nhom.kltn.services.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import hcmute.nhom.kltn.dto.AddressDTO;
import hcmute.nhom.kltn.mapper.AddressMapper;
import hcmute.nhom.kltn.model.Address;
import hcmute.nhom.kltn.repository.AddressRepository;
import hcmute.nhom.kltn.services.AddressService;

/**
 * Class AddressServiceImpl.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Service
@RequiredArgsConstructor
public class AddressServiceImpl extends AbstractServiceImpl<AddressRepository, AddressMapper, AddressDTO, Address>
        implements AddressService {
    private static final Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);
    private static final String SERVICE = "AddressService";
    private final AddressRepository addressRepository;

    @Override
    public Page<AddressDTO> getAddressListPageable(AddressDTO addressDTO, Pageable pageable) {
        logger.info(getMessageStart(SERVICE, "getAddressListPageable"));
        logger.debug(getMessageInputParam(SERVICE, "addressDTO", addressDTO.toString()));
        List<AddressDTO> list = addressRepository.searchAddressList(addressDTO);
        logger.debug(getMessageOutputParam(SERVICE, "list", list.size()));
        logger.info(getMessageEnd(SERVICE, "getAddressListPageable"));
        return new PageImpl<>(list, pageable, list.size());
    }
}
