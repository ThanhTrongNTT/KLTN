package hcmute.nhom.kltn.controller.apiv1.auth;

import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import hcmute.nhom.kltn.common.payload.ApiResponse;
import hcmute.nhom.kltn.common.payload.ForgotPasswordRequest;
import hcmute.nhom.kltn.common.payload.LoginRequest;
import hcmute.nhom.kltn.controller.AbstractController;
import hcmute.nhom.kltn.dto.UserDTO;
import hcmute.nhom.kltn.enums.GenderType;
import hcmute.nhom.kltn.enums.RoleName;
import hcmute.nhom.kltn.model.Role;
import hcmute.nhom.kltn.security.jwt.JwtProvider;
import hcmute.nhom.kltn.services.RoleService;
import hcmute.nhom.kltn.services.UserService;

/**
 * Class AuthenticationController.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version: 1.0.0
 **/
@RestController
public class AuthenticationController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Register User.
     * @param httpServletRequest HttpServletRequest
     * @param userDTO userDTO
     * @return ResponseEntity<?>
     */
    @PostMapping("/auth/register")
    public ResponseEntity<ApiResponse<UserDTO>> registerUser(HttpServletRequest httpServletRequest,
                                                    @RequestBody UserDTO userDTO) {
        logger.info(getMessageStart(httpServletRequest.getRequestURL().toString(), "registerUser"));
        // Execute RegisterUserc
        Role userRole = roleService.findByName(RoleName.USER.name());
        Set<Role> roleDTOS = new HashSet<>();
        roleDTOS.add(userRole);
        userDTO.setGender(GenderType.OTHER.name());
        userDTO.setRemovalFlag(false);
        userDTO.setRoles(roleDTOS);
        userDTO = userService.save(userDTO);
        logger.info(getMessageEnd(httpServletRequest.getRequestURL().toString(), "registerUser"));
        return ResponseEntity.ok().body(new ApiResponse(userDTO, "User registered successfully"));
    }

    /**
     * Login User.
     * @param httpServletRequest HttpServletRequest
     * @param loginRequest      LoginRequest
     * @return ResponseEntity<?>
     */
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(HttpServletRequest httpServletRequest,
                                   @Valid @RequestBody LoginRequest loginRequest) {
        String method = "loginUser";
        logger.info(getMessageStart(httpServletRequest.getRequestURL().toString(), method));
        // Execute Login
        UserDTO userDTO = userService.findByEmail(loginRequest.getEmail());
        if (userDTO == null) {
            logger.info(getMessageEnd(httpServletRequest.getRequestURL().toString(), method));
            return new ResponseEntity<>(new ApiResponse(null, "Email not found"),
                    HttpStatus.BAD_REQUEST);
        }
        if (!passwordEncoder.matches(loginRequest.getPassword(), userDTO.getPassword())) {
            logger.info(getMessageEnd(httpServletRequest.getRequestURL().toString(), method));
            return new ResponseEntity<>(new ApiResponse(null, "The password was wrong!"), HttpStatus.BAD_REQUEST);
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        logger.info("Creating Json Web Token!!");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        logger.info(getMessageEnd(httpServletRequest.getRequestURL().toString(), "loginUser"));
        return ResponseEntity.ok(jwtProvider.createToken(authentication));
    }

    /**
     * Forgot Password.
     * @param httpServletRequest HttpServletRequest
     * @param forgotPasswordRequest ForgotPasswordRequest
     * @return ResponseEntity<?>
     */
    @PostMapping("/auth/forgot-password")
    public ResponseEntity<?> forgotPassword(HttpServletRequest httpServletRequest,
                                            @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        logger.info(getMessageStart(httpServletRequest.getRequestURL().toString(), "forgotPassword"));
        // Execute ForgotPassword
        logger.info(getMessageEnd(httpServletRequest.getRequestURL().toString(), "forgotPassword"));
        return null;
    }

    /**
     * Logout.
     * @param httpServletRequest HttpServletRequest
     * @return ResponseEntity<?>
     */
    @GetMapping("/auth/logout")
    public ResponseEntity<?> logout(HttpServletRequest httpServletRequest) {
        logger.info(getMessageStart(httpServletRequest.getRequestURL().toString(), "logout"));
        // Execute Logout
        ApiResponse<String> apiResponse = new ApiResponse<>("Logout successfully");
        logger.info(getMessageEnd(httpServletRequest.getRequestURL().toString(), "logout"));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
