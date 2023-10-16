package hcmute.nhom.kltn.controller.apiv1;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import hcmute.nhom.kltn.common.AbstractMessage;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController extends AbstractMessage {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/home")
    public String home(HttpServletRequest request) {
        logger.info(getMessageStart(request.getRequestURL().toString(), "Home Controller"));
        logger.info(getMessageEnd(request.getRequestURL().toString(), "Home Controller"));
        return "Welcome to home page";
    }
}
