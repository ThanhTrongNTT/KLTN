package hcmute.nhom.kltn.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import hcmute.nhom.kltn.common.payload.ApiResponse;

/**
 * Class GlobalExceptionHandler.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    protected static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setCode("ERROR no handler found.");
        apiResponse.setResult(false);
        apiResponse.setMessage("[Ex3]: 404");
        log.error("No handler found.", ex);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle exception.
     *
     * @param ex Exception
     * @return ResponseEntity<Object>
     */
    @ExceptionHandler({UnauthorizationException.class})
    public ResponseEntity<ApiResponse<String>> notAllow(UnauthorizationException ex) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setResult(false);
        apiResponse.setMessage(ex.getMessage());
        log.error("Got unAuthorization exception", ex);
        return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
    }

    /**
     * Handle exception.
     *
     * @param ex Exception
     * @return ResponseEntity<Object>
     */
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ApiResponse<String>> globalExceptionNotFoundHandler(NotFoundException ex) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setResult(false);
        apiResponse.setMessage(ex.getMessage());
        log.error("Got not found exception", ex);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }
}
