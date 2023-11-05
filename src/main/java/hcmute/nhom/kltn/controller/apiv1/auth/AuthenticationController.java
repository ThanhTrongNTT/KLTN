package hcmute.nhom.kltn.controller.apiv1.auth;

import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import hcmute.nhom.kltn.common.payload.JwtAuthenticationResponse;
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
@RequiredArgsConstructor
public class AuthenticationController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    /**
     * Register User
     * @param httpServletRequest HttpServletRequest
     * @param userDTO userDTO
     * @return ResponseEntity<?>
     */
    @PostMapping("/auth/register")
    public ResponseEntity<ApiResponse<UserDTO>> registerUser(HttpServletRequest httpServletRequest,
                                                    @RequestBody UserDTO userDTO) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "registerUser");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "registerUser");
        logger.info("{}", messageStart);
        // Execute RegisterUser
        Role userRole = roleService.findByName(RoleName.USER.name());
        Set<Role> roleDTOS = new HashSet<>();
        roleDTOS.add(userRole);
        userDTO.setGender(GenderType.OTHER.name());
        userDTO.setRoles(roleDTOS);
        userDTO = userService.save(userDTO);
        logger.info("{}", messageEnd);
        return ResponseEntity.ok().body(new ApiResponse<>(userDTO, "User registered successfully"));
    }

    /**
     * Login User.
     * @param httpServletRequest HttpServletRequest
     * @param loginRequest      LoginRequest
     * @return ResponseEntity<?>
     */
    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponse<JwtAuthenticationResponse>> login(HttpServletRequest httpServletRequest,
                                                                        @Valid @RequestBody LoginRequest loginRequest) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "loginUser");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "loginUser");
        logger.info("{}", messageStart);
        // Execute Login
        UserDTO userDTO = userService.findByEmail(loginRequest.getEmail());
        if (userDTO == null) {
            logger.info("{}", messageEnd);
            return new ResponseEntity<>(new ApiResponse<>(null, "Email not found"),
                    HttpStatus.BAD_REQUEST);
        }
        if (!passwordEncoder.matches(loginRequest.getPassword(), userDTO.getPassword())) {
            logger.info("{}", messageEnd);
            return new ResponseEntity<>(new ApiResponse<>(null, "The password was wrong!"), HttpStatus.BAD_REQUEST);
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        logger.info("Creating Json Web Token!!");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        logger.info("{}", messageEnd);
        return ResponseEntity.ok().body(new ApiResponse<>(jwtProvider.createToken(authentication),
                "Login successfully!!"));
    }

    /**
     * Forgot Password.
     * @param httpServletRequest HttpServletRequest
     * @param forgotPasswordRequest ForgotPasswordRequest
     * @return ResponseEntity<?>
     */
    @PostMapping("/auth/forgot-password")
    public ResponseEntity<ApiResponse<String>> forgotPassword(HttpServletRequest httpServletRequest,
                                            @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "forgotPassword");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "forgotPassword");
        logger.info("{}", messageStart);
        // Execute ForgotPassword
        logger.debug("{}", forgotPasswordRequest);
        logger.info("{}", messageEnd);
        return ResponseEntity.ok().body(new ApiResponse<>("Love you", "Forgot password successfully"));
    }

    /**
     * Logout.
     * @param httpServletRequest HttpServletRequest
     * @return ResponseEntity<?>
     */
    @GetMapping("/auth/logout")
    public ResponseEntity<ApiResponse<String>> logout(HttpServletRequest httpServletRequest) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "logout");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "logout");
        logger.info("{}", messageStart);
        // Execute Logout
        ApiResponse<String> apiResponse = new ApiResponse<>("Logout successfully");
        logger.info("{}", messageEnd);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
