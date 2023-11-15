package hcmute.nhom.kltn.services.impl;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import hcmute.nhom.kltn.common.payload.LoginRequest;
import hcmute.nhom.kltn.dto.AddressDTO;
import hcmute.nhom.kltn.dto.UserDTO;
import hcmute.nhom.kltn.enums.RoleName;
import hcmute.nhom.kltn.exception.NotFoundException;
import hcmute.nhom.kltn.exception.SystemErrorException;
import hcmute.nhom.kltn.mapper.UserMapper;
import hcmute.nhom.kltn.model.Role;
import hcmute.nhom.kltn.model.User;
import hcmute.nhom.kltn.repository.UserRepository;
import hcmute.nhom.kltn.services.AddressService;
import hcmute.nhom.kltn.services.RoleService;
import hcmute.nhom.kltn.services.UserService;
import hcmute.nhom.kltn.util.Utilities;

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
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User findByUserNameModel(String userName) {
        logger.info(getMessageStart(METHOD, "FindByUserNameModel"));
        logger.debug(getMessageInputParam(METHOD, "userName", userName));
        try {
            User user = userRepository.findByUserName(userName)
                    .orElseThrow(() -> new NotFoundException("User not found"));
            logger.debug(getMessageOutputParam(METHOD, "user", user));
            logger.info(getMessageEnd(METHOD, "FindByUserNameModel"));
            return user;
        } catch (NotFoundException e) {
            String message = "User not found";
            logger.error("{}", message);
            logger.info(getMessageEnd(METHOD, "FindByUserNameModel"));
            throw new NotFoundException(message);
        }
    }

    @Override
    public UserDTO saveWithAddress(UserDTO userDTO) {
        logger.info(getMessageStart(METHOD, "SaveWithAddress"));
        AddressDTO savedAddress = addressService.save(userDTO.getAddress());
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
        logger.debug(getMessageInputParam(METHOD, "email", email));
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
        logger.debug(getMessageOutputParam(METHOD, "user", user));
        logger.info(getMessageEnd(METHOD, "FindByEmail"));
        return getMapper().toDto(user, getCycleAvoidingMappingContext());
    }

    @Override
    public UserDTO changePassword(String email, String oldPassword, String newPassword) {
        logger.info(getMessageStart(METHOD, "ChangePassword"));
        logger.debug(getMessageInputParam(METHOD, "email", email));
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
        if (user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            user = userRepository.save(user);
            logger.info(getMessageEnd(METHOD, "ChangePassword"));
            return getMapper().toDto(user, getCycleAvoidingMappingContext());
        } else {
            logger.info(getMessageEnd(METHOD, "ChangePassword"));
            throw new NotFoundException("Old password is not correct");
        }
    }

    @Override
    public Page<UserDTO> getAllUser(int pageNo, int pageSize, String sortBy, String sortDir) {
        logger.info(getMessageStart(METHOD, "GetAllUser"));
        logger.debug(getMessageInputParam(METHOD, "page", pageNo));
        logger.debug(getMessageInputParam(METHOD, "size", pageSize));
        PageRequest pageRequest = Utilities.getPageRequest(pageNo, pageSize, sortBy, sortDir);
        Page<User> users = userRepository.findAll(pageRequest);
        logger.info(getMessageEnd(METHOD, "GetAllUser"));
        return users.map(user -> getMapper().toDto(user, getCycleAvoidingMappingContext()));
    }

    @Override
    public UserDTO activeUser(String email) {
        logger.info(getMessageStart(METHOD, "ActiveUser"));
        logger.debug(getMessageInputParam(METHOD, "email", email));
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
        user.setIsActive(true);
        user = userRepository.save(user);
        logger.info(getMessageEnd(METHOD, "ActiveUser"));
        return getMapper().toDto(user, getCycleAvoidingMappingContext());
    }

    @Override
    public UserDTO findByUserName(String userName) {
        logger.info(getMessageStart(METHOD, "FindByUserName"));
        logger.debug(getMessageInputParam(METHOD, "userName", userName));
        try {
            User user =
                    userRepository.findByUserName(userName).orElseThrow(() -> new NotFoundException("User not found"));
            logger.debug(getMessageOutputParam(METHOD, "user", user));
            logger.info(getMessageEnd(METHOD, "FindByUserName"));
            return getMapper().toDto(user, getCycleAvoidingMappingContext());
        } catch (NotFoundException e) {
            String message = "User not found";
            logger.error("{}", message);
            logger.info(getMessageEnd(METHOD, "FindByUserName"));
            throw new NotFoundException(message);
        }
    }

    @Override
    public Boolean registerUser(LoginRequest registerRequest) {
        logger.info(getMessageStart(METHOD, "RegisterUser"));
        logger.debug(getMessageInputParam(METHOD, "registerRequest", registerRequest));
        try {
            Role userRole = roleService.findByName(RoleName.USER.name());
            Set<Role> roleDTOS = new HashSet<>();
            roleDTOS.add(userRole);
            User user = userRepository.findByEmail(registerRequest.getEmail()).orElse(null);
            if (Objects.isNull(user)) {
                user = new User();
                user.setEmail(registerRequest.getEmail());
                user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
                user.setIsActive(false);
                user.setRoles(roleDTOS);
            } else {
                throw new SystemErrorException("Email is exist");
            }
            userRepository.save(user);
            return true;
        } catch (SystemErrorException e) {
            String message = "Register user fail";
            logger.error("{}", message);
            logger.info(getMessageEnd(METHOD, "RegisterUser"));
            throw new SystemErrorException(message);
        }
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
