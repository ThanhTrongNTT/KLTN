package hcmute.nhom.kltn.controller.apiv1;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import hcmute.nhom.kltn.common.payload.ApiResponse;
import hcmute.nhom.kltn.common.payload.ListResponse;
import hcmute.nhom.kltn.controller.AbstractController;
import hcmute.nhom.kltn.dto.FriendDTO;
import hcmute.nhom.kltn.services.FriendService;
import hcmute.nhom.kltn.util.SessionConstants;

/**
 * Class FriendController.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@RestController
@RequiredArgsConstructor
public class FriendController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(FriendController.class);
    private final FriendService friendService;

    /**
     * Get list friend.
     * @param httpServletRequest HttpServletRequest
     * @param session            HttpSession
     * @param userName          User name
     * @return ResponseEntity<ApiResponse<FriendDTO>>
     */
    @PostMapping("friend/add-friend/{userName}")
    public ResponseEntity<ApiResponse<FriendDTO>> addFriend(
            HttpServletRequest httpServletRequest,
            HttpSession session,
            @PathVariable("userName") String userName
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "addFriend");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "addFriend");
        logger.info("{}", messageStart);
        String userNameSession = session.getAttribute(SessionConstants.USER_NAME).toString();
        // Execute addFriend
        FriendDTO friendDTO = friendService.addFriend(userNameSession, userName);
        logger.info("{}", messageEnd);
        return new ResponseEntity<>(new ApiResponse<>(friendDTO, "Add friend successfully"), HttpStatus.OK);
    }

    /**
     * Accept friend.
     * @param httpServletRequest HttpServletRequest
     * @param session           HttpSession
     * @param id                String
     * @return ResponseEntity<ApiResponse<Boolean>>
     */
    @PostMapping("friend/{id}/accept-friend")
    public ResponseEntity<ApiResponse<Boolean>> acceptFriend(
            HttpServletRequest httpServletRequest,
            HttpSession session,
            @PathVariable String id
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "rejectFriend");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "rejectFriend");
        logger.info("{}", messageStart);
        String userName = session.getAttribute(SessionConstants.USER_NAME).toString();
        // Execute addFriend
        Boolean accepted = friendService.acceptFriend(userName, id);
        String message = Boolean.TRUE.equals(accepted) ? "Reject friend successfully" : "Reject friend failed";
        logger.info("{}", messageEnd);
        return new ResponseEntity<>(new ApiResponse<>(accepted, message), HttpStatus.OK);
    }

    /**
     * Reject friend.
     * @param httpServletRequest HttpServletRequest
     * @param session           HttpSession
     * @param id                String
     * @return ResponseEntity<ApiResponse<Boolean>>
     */
    @PostMapping("friend/{id}/reject-friend")
    public ResponseEntity<ApiResponse<Boolean>> rejectFriend(
            HttpServletRequest httpServletRequest,
            HttpSession session,
            @PathVariable String id
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "rejectFriend");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "rejectFriend");
        logger.info("{}", messageStart);
        String userName = session.getAttribute(SessionConstants.USER_NAME).toString();
        // Execute addFriend
        Boolean reject = friendService.rejectFriend(userName, id);
        String message = Boolean.TRUE.equals(reject) ? "Reject friend successfully" : "Reject friend failed";
        logger.info("{}", messageEnd);
        return new ResponseEntity<>(new ApiResponse<>(reject, message), HttpStatus.OK);
    }

    /**
     * Get list friend suggest.
     * @param httpServletRequest HttpServletRequest
     * @param session           HttpSession
     * @return ResponseEntity<ApiResponse<ListResponse<FriendDTO>>>
     */
    @GetMapping("friend/friend-suggest")
    public ResponseEntity<ApiResponse<ListResponse<FriendDTO>>> getSuggestFriend(
            HttpServletRequest httpServletRequest,
            HttpSession session
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "getSuggestFriend");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "getSuggestFriend");
        logger.info("{}", messageStart);
        String userName = session.getAttribute(SessionConstants.USER_NAME).toString();
        // Execute getSuggestFriend
        ListResponse<FriendDTO> friendDTOListResponse = friendService.getSuggestFriend(userName);
        logger.info("{}", messageEnd);
        return ResponseEntity.ok(new ApiResponse<>(friendDTOListResponse, "Get list friend suggest successfully"));
    }

    /**
     * Get Friends Receive.
     * @param httpServletRequest HttpServletRequest
     * @param session HttpSession
     * @return ResponseEntity<ApiResponse<ListResponse<FriendDTO>>>
     */
    @GetMapping("friend/friends-receive")
    public ResponseEntity<ApiResponse<ListResponse<FriendDTO>>> getFriendReceive(
            HttpServletRequest httpServletRequest,
            HttpSession session
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "getFriendReceive");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "getFriendReceive");
        logger.info("{}", messageStart);
        String userName = session.getAttribute(SessionConstants.USER_NAME).toString();
        // Execute getFriendRequest
        ListResponse<FriendDTO> friendDTOListResponse = friendService.getFriendReceive(userName);
        logger.info("{}", messageEnd);
        return new ResponseEntity<>(new ApiResponse<>(friendDTOListResponse, "Get Friends Request success!"),
                HttpStatus.OK);
    }

    /**
     * Get Friends Request.
     * @param httpServletRequest HttpServletRequest
     * @param session HttpSession
     * @return ResponseEntity<ApiResponse<ListResponse<FriendDTO>>>
     */
    @GetMapping("friend/friends-request")
    public ResponseEntity<ApiResponse<ListResponse<FriendDTO>>> getFriendRequest(
            HttpServletRequest httpServletRequest,
            HttpSession session
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "getFriendRequest");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "getFriendRequest");
        logger.info("{}", messageStart);
        String userName = session.getAttribute(SessionConstants.USER_NAME).toString();
        // Execute getFriendRequest
        ListResponse<FriendDTO> friendDTOListResponse = friendService.getFriendRequest(userName);
        logger.info("{}", messageEnd);
        return new ResponseEntity<>(new ApiResponse<>(friendDTOListResponse, "Get Friends Request success!"),
                HttpStatus.OK);
    }

    /**
     * Get Friends.
     * @param httpServletRequest HttpServletRequest
     * @param session HttpSession
     * @param userName String
     * @return ResponseEntity<ApiResponse<Boolean>>
     */
    @DeleteMapping("friend/{userName}/un-friend")
    public ResponseEntity<ApiResponse<Boolean>> unFriend(
            HttpServletRequest httpServletRequest,
            HttpSession session,
            @PathVariable String userName
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "unFriend");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "unFriend");
        logger.info("{}", messageStart);
        String userNameSession = session.getAttribute(SessionConstants.USER_NAME).toString();
        // Execute addFriend
        Boolean unFriend = friendService.unFriend(userNameSession, userName);
        String message = Boolean.TRUE.equals(unFriend) ? "Unfriend successfully" : "Unfriend failed";
        logger.info("{}", messageEnd);
        return new ResponseEntity<>(new ApiResponse<>(unFriend, unFriend, message), HttpStatus.OK);
    }

    /**
     * Block Friend.
     * @param httpServletRequest HttpServletRequest
     * @param session HttpSession
     * @param userName String
     * @return ResponseEntity<ApiResponse<Boolean>>
     */
    @PostMapping("friend/{userName}/block-friend")
    public ResponseEntity<ApiResponse<Boolean>> blockFriend(
            HttpServletRequest httpServletRequest,
            HttpSession session,
            @PathVariable String userName
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "blockFriend");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "blockFriend");
        logger.info("{}", messageStart);
        String userNameSession = session.getAttribute(SessionConstants.USER_NAME).toString();
        // Execute addFriend
        Boolean blockFriend = friendService.blockFriend(userNameSession, userName);
        String message = Boolean.TRUE.equals(blockFriend) ? "Block friend successfully" : "Block friend failed";
        logger.info("{}", messageEnd);
        return new ResponseEntity<>(new ApiResponse<>(blockFriend, blockFriend, message), HttpStatus.OK);
    }

    /**
     * Un Block Friend.
     * @param httpServletRequest HttpServletRequest
     * @param session HttpSession
     * @param userName String
     * @return ResponseEntity<ApiResponse<Boolean>>
     */
    @PostMapping("friend/{userName}/unblock-friend")
    public ResponseEntity<ApiResponse<Boolean>> unBlockFriend(
            HttpServletRequest httpServletRequest,
            HttpSession session,
            @PathVariable String userName
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "unBlockFriend");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "unBlockFriend");
        logger.info("{}", messageStart);
        String userNameSession = session.getAttribute(SessionConstants.USER_NAME).toString();
        // Execute addFriend
        Boolean unBlockFriend = friendService.unBlockFriend(userNameSession, userName);
        String message = Boolean.TRUE.equals(unBlockFriend) ? "Unblock friend successfully" : "Unblock friend failed";
        logger.info("{}", messageEnd);
        return new ResponseEntity<>(new ApiResponse<>(unBlockFriend, unBlockFriend, message), HttpStatus.OK);
    }

    /**
     * Get Block Friend.
     * @param httpServletRequest HttpServletRequest
     * @param session HttpSession
     * @return ResponseEntity<ApiResponse<ListResponse<FriendDTO>>>
     */
    @GetMapping("friend/block-friends")
    public ResponseEntity<ApiResponse<ListResponse<FriendDTO>>> getBlockFriend(
            HttpServletRequest httpServletRequest,
            HttpSession session
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "getBlockFriend");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "getBlockFriend");
        logger.info("{}", messageStart);
        String userName = session.getAttribute(SessionConstants.USER_NAME).toString();
        // Execute getFriendRequest
        ListResponse<FriendDTO> friendDTOListResponse = friendService.getBlockFriend(userName);
        logger.info("{}", messageEnd);
        return new ResponseEntity<>(new ApiResponse<>(friendDTOListResponse, "Get Friends Request success!"),
                HttpStatus.OK);
    }
}
