package hcmute.nhom.kltn;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class HomeController {

    @GetMapping("/home")
    public ResponseEntity<?> HomeApi() {
        return ResponseEntity.ok("Hello World!");
    }
}
