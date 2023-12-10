package hcmute.nhom.kltn.services.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import hcmute.nhom.kltn.dto.AddressDTO;
import hcmute.nhom.kltn.exception.NotFoundException;
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
    public Page<AddressDTO> getAllAddressPaging(Pageable pageable) {
        logger.info(getMessageStart(SERVICE, "getAddressListPageable"));
        List<Address> list = getRepository().findAll();
        List<AddressDTO> result =
                list.stream().map(address ->
                        getMapper().toDto(address, getCycleAvoidingMappingContext())).collect(Collectors.toList());
        logger.debug(getMessageOutputParam(SERVICE, "result", result.size()));
        logger.info(getMessageEnd(SERVICE, "getAddressListPageable"));
        return new PageImpl<>(result, pageable, result.size());
    }

    @Override
    public AddressDTO updateAddress(String addressId, AddressDTO addressDTO) {
        logger.info(getMessageStart(SERVICE, "updateAddress"));
        logger.debug(getMessageInputParam(SERVICE, "addressId", addressId));
        logger.debug(getMessageInputParam(SERVICE, "addressDTO", addressDTO));
        try {
            Address address = getRepository().findById(addressId)
                    .orElseThrow(() -> new NotFoundException("Address not found"));
            address.setStreet(addressDTO.getStreet());
            address.setDistrict(addressDTO.getDistrict());
            address.setCity(addressDTO.getCity());
            address.setCountry(addressDTO.getCountry());
            address.setPostalCode(addressDTO.getPostalCode());
            AddressDTO result = getMapper().toDto(getRepository().save(address), getCycleAvoidingMappingContext());
            logger.debug(getMessageOutputParam(SERVICE, "result", result));
            logger.info(getMessageEnd(SERVICE, "updateAddress"));
            return result;
        } catch (NotFoundException e) {
            String message = "Update Address fail";
            logger.error("{}", message);
            logger.info(getMessageEnd(SERVICE, "updateAddress"));
            throw new NotFoundException(message);
        }
    }

    @Override
    public AddressMapper getMapper() {
        return AddressMapper.INSTANCE;
    }

    @Override
    public AddressRepository getRepository() {
        return addressRepository;
    }
}
