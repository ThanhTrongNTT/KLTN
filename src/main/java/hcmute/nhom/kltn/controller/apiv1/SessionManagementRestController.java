package hcmute.nhom.kltn.controller.apiv1;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import hcmute.nhom.kltn.common.AbstractMessage;
import hcmute.nhom.kltn.services.session.SessionManagementService;

/**
 * Class SessionManagementRestController.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/session-management")
public class SessionManagementRestController extends AbstractMessage {
    private static final Logger logger = LoggerFactory.getLogger(SessionManagementRestController.class);

    private final SessionManagementService sessionService;
}
