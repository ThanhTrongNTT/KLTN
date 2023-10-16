package hcmute.nhom.kltn.security.jwt;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import hcmute.nhom.kltn.common.AbstractMessage;
import hcmute.nhom.kltn.controller.apiv1.HomeController;

/**
 * Class JwtEntryPoint.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Component
public class JwtEntryPoint extends AbstractMessage implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        logger.error(getMessage("common.error.unAuthorized", new String[]{authException.getMessage()}));
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized");
    }
}
