package hcmute.nhom.kltn.controller.apiv1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationRestController {

    @GetMapping("/login")
    public ResponseEntity<?> Login(@RequestParam("userName") String userName,
                                   HttpServletRequest request, HttpServletResponse response, HttpSession session){
        return ResponseEntity.ok(String.format("Login Success with userName: %s", userName));
    }
}
