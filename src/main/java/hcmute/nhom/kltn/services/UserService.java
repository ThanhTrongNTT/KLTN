package hcmute.nhom.kltn.services;

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

    UserDTO saveWithAddress(UserDTO userDTO);

    UserDTO findByEmail(String email);
}
