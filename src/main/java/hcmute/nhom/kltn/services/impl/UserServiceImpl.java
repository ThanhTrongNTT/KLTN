package hcmute.nhom.kltn.services.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import hcmute.nhom.kltn.dto.UserDTO;
import hcmute.nhom.kltn.exception.NotFoundException;
import hcmute.nhom.kltn.mapper.UserMapper;
import hcmute.nhom.kltn.model.Address;
import hcmute.nhom.kltn.model.User;
import hcmute.nhom.kltn.repository.UserRepository;
import hcmute.nhom.kltn.services.AddressService;
import hcmute.nhom.kltn.services.UserService;

/**
 * Class UserServiceImpl.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends AbstractServiceImpl<UserRepository, UserMapper, UserDTO, User>
        implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String METHOD = "UserService";
    private final AddressService addressService;
    private final UserRepository userRepository;


    @Override
    public UserDTO saveWithAddress(UserDTO userDTO) {
        logger.info(getMessageStart(METHOD, "SaveWithAddress"));
        Address savedAddress = addressService.save(userDTO.getAddress());
        userDTO.setAddress(savedAddress);
        var entity = getMapper().toEntity(userDTO, getCycleAvoidingMappingContext());
        entity = getRepository().save(entity);
        logger.debug("{}", getMessageOutputParam(METHOD, "userDTO", userDTO));
        logger.info(getMessageEnd(METHOD, "SaveWithAddress"));
        return getMapper().toDto(entity, getCycleAvoidingMappingContext());
    }

    @Override
    public UserDTO findByEmail(String email) {
        logger.info(getMessageStart(METHOD, "FindByEmail"));
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
        logger.debug(getMessageOutputParam(METHOD, "user", user));
        logger.info(getMessageEnd(METHOD, "FindByEmail"));
        return getMapper().toDto(user, getCycleAvoidingMappingContext());
    }

    @Override
    public UserDTO save(UserDTO dto) {
        logger.info(getMessageStart(METHOD, "Save User"));
        var entity = getMapper().toEntity(dto, getCycleAvoidingMappingContext());
        entity.setRoles(dto.getRoles());
        entity = getRepository().save(entity);
        UserDTO dtoSave = getMapper().toDto(entity, getCycleAvoidingMappingContext());
        logger.debug(getMessageOutputParam(METHOD, "userDTO", dtoSave));
        logger.info(getMessageEnd(METHOD, "Save User"));
        return dtoSave;
    }

    @Override
    public UserMapper getMapper() {
        return UserMapper.INSTANCE;
    }

    @Override
    public UserRepository getRepository() {
        return userRepository;
    }
}
