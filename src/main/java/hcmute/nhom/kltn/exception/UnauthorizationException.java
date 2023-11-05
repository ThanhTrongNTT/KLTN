package hcmute.nhom.kltn.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Class UnauthorizationException.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizationException extends RuntimeException {
    public UnauthorizationException(String message) {
        super(message);
    }
}
