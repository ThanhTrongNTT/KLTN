package hcmute.nhom.kltn.controller.apiv1.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {
    public ResponseEntity<?> login(){
        return ResponseEntity.ok().body("Login Success!");
    }
}
