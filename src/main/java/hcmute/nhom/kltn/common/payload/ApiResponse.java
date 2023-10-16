package hcmute.nhom.kltn.common.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class ApiResponse.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Getter
@Setter
@NoArgsConstructor
public class ApiResponse<T> {
    private Boolean result = true;
    private String message = null;
    private String code = null;
    private T data = null;

    public ApiResponse(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public ApiResponse(String message) {
        this.message = message;
    }
}
