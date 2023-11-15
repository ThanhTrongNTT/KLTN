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

    User findByUserNameModel(String userName);

    UserDTO saveWithAddress(UserDTO userDTO);

    UserDTO findByEmail(String email);

    UserDTO changePassword(String email, String oldPassword, String newPassword);

    Page<UserDTO> getAllUser(int pageNo, int pageSize, String sortBy, String sortDir);

    UserDTO activeUser(String email);

    UserDTO findByUserName(String userName);

    Boolean registerUser(LoginRequest registerRequest);
}
