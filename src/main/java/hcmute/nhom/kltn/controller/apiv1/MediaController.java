package hcmute.nhom.kltn.controller.apiv1;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import hcmute.nhom.kltn.common.payload.ApiResponse;
import hcmute.nhom.kltn.common.payload.ListResponse;
import hcmute.nhom.kltn.controller.AbstractController;
import hcmute.nhom.kltn.dto.MediaFileDTO;
import hcmute.nhom.kltn.services.PostService;

/**
 * Class MediaController.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@RestController
@RequiredArgsConstructor
public class MediaController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(MediaController.class);

    private final PostService postService;

    /**
     * Get all media by userName.
     * @param httpServletRequest HttpServletRequest
     * @param userName         String
     * @return ResponseEntity<ApiResponse<List<PostDTO>>>
     */
    @GetMapping("media/{userName}")
    public ResponseEntity<ApiResponse<ListResponse<MediaFileDTO>>> getAllMediaByUserName(
            HttpServletRequest httpServletRequest,
            @PathVariable("userName") String userName
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "getAllMediaByUserName");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "getAllMediaByUserName");
        logger.info("{}", messageStart);
        // Execute getAllPost
        ListResponse<MediaFileDTO> mediaFiles = postService.getAllMediaByUserName(userName);
        logger.info("{}", messageEnd);
        return ResponseEntity.ok().body(new ApiResponse<>(mediaFiles, "Get all media successfully"));
    }
}
