package hcmute.nhom.kltn.controller.apiv1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {
    @GetMapping("/get")
    public ResponseEntity<?> helloWorld() {
        return ResponseEntity.ok().body(new String("Hello world!"));
    }
}
