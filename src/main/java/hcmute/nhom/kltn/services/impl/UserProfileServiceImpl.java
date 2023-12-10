package hcmute.nhom.kltn.services.impl;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import hcmute.nhom.kltn.dto.AddressDTO;
import hcmute.nhom.kltn.dto.UserProfileDTO;
import hcmute.nhom.kltn.exception.SystemErrorException;
import hcmute.nhom.kltn.mapper.UserProfileMapper;
import hcmute.nhom.kltn.model.UserProfile;
import hcmute.nhom.kltn.repository.UserProfileRepository;
import hcmute.nhom.kltn.services.AddressService;
import hcmute.nhom.kltn.services.UserProfileService;
import hcmute.nhom.kltn.util.Constants;

/**
 * Class UserProfileServiceImpl.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl
        extends AbstractServiceImpl<UserProfileRepository, UserProfileMapper, UserProfileDTO, UserProfile>
        implements UserProfileService {
    private static final Logger logger = LoggerFactory.getLogger(UserProfileServiceImpl.class);
    private static final String SERVICE = "UserProfileService";
    private final AddressService addressService;
    private final UserProfileRepository userProfileRepository;

    @Override
    public UserProfileDTO saveUserProfile(UserProfileDTO userProfileDTO) {
        String method = "SaveUserProfile";
        logger.info(getMessageStart(SERVICE, method));
        logger.debug(getMessageInputParam(SERVICE, "userProfileDTO", userProfileDTO));
        try {
            if (Objects.nonNull(userProfileDTO.getAddress())) {
                AddressDTO addressDTO = addressService.save(userProfileDTO.getAddress());
                userProfileDTO.setAddress(addressDTO);
            }
            UserProfile userProfile = getRepository().save(getMapper()
                    .toEntity(userProfileDTO, getCycleAvoidingMappingContext()));
            UserProfileDTO result = getMapper().toDto(userProfile, getCycleAvoidingMappingContext());
            logger.debug(getMessageOutputParam(SERVICE, "result", result));
            logger.info(getMessageEnd(SERVICE, method));
            return result;
        } catch (Exception e) {
            String message = "SaveUserProfile fail";
            logger.error("{}", message);
            logger.info(getMessageEnd(SERVICE, method));
            throw new SystemErrorException(message);
        }
    }

    @Override
    public UserProfileDTO updateProfile(UserProfileDTO userProfileDTO, String email) {
        String method = "UpdateProfile";
        logger.info(getMessageStart(SERVICE, method));
        logger.debug(getMessageInputParam(SERVICE, "userProfileDTO", userProfileDTO));
        logger.debug(getMessageInputParam(SERVICE, "email", email));
        try {
            UserProfileDTO userProfile = getMapper().toDto(getRepository().findByEmailUser(email),
                    getCycleAvoidingMappingContext());
            userProfileDTO.setId(userProfile.getId());
            userProfileDTO.setRemovalFlag(Constants.REMOVAL_FLAG_FALSE);
            if (Objects.nonNull(userProfileDTO.getAddress())) {
                AddressDTO addressDTO = addressService.save(userProfileDTO.getAddress());
                userProfileDTO.setAddress(addressDTO);
            }
            UserProfile userProfileResult = getRepository().save(getMapper()
                    .toEntity(userProfileDTO, getCycleAvoidingMappingContext()));
            UserProfileDTO result = getMapper().toDto(userProfileResult, getCycleAvoidingMappingContext());
            logger.debug(getMessageOutputParam(SERVICE, "result", result));
            logger.info(getMessageEnd(SERVICE, method));
            return result;
        } catch (Exception e) {
            String message = e.getMessage();
            logger.error("{}", message);
            logger.info(getMessageEnd(SERVICE, method));
            throw new SystemErrorException(message);
        }
    }

    @Override
    public UserProfileDTO deleteAvatar(String id) {
        String method = "DeleteAvatar";
        logger.info(getMessageStart(SERVICE, method));
        logger.debug(getMessageInputParam(SERVICE, "id", id));
        try {
            UserProfile userProfile = getRepository().findById(id).orElse(null);
            if (Objects.nonNull(userProfile)) {
                userProfile.setAvatar(null);
                userProfile = getRepository().save(userProfile);
                UserProfileDTO result = getMapper().toDto(userProfile, getCycleAvoidingMappingContext());
                logger.debug(getMessageOutputParam(SERVICE, "result", result));
                logger.info(getMessageEnd(SERVICE, method));
                return result;
            } else {
                UserProfileDTO result = getMapper().toDto(userProfile, getCycleAvoidingMappingContext());
                logger.debug(getMessageOutputParam(SERVICE, "result", result));
                logger.info(getMessageEnd(SERVICE, method));
                return result;
            }
        } catch (Exception e) {
            String message = e.getMessage();
            logger.error("{}", message);
            logger.info(getMessageEnd(SERVICE, method));
            throw new SystemErrorException(message);
        }
    }

    @Override
    public UserProfileDTO deleteCover(String id) {
        String method = "DeleteCover";
        logger.info(getMessageStart(SERVICE, method));
        logger.debug(getMessageInputParam(SERVICE, "id", id));
        try {
            UserProfile userProfile = getRepository().findById(id).orElse(null);
            if (Objects.nonNull(userProfile)) {
                userProfile.setCover(null);
                userProfile = getRepository().save(userProfile);
                UserProfileDTO result = getMapper().toDto(userProfile, getCycleAvoidingMappingContext());
                logger.debug(getMessageOutputParam(SERVICE, "result", result));
                logger.info(getMessageEnd(SERVICE, method));
                return result;
            } else {
                UserProfileDTO result = getMapper().toDto(userProfile, getCycleAvoidingMappingContext());
                logger.debug(getMessageOutputParam(SERVICE, "result", result));
                logger.info(getMessageEnd(SERVICE, method));
                return result;
            }
        } catch (Exception e) {
            String message = e.getMessage();
            logger.error("{}", message);
            logger.info(getMessageEnd(SERVICE, method));
            throw new SystemErrorException(message);
        }
    }

    @Override
    public UserProfileMapper getMapper() {
        return UserProfileMapper.INSTANCE;
    }

    @Override
    public UserProfileRepository getRepository() {
        return userProfileRepository;
    }
}
