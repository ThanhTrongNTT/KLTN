package hcmute.nhom.kltn.common.payload;

import lombok.Data;

/**
 * Class LoginRequest.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Data
public class LoginRequest {
    private String email;
    private String password;
}
