package hcmute.nhom.kltn.common.payload;

import lombok.Data;

/**
 * Class ChangePasswordRequest.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Data
public class ChangePasswordRequest {
    private String email;
    private String oldPassword;
    private String newPassword;
}
