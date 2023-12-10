package hcmute.nhom.kltn.services;

import hcmute.nhom.kltn.dto.UserProfileDTO;
import hcmute.nhom.kltn.model.UserProfile;

/**
 * Class UserProfileService.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
public interface UserProfileService extends AbstractService<UserProfileDTO, UserProfile> {
    /**
     * findByUserNameModel.
     * @param userProfileDTO userProfileDTO
     * @return UserProfileDTO
     */
    UserProfileDTO saveUserProfile(UserProfileDTO userProfileDTO);

    /**
     *  updateProfile.
     * @param userProfileDTO userProfileDTO
     * @return UserProfileDTO
     */
    UserProfileDTO updateProfile(UserProfileDTO userProfileDTO, String email);

    /**
     * deleteAvatar.
     * @param id String
     * @return Boolean
     */
    UserProfileDTO deleteAvatar(String id);

    /**
     * deleteCover.
     * @param id String
     * @return Boolean
     */
    UserProfileDTO deleteCover(String id);
}
