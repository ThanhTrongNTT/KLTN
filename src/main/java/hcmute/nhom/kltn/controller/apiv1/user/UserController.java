package hcmute.nhom.kltn.controller.apiv1.user;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import hcmute.nhom.kltn.common.payload.ApiResponse;
import hcmute.nhom.kltn.common.payload.ChangePasswordRequest;
import hcmute.nhom.kltn.controller.AbstractController;
import hcmute.nhom.kltn.dto.UserDTO;
import hcmute.nhom.kltn.services.UserService;

/**
 * Class UserController.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version: 1.0.0
 **/
@RestController
@RequiredArgsConstructor
public class UserController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    /**
     * Change password.
     * @param httpServletRequest HttpServletRequest
     * @param changePasswordRequest ChangePasswordRequest
     * @return ResponseEntity<?>
     */
    @PostMapping("/user/change-password")
    public ResponseEntity<ApiResponse<String>> changePassword(HttpServletRequest httpServletRequest,
                                            @RequestBody ChangePasswordRequest changePasswordRequest) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "changePassword");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "changePassword");
        logger.info("{}", messageStart);
        // Execute changePassword
        logger.debug("{}", changePasswordRequest);
        logger.info("{}", messageEnd);
        return null;
    }

    /**
     * Get user by email.
     * @param httpServletRequest HttpServletRequest
     * @param email String
     * @return ResponseEntity<?>
     */
    @GetMapping("/user/{email}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserByEmail(HttpServletRequest httpServletRequest,
                                            @PathVariable String email) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "getUserByEmail");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "getUserByEmail");
        logger.info("{}", messageStart);
        // Execute getUserByEmail
        UserDTO userDTO = userService.findByEmail(email);
        logger.info("{}", messageEnd);
        return ResponseEntity.ok().body(new ApiResponse<>(userDTO, "Get user by email successfully"));
    }

    /**
     * Get user by id.
     * @param httpServletRequest HttpServletRequest
     * @param id Long
     * @return ResponseEntity<?>
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(HttpServletRequest httpServletRequest,
                                                            @PathVariable Long id) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "getUserById");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "getUserById");
        logger.info("{}", messageStart);
        // Execute getUserById
        UserDTO userDTO = userService.findById(id);
        logger.info("{}", messageEnd);
        return ResponseEntity.ok().body(new ApiResponse<>(userDTO, "Get user by id successfully"));
    }

    /**
     * Get all users.
     * @param httpServletRequest HttpServletRequest
     * @return ResponseEntity<?>
     */
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<?>> getAllUsers(HttpServletRequest httpServletRequest) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "getAllUsers");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "getAllUsers");
        logger.info("{}", messageStart);
        // Execute getAllUsers
        logger.info("{}", messageEnd);
        return null;
    }

    /**
     * Active user.
     * @param httpServletRequest HttpServletRequest
     * @param email String
     * @return ResponseEntity<?>
     */
    @PostMapping("/user/active/{email}")
    public ResponseEntity<ApiResponse<?>> activeUser(HttpServletRequest httpServletRequest,
                                                     @PathVariable String email) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "activeUser");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "activeUser");
        logger.info("{}", messageStart);
        // Execute activeUser
        logger.info("{}", messageEnd);
        return ResponseEntity.ok().body(new ApiResponse<>(null, "Active user successfully"));
    }
}
