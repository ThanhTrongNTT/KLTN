package hcmute.nhom.kltn.controller.apiv1;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import hcmute.nhom.kltn.common.payload.ApiResponse;
import hcmute.nhom.kltn.common.payload.ChangePasswordRequest;
import hcmute.nhom.kltn.common.payload.ListResponse;
import hcmute.nhom.kltn.controller.AbstractController;
import hcmute.nhom.kltn.dto.UserDTO;
import hcmute.nhom.kltn.services.UserService;
import hcmute.nhom.kltn.util.Constants;
import hcmute.nhom.kltn.util.SessionConstants;

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
     *
     * @param httpServletRequest    HttpServletRequest
     * @param changePasswordRequest ChangePasswordRequest
     * @return ResponseEntity<?>
     */
    @PostMapping("user/change-password")
    public ResponseEntity<ApiResponse<String>> changePassword(
            HttpServletRequest httpServletRequest,
            @RequestBody ChangePasswordRequest changePasswordRequest) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "changePassword");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "changePassword");
        logger.info("{}", messageStart);
        // Execute changePassword
        userService.changePassword(changePasswordRequest.getEmail(), changePasswordRequest.getOldPassword(),
                changePasswordRequest.getNewPassword());
        logger.debug("{}", changePasswordRequest);
        logger.info("{}", messageEnd);
        return new ResponseEntity<>(new ApiResponse<>("Change password successfully"), HttpStatus.OK);
    }

    /**
     * Get user by email.
     *
     * @param httpServletRequest HttpServletRequest
     * @param email              String
     * @return ResponseEntity<?>
     */
    @GetMapping("user/email/{email}")
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
     *
     * @param httpServletRequest HttpServletRequest
     * @param id                 Long
     * @return ResponseEntity<?>
     */
    @GetMapping("user/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(HttpServletRequest httpServletRequest,
                                                            @PathVariable String id) {
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
     *
     * @param httpServletRequest HttpServletRequest
     * @return ResponseEntity<?>
     */
    @GetMapping("users")
    public ResponseEntity<ApiResponse<Page<UserDTO>>> getAllUsers(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "pageNo", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false)
            int pageNo,
            @RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false)
            int pageSize,
            @RequestParam(value = "sortBy", defaultValue = Constants.DEFAULT_SORT_BY, required = false)
            String sortBy,
            @RequestParam(value = "sortDir", defaultValue = Constants.DEFAULT_SORT_DIRECTION, required = false)
            String sortDir) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "getAllUsers");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "getAllUsers");
        logger.info("{}", messageStart);
        // Execute getAllUsers
        Page<UserDTO> userDTOs = userService.getAllUserPaging(pageNo, pageSize, sortBy, sortDir);
        logger.info("{}", messageEnd);
        return ResponseEntity.ok().body(new ApiResponse<>(userDTOs, "Get all users successfully"));
    }

    /**
     * Active user.
     *
     * @param httpServletRequest HttpServletRequest
     * @param email              String
     * @return ResponseEntity<?>
     */
    @PostMapping("user/active/{email}")
    public ResponseEntity<ApiResponse<String>> activeUser(HttpServletRequest httpServletRequest,
                                                     @PathVariable String email) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "activeUser");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "activeUser");
        logger.info("{}", messageStart);
        // Execute activeUser
        userService.activeUser(email);
        logger.info("{}", messageEnd);
        return ResponseEntity.ok().body(new ApiResponse<>(null, "Active user successfully"));
    }

    /**
     * Search user.
     * @param httpServletRequest HttpServletRequest
     * @param userName          String
     * @return ResponseEntity<ApiResponse<List<UserDTO>>>
     */
    @GetMapping("users/search")
    public ResponseEntity<ApiResponse<ListResponse<UserDTO>>> searchUser(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "userName") String userName
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "searchUser");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "searchUser");
        logger.info("{}", messageStart);
        // Execute searchUser
        ListResponse<UserDTO> userDTOs = userService.searchUser(userName);
        logger.info("{}", messageEnd);
        return ResponseEntity.ok().body(new ApiResponse<>(userDTOs, "Search user successfully"));
    }

    /**
     * Update user profile.
     * @param httpServletRequest HttpServletRequest
     * @param session           HttpSession
     * @param userDTO          UserDTO
     * @return ResponseEntity<ApiResponse<UserDTO>>
     */
    @PostMapping("users/profile")
    public ResponseEntity<ApiResponse<UserDTO>> updateUserProfile(
            HttpServletRequest httpServletRequest,
            HttpSession session,
            @RequestBody UserDTO userDTO
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "updateUserProfile");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "updateUserProfile");
        logger.info("{}", messageStart);
        try {
            String email = session.getAttribute(SessionConstants.USER_EMAIL).toString();
            // Execute updateUserProfile
            UserDTO result = userService.saveUserProfile(userDTO, email);
            logger.info("{}", messageEnd);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(result, "Update user profile successfully"));
        } catch (Exception e) {
            String message = "Update user profile failed";
            logger.error("{}", message);
            return new ResponseEntity<>(new ApiResponse<>(false, null, message), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get user by userName.
     * @param httpServletRequest HttpServletRequest
     * @param userName         String
     * @return ResponseEntity<ApiResponse<UserDTO>>
     */
    @GetMapping("user/userName/{userName}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserByUserName(
            HttpServletRequest httpServletRequest,
            @PathVariable String userName) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "getUserByUserName");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "getUserByUserName");
        logger.info("{}", messageStart);
        // Execute getUserByUserName
        UserDTO userDTO = userService.findByUserName(userName);
        logger.info("{}", messageEnd);
        return ResponseEntity.ok().body(new ApiResponse<>(userDTO, "Get user by userName successfully"));
    }

    /**
     * Update avatar.
     * @param httpServletRequest HttpServletRequest
     * @param session          HttpSession
     * @param userDTO        UserDTO
     * @return ResponseEntity<ApiResponse<UserDTO>>
     */
    @PostMapping("user/avatar")
    public ResponseEntity<ApiResponse<UserDTO>> updateAvatar(
            HttpServletRequest httpServletRequest,
            HttpSession session,
            @RequestBody UserDTO userDTO
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "updateAvatar");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "updateAvatar");
        logger.info("{}", messageStart);
        try {
            String email = session.getAttribute(SessionConstants.USER_EMAIL).toString();
            // Execute updateAvatar
            UserDTO result = userService.updateAvatar(userDTO, email);
            logger.info("{}", messageEnd);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(result, "Update avatar successfully"));
        } catch (Exception e) {
            String message = "Update avatar failed";
            logger.error("{}", message);
            return new ResponseEntity<>(new ApiResponse<>(false, null, message), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete Avatar.
     * @param httpServletRequest HttpServletRequest
     * @param session          HttpSession
     * @return ResponseEntity<ApiResponse<UserDTO>>
     */
    @DeleteMapping("user/avatar")
    public ResponseEntity<ApiResponse<Boolean>> deleteAvatar(
            HttpServletRequest httpServletRequest,
            HttpSession session
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "deleteAvatar");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "deleteAvatar");
        logger.info("{}", messageStart);
        try {
            String email = session.getAttribute(SessionConstants.USER_EMAIL).toString();
            // Execute deleteAvatar
            Boolean result = userService.deleteAvatar(email);
            logger.info("{}", messageEnd);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(result, "Delete avatar successfully"));
        } catch (Exception e) {
            String message = "Delete avatar failed";
            logger.error("{}", message);
            return new ResponseEntity<>(new ApiResponse<>(false, null, message), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update cover.
     * @param httpServletRequest HttpServletRequest
     * @param session          HttpSession
     * @param userDTO        UserDTO
     * @return ResponseEntity<ApiResponse<UserDTO>>
     */
    @PostMapping("user/cover")
    public ResponseEntity<ApiResponse<UserDTO>> updateCover(
            HttpServletRequest httpServletRequest,
            HttpSession session,
            @RequestBody UserDTO userDTO
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "updateCover");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "updateCover");
        logger.info("{}", messageStart);
        try {
            String email = session.getAttribute(SessionConstants.USER_EMAIL).toString();
            // Execute updateCover
            UserDTO result = userService.updateCover(userDTO, email);
            logger.info("{}", messageEnd);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(result, "Update cover successfully"));
        } catch (Exception e) {
            String message = "Update cover failed";
            logger.error("{}", message);
            return new ResponseEntity<>(new ApiResponse<>(false, null, message), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete cover.
     * @param httpServletRequest HttpServletRequest
     * @param session          HttpSession
     * @return ResponseEntity<ApiResponse<UserDTO>>
     */
    @DeleteMapping("user/cover")
    public ResponseEntity<ApiResponse<Boolean>> deleteCover(
            HttpServletRequest httpServletRequest,
            HttpSession session
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "deleteCover");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "deleteCover");
        logger.info("{}", messageStart);
        try {
            String email = session.getAttribute(SessionConstants.USER_EMAIL).toString();
            // Execute deleteCover
            Boolean result = userService.deleteCover(email);
            logger.info("{}", messageEnd);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(result, "Delete cover successfully"));
        } catch (Exception e) {
            String message = "Delete cover failed";
            logger.error("{}", message);
            return new ResponseEntity<>(new ApiResponse<>(false, null, message), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Check active user.
     * @param httpServletRequest HttpServletRequest
     * @param email String
     * @return
     */
    @GetMapping("user/check-active/{email}")
    public ResponseEntity<ApiResponse<Boolean>> checkActiveUser(
            HttpServletRequest httpServletRequest,
            @PathVariable("email") String email
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "checkActiveUser");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "checkActiveUser");
        logger.info("{}", messageStart);
        try {
            // Execute deleteCover
            Boolean result = userService.checkActiveUser(email);
            logger.info("{}", messageEnd);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(result, "Check active user successfully"));
        } catch (Exception e) {
            String message = "Check active user failed";
            logger.error("{}", message);
            return new ResponseEntity<>(new ApiResponse<>(false, null, message), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
