package hcmute.nhom.kltn.services;

import org.springframework.data.domain.Page;
import hcmute.nhom.kltn.common.payload.LoginRequest;
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
     * @param userDTO userDTO
     * @return UserDTO
     */
    UserDTO saveWithAddress(UserDTO userDTO);

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
    Page<UserDTO> getAllUser(int pageNo, int pageSize, String sortBy, String sortDir);

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
     * @param registerRequest registerRequest
     * @return Boolean
     */
    Boolean registerUser(LoginRequest registerRequest);
}
