package hcmute.nhom.kltn.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import hcmute.nhom.kltn.common.payload.ListResponse;
import hcmute.nhom.kltn.common.payload.LoginRequest;
import hcmute.nhom.kltn.dto.UserDTO;
import hcmute.nhom.kltn.dto.UserProfileDTO;
import hcmute.nhom.kltn.enums.RoleName;
import hcmute.nhom.kltn.exception.NotFoundException;
import hcmute.nhom.kltn.exception.SystemErrorException;
import hcmute.nhom.kltn.mapper.UserMapper;
import hcmute.nhom.kltn.model.Role;
import hcmute.nhom.kltn.model.User;
import hcmute.nhom.kltn.repository.UserRepository;
import hcmute.nhom.kltn.services.RoleService;
import hcmute.nhom.kltn.services.UserProfileService;
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
    private static final String SERVICE = "UserService";
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserProfileService userProfileService;


    @Override
    public User findByUserNameModel(String userName) {
        logger.info(getMessageStart(SERVICE, "FindByUserNameModel"));
        logger.debug(getMessageInputParam(SERVICE, "userName", userName));
        try {
            User user = getRepository().findByUserName(userName)
                    .orElseThrow(() -> new NotFoundException("User not found"));
            logger.debug(getMessageOutputParam(SERVICE, "user", user));
            logger.info(getMessageEnd(SERVICE, "FindByUserNameModel"));
            return user;
        } catch (NotFoundException e) {
            String message = "User not found";
            logger.error("{}", message);
            logger.info(getMessageEnd(SERVICE, "FindByUserNameModel"));
            throw new NotFoundException(message);
        }
    }

    @Override
    public UserDTO saveUserProfile(UserDTO userDTO, String email) {
        String method = "SaveUserProfile";
        logger.info(getMessageStart(SERVICE, method));
        logger.debug(getMessageInputParam(SERVICE, "userDTO", userDTO));
        logger.debug(getMessageInputParam(SERVICE, "email", email));
        try {
            UserDTO user = findByEmail(email);
            UserProfileDTO userProfileDTOSave = null;
            if (Objects.isNull(userDTO.getUserProfile())) {
                String message = "User profile is null";
                logger.error("{}", message);
                throw new SystemErrorException(message);
            } else if (Objects.nonNull(user.getUserProfile()) && Objects.isNull(user.getUserProfile().getId())) {
                userProfileDTOSave = userProfileService.updateProfile(userDTO.getUserProfile(), email);
            } else {
                userProfileDTOSave = userProfileService.saveUserProfile(userDTO.getUserProfile());
            }
            user.setUserProfile(userProfileDTOSave);
            if (Objects.isNull(user.getUserName())) {
                List<String> userNames = getAllUserName();
                if (userNames.contains(userDTO.getUserName())) {
                    String message = "User name is exist";
                    logger.error("{}", message);
                    logger.info(getMessageEnd(SERVICE, method));
                    throw new SystemErrorException(message);
                }
                user.setUserName(userDTO.getUserName());
            }
            var entity = getMapper().toEntity(user, getCycleAvoidingMappingContext());
            entity = getRepository().save(entity);
            logger.debug("{}", getMessageOutputParam(SERVICE, "userDTO", userProfileDTOSave));
            logger.info(getMessageEnd(SERVICE, method));
            return getMapper().toDto(entity, getCycleAvoidingMappingContext());
        } catch (Exception e) {
            String message = e.getMessage();
            logger.error("{}", message);
            logger.info(getMessageEnd(SERVICE, method));
            throw new SystemErrorException(message);
        }
    }

    @Override
    public UserDTO findByEmail(String email) {
        logger.info(getMessageStart(SERVICE, "FindByEmail"));
        logger.debug(getMessageInputParam(SERVICE, "email", email));
        try {
            User user = getRepository().findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
            logger.debug(getMessageOutputParam(SERVICE, "user", user));
            logger.info(getMessageEnd(SERVICE, "FindByEmail"));
            return getMapper().toDto(user, getCycleAvoidingMappingContext());
        } catch (NotFoundException e) {
            String message = "User not found";
            logger.error("{}", message);
            logger.info(getMessageEnd(SERVICE, "FindByEmail"));
            throw new NotFoundException(message);
        }
    }

    @Override
    public UserDTO changePassword(String email, String oldPassword, String newPassword) {
        logger.info(getMessageStart(SERVICE, "ChangePassword"));
        logger.debug(getMessageInputParam(SERVICE, "email", email));
        try {
            User user = getRepository().findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
            if (user.getPassword().equals(oldPassword)) {
                user.setPassword(newPassword);
                user = getRepository().save(user);
                logger.info(getMessageEnd(SERVICE, "ChangePassword"));
                return getMapper().toDto(user, getCycleAvoidingMappingContext());
            } else {
                logger.info(getMessageEnd(SERVICE, "ChangePassword"));
                throw new NotFoundException("Old password is not correct");
            }
        } catch (NotFoundException e) {
            String message = e.getMessage();
            logger.error("{}", message);
            logger.info(getMessageEnd(SERVICE, "ChangePassword"));
            throw new NotFoundException(message);
        }
    }

    @Override
    public Page<UserDTO> getAllUserPaging(int pageNo, int pageSize, String sortBy, String sortDir) {
        logger.info(getMessageStart(SERVICE, "GetAllUser"));
        logger.debug(getMessageInputParam(SERVICE, "page", pageNo));
        logger.debug(getMessageInputParam(SERVICE, "size", pageSize));
        PageRequest pageRequest = Utilities.getPageRequest(pageNo, pageSize, sortBy, sortDir);
        Page<User> users = getRepository().findAll(pageRequest);
        logger.info(getMessageEnd(SERVICE, "GetAllUser"));
        return users.map(user -> getMapper().toDto(user, getCycleAvoidingMappingContext()));
    }

    @Override
    public UserDTO activeUser(String email) {
        logger.info(getMessageStart(SERVICE, "ActiveUser"));
        logger.debug(getMessageInputParam(SERVICE, "email", email));
        try {
            User user = getRepository().findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
            user.setIsActive(true);
            user = getRepository().save(user);
            logger.debug(getMessageOutputParam(SERVICE, "ouput", true));
            logger.info(getMessageEnd(SERVICE, "ActiveUser"));
            return getMapper().toDto(user, getCycleAvoidingMappingContext());
        } catch (NotFoundException e) {
            String message = e.getMessage();
            logger.error("{}", message);
            logger.info(getMessageEnd(SERVICE, "ActiveUser"));
            throw new NotFoundException(message);
        }
    }

    @Override
    public UserDTO findByUserName(String userName) {
        logger.info(getMessageStart(SERVICE, "FindByUserName"));
        logger.debug(getMessageInputParam(SERVICE, "userName", userName));
        try {
            User user =
                    getRepository().findByUserName(userName).orElseThrow(() -> new NotFoundException("User not found"));
            logger.debug(getMessageOutputParam(SERVICE, "user", user));
            logger.info(getMessageEnd(SERVICE, "FindByUserName"));
            return getMapper().toDto(user, getCycleAvoidingMappingContext());
        } catch (NotFoundException e) {
            String message = "User not found";
            logger.error("{}", message);
            logger.info(getMessageEnd(SERVICE, "FindByUserName"));
            throw new NotFoundException(message);
        }
    }

    @Override
    public Boolean registerUser(LoginRequest registerRequest) {
        logger.info(getMessageStart(SERVICE, "RegisterUser"));
        logger.debug(getMessageInputParam(SERVICE, "registerRequest", registerRequest));
        try {
            Role userRole = roleService.findByName(RoleName.USER.name());
            Set<Role> roleDTOS = new HashSet<>();
            roleDTOS.add(userRole);
            User user = getRepository().findByEmail(registerRequest.getEmail()).orElse(null);
            if (Objects.isNull(user)) {
                user = new User();
                user.setEmail(registerRequest.getEmail());
                user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
                user.setIsActive(false);
                user.setRoles(roleDTOS);
            } else {
                throw new SystemErrorException("Email is exist");
            }
            getRepository().save(user);
            return true;
        } catch (SystemErrorException e) {
            String message = e.getMessage();
            logger.error("{}", message);
            logger.info(getMessageEnd(SERVICE, "RegisterUser"));
            throw new SystemErrorException(message);
        }
    }

    @Override
    public ListResponse<UserDTO> searchUser(String userName) {
        logger.info(getMessageStart(SERVICE, "SearchUser"));
        logger.debug(getMessageInputParam(SERVICE, "userName", userName));
        try {
            String search = "%" + userName + "%";
            logger.info("{}", search);
            List<User> users = getRepository().findByUserNameContaining(search);
            logger.debug(getMessageOutputParam(SERVICE, "users", users));
            logger.info(getMessageEnd(SERVICE, "SearchUser"));
            ListResponse<UserDTO> listResponse = Utilities.createListResponse(users.stream().map(user ->
                    getMapper().toDto(user, getCycleAvoidingMappingContext())).collect(Collectors.toList()));
            return listResponse;
        } catch (Exception e) {
            String message = "Search user fail";
            logger.error("{}", message);
            logger.info(getMessageEnd(SERVICE, "SearchUser"));
            throw new SystemErrorException(message);
        }
    }

    @Override
    public List<String> getAllUserName() {
        logger.info(getMessageStart(SERVICE, "GetAllUserName"));
        List<String> userNames = getRepository().getAllUserName();
        logger.debug(getMessageOutputParam(SERVICE, "userNames", userNames));
        logger.info(getMessageEnd(SERVICE, "GetAllUserName"));
        return userNames.stream().filter(item -> Objects.nonNull(item)).collect(Collectors.toList());
    }

    @Override
    public UserDTO updateAvatar(UserDTO userDTO, String email) {
        logger.info(getMessageStart(SERVICE, "UpdateAvatar"));
        logger.debug(getMessageInputParam(SERVICE, "userDTO", userDTO));
        logger.debug(getMessageInputParam(SERVICE, "email", email));
        try {
            UserDTO user = findByEmail(email);
            if (Objects.isNull(user.getUserProfile())) {
                String message = "User profile is null";
                logger.error("{}", message);
                throw new SystemErrorException(message);
            }
            UserProfileDTO userProfileDTOSave = userProfileService.updateProfile(userDTO.getUserProfile(), email);
            user.setUserProfile(userProfileDTOSave);
            var entity = getMapper().toEntity(user, getCycleAvoidingMappingContext());
            entity = getRepository().save(entity);
            logger.debug(getMessageOutputParam(SERVICE, "userDTO", userProfileDTOSave));
            logger.info(getMessageEnd(SERVICE, "UpdateAvatar"));
            return getMapper().toDto(entity, getCycleAvoidingMappingContext());
        } catch (Exception e) {
            String message = e.getMessage();
            logger.error("{}", message);
            logger.info(getMessageEnd(SERVICE, "UpdateAvatar"));
            throw new SystemErrorException(message);
        }
    }

    @Override
    public Boolean deleteAvatar(String email) {
        logger.info(getMessageStart(SERVICE, "DeleteAvatar"));
        logger.debug(getMessageInputParam(SERVICE, "email", email));
        try {
            UserDTO user = findByEmail(email);
            if (Objects.isNull(user.getUserProfile())) {
                String message = "User profile is null";
                logger.error("{}", message);
                throw new SystemErrorException(message);
            }
            UserProfileDTO profileDTO = userProfileService.deleteAvatar(user.getUserProfile().getId().toString());
            user.setUserProfile(profileDTO);
            var entity = getMapper().toEntity(user, getCycleAvoidingMappingContext());
            entity = getRepository().save(entity);
            logger.debug(getMessageOutputParam(SERVICE, "userDTO", true));
            logger.info(getMessageEnd(SERVICE, "DeleteAvatar"));
            return true;
        } catch (Exception e) {
            String message = e.getMessage();
            logger.error("{}", message);
            logger.info(getMessageEnd(SERVICE, "DeleteAvatar"));
            throw new SystemErrorException(message);
        }
    }

    @Override
    public UserDTO updateCover(UserDTO userDTO, String email) {
        logger.info(getMessageStart(SERVICE, "UpdateCover"));
        logger.debug(getMessageInputParam(SERVICE, "userDTO", userDTO));
        logger.debug(getMessageInputParam(SERVICE, "email", email));
        try {
            UserDTO user = findByEmail(email);
            if (Objects.isNull(user.getUserProfile())) {
                String message = "User profile is null";
                logger.error("{}", message);
                throw new SystemErrorException(message);
            }
            UserProfileDTO userProfileDTOSave = userProfileService.updateProfile(userDTO.getUserProfile(), email);
            user.setUserProfile(userProfileDTOSave);
            var entity = getMapper().toEntity(user, getCycleAvoidingMappingContext());
            entity = getRepository().save(entity);
            logger.debug(getMessageOutputParam(SERVICE, "userDTO", userProfileDTOSave));
            logger.info(getMessageEnd(SERVICE, "UpdateCover"));
            return getMapper().toDto(entity, getCycleAvoidingMappingContext());
        } catch (Exception e) {
            String message = e.getMessage();
            logger.error("{}", message);
            logger.info(getMessageEnd(SERVICE, "UpdateCover"));
            throw new SystemErrorException(message);
        }
    }

    @Override
    public Boolean deleteCover(String email) {
        logger.info(getMessageStart(SERVICE, "DeleteCover"));
        logger.debug(getMessageInputParam(SERVICE, "email", email));
        try {
            UserDTO user = findByEmail(email);
            if (Objects.isNull(user.getUserProfile())) {
                String message = "User profile is null";
                logger.error("{}", message);
                throw new SystemErrorException(message);
            }
            UserProfileDTO profileDTO = userProfileService.deleteCover(user.getUserProfile().getId().toString());
            user.setUserProfile(profileDTO);
            var entity = getMapper().toEntity(user, getCycleAvoidingMappingContext());
            entity = getRepository().save(entity);
            logger.debug(getMessageOutputParam(SERVICE, "userDTO", true));
            logger.info(getMessageEnd(SERVICE, "DeleteCover"));
            return true;
        } catch (Exception e) {
            String message = e.getMessage();
            logger.error("{}", message);
            logger.info(getMessageEnd(SERVICE, "DeleteCover"));
            throw new SystemErrorException(message);
        }
    }

    @Override
    public UserDTO save(UserDTO dto) {
        logger.info(getMessageStart(SERVICE, "Save User"));
        var entity = getMapper().toEntity(dto, getCycleAvoidingMappingContext());
        entity.setRoles(dto.getRoles());
        entity = getRepository().save(entity);
        UserDTO dtoSave = getMapper().toDto(entity, getCycleAvoidingMappingContext());
        logger.debug(getMessageOutputParam(SERVICE, "userDTO", dtoSave));
        logger.info(getMessageEnd(SERVICE, "Save User"));
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
