package hcmute.nhom.kltn.controller.apiv1;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import hcmute.nhom.kltn.common.AbstractMessage;

/**
 * Class HomeController.
 *
 * @version: 1.0.0
 * @author: ThanhTrong
 * @function_id:
 */
@RestController
@RequestMapping("/api/v1/home")
public class HomeController extends AbstractMessage {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    /**
     * Home controller.
     * @param request HttpServletRequest
     * @return String
     */
    @GetMapping("/home")
    public String home(HttpServletRequest request) {
        String messageStart = getMessageStart(request.getRequestURL().toString(), "Home Controller");
        String messageEnd = getMessageEnd(request.getRequestURL().toString(), "Home Controller");
        logger.info("{}", messageStart);
        logger.info("{}", messageEnd);
        return "Welcome to home page";
    }
}
