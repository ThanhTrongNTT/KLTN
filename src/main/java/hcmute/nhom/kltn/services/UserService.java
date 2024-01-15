package hcmute.nhom.kltn.services;

import java.util.List;
import org.springframework.data.domain.Page;
import hcmute.nhom.kltn.common.payload.ListResponse;
import hcmute.nhom.kltn.dto.UserDTO;
import hcmute.nhom.kltn.model.User;

/**
 * Class UserService.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
public interface UserService extends AbstractService<UserDTO, User> {

    /**
     * findByUserNameModel.
     * @param userName userName
     * @return UserDTO
     */
    User findByUserNameModel(String userName);

    /**
     * saveWithAddress.
     * @param userDTO UserDTO
     * @return UserDTO
     */
    UserDTO saveUserProfile(UserDTO userDTO, String email);

    /**
     * findByEmail.
     * @param email email
     * @return UserDTO
     */
    UserDTO findByEmail(String email);

    /**
     * changePassword.
     * @param email email
     * @param oldPassword oldPassword
     * @param newPassword newPassword
     * @return UserDTO
     */
    UserDTO changePassword(String email, String oldPassword, String newPassword);

    /**
     * getAllUser.
     * @param pageNo pageNo
     * @param pageSize pageSize
     * @param sortBy sortBy
     * @param sortDir sortDir
     * @return Page<UserDTO>
     */
    Page<UserDTO> getAllUserPaging(int pageNo, int pageSize, String sortBy, String sortDir);

    /**
     * activeUser.
     * @param email email
     * @return UserDTO
     */
    UserDTO activeUser(String email);

    /**
     * findByUserName.
     * @param userName userName
     * @return UserDTO
     */
    UserDTO findByUserName(String userName);

    /**
     * registerUser.
     * @param userDTO UserDTO
     * @return Boolean
     */
    Boolean registerUser(UserDTO userDTO);

    /**
     * searchUser.
     * @param userName String
     * @return List<UserDTO>
     */
    ListResponse<UserDTO> searchUser(String userName);

    /**
     * getAllUserName.
     * @return List<String>
     */
    List<String> getAllUserName();

    /**
     * updateAvatar.
     * @param email String
     * @param userDTO UserDTO
     * @return UserDTO
     */
    UserDTO updateAvatar(UserDTO userDTO, String email);

    /**
     * deleteAvatar.
     * @param email String
     * @return Boolean
     */
    Boolean deleteAvatar(String email);

    /**
     * updateCover.
     * @param email String
     * @param userDTO UserDTO
     * @return UserDTO
     */
    UserDTO updateCover(UserDTO userDTO, String email);

    /**
     * deleteCover.
     * @param email String
     * @return Boolean
     */
    Boolean deleteCover(String email);

    /**
     * checkActiveUser.
     * @param email String
     * @return Boolean
     */
    Boolean checkActiveUser(String email);
}
