package hcmute.nhom.kltn.controller.apiv1;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import hcmute.nhom.kltn.common.payload.ApiResponse;
import hcmute.nhom.kltn.controller.AbstractController;
import hcmute.nhom.kltn.dto.CommentDTO;
import hcmute.nhom.kltn.dto.ReplyCommentDTO;
import hcmute.nhom.kltn.services.CommentService;
import hcmute.nhom.kltn.util.SessionConstants;

/**
 * Class CommentController.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@RestController
@RequiredArgsConstructor
public class CommentController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    private final CommentService commentService;

    /**
     * commentToPost.
     * @param httpServletRequest HttpServletRequest
     * @param session HttpSession
     * @param postId String
     * @param commentDTO CommentDTO
     * @return ResponseEntity<ApiResponse<CommentDTO>>
     */
    @PostMapping("post/{postId}/comment-to-post")
    public ResponseEntity<ApiResponse<CommentDTO>> commentToPost(
            HttpServletRequest httpServletRequest,
            HttpSession session,
            @PathVariable("postId") String postId,
            CommentDTO commentDTO
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "CommentToPost");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "CommentToPost");
        logger.info("{}", messageStart);
        String userName = (String) session.getAttribute(SessionConstants.USER_NAME);
        // Execute Comment to Post
        CommentDTO result = commentService.commentToPost(postId, commentDTO, userName);
        logger.info("{}", messageEnd);
        return ResponseEntity.ok().body(new ApiResponse<>(result, "Comment to post successfully"));
    }

    /**
     * likeComment.
     * @param httpServletRequest HttpServletRequest
     * @param session HttpSession
     * @param commentId String
     * @return ResponseEntity<ApiResponse<Boolean>>
     */
    @PostMapping("comment/{commentId}/like")
    public ResponseEntity<ApiResponse<Boolean>> likeComment(
            HttpServletRequest httpServletRequest,
            HttpSession session,
            @PathVariable("commentId") String commentId
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "LikeComment");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "LikeComment");
        String like = "like";
        String unlike = "unlike";
        logger.info("{}", messageStart);
        String userName = (String) session.getAttribute(SessionConstants.USER_NAME);
        // Execute Like Comment
        Map<String, Boolean> result = commentService.likeComment(commentId, userName);
        String messageLike = Objects.nonNull(result.get(like)) && Boolean.TRUE.equals(result.get(like))
                ? "Like post successfully" : null;
        String messageUnlike = Objects.nonNull(result.get(unlike)) && Boolean.TRUE.equals(result.get(unlike))
                ? "Unlike post successfully" : null;
        logger.info("{}", messageEnd);
        return ResponseEntity.ok()
                .body(new ApiResponse<>(Objects.nonNull(result.get(like)) ? result.get(like) : result.get(unlike),
                        Objects.nonNull(messageLike) ? messageLike : messageUnlike));
    }

    /**
     * getCommentById.
     * @param httpServletRequest HttpServletRequest
     * @param session HttpSession
     * @param commentId String
     * @return ResponseEntity<ApiResponse<CommentDTO>>
     */
    @GetMapping("comments/{commentId}")
    public ResponseEntity<ApiResponse<CommentDTO>> getCommentById(
            HttpServletRequest httpServletRequest,
            HttpSession session,
            @PathVariable("commentId") String commentId
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "GetCommentById");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "GetCommentById");
        logger.info("{}", messageStart);
        // Execute Like Comment
        CommentDTO result = commentService.findById(commentId);
        logger.info("{}", messageEnd);
        return ResponseEntity.ok()
                .body(new ApiResponse<>(result, "Get comment successfully"));
    }

    /**
     * getCommentByPost.
     * @param httpServletRequest HttpServletRequest
     * @param session HttpSession
     * @param postId String
     * @return ResponseEntity<ApiResponse<List<CommentDTO>>>
     */
    @GetMapping("posts/{postId}/comments")
    public ResponseEntity<ApiResponse<List<CommentDTO>>> getCommentByPost(
            HttpServletRequest httpServletRequest,
            HttpSession session,
            @PathVariable("postId") String postId
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "GetCommentByPost");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "GetCommentByPost");
        logger.info("{}", messageStart);
        // Execute Like Comment
        List<CommentDTO> result = commentService.getCommentByPost(postId);
        logger.info("{}", messageEnd);
        return ResponseEntity.ok()
                .body(new ApiResponse<>(result, "Get comment successfully"));
    }

    /**
     * replyComment.
     * @param httpServletRequest HttpServletRequest
     * @param session HttpSession
     * @param commentId String
     * @param replyCommentDTO ReplyCommentDTO
     * @return ResponseEntity<ApiResponse<CommentDTO>>
     */
    @PostMapping("comments/{commentId}/reply")
    public ResponseEntity<ApiResponse<ReplyCommentDTO>> replyComment(
            HttpServletRequest httpServletRequest,
            HttpSession session,
            @PathVariable("commentId") String commentId,
            @RequestBody ReplyCommentDTO replyCommentDTO
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "ReplyComment");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "ReplyComment");
        logger.info("{}", messageStart);
        String userName = (String) session.getAttribute(SessionConstants.USER_NAME);
        // Execute Comment to Post
        ReplyCommentDTO result = commentService.replyComment(commentId, replyCommentDTO, userName);
        logger.info("{}", messageEnd);
        return ResponseEntity.ok().body(new ApiResponse<>(result, "Reply comment successfully"));
    }

    /**
     * getReplyCommentByComment.
     * @param httpServletRequest HttpServletRequest
     * @param session HttpSession
     * @param commentId String
     * @param commentDTO CommentDTO
     * @return ResponseEntity<ApiResponse<CommentDTO>>
     */
    @PostMapping("comments/{commentId}/edit")
    public ResponseEntity<ApiResponse<CommentDTO>> editComment(
            HttpServletRequest httpServletRequest,
            HttpSession session,
            @PathVariable("commentId") String commentId,
            @RequestBody CommentDTO commentDTO
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "EditComment");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "EditComment");
        logger.info("{}", messageStart);
        String userName = (String) session.getAttribute(SessionConstants.USER_NAME);
        // Execute Edit Comment
        CommentDTO result = commentService.editComment(commentId, commentDTO, userName);
        logger.info("{}", messageEnd);
        return ResponseEntity.ok().body(new ApiResponse<>(result, "Edit comment successfully"));
    }

    /**
     * getReplyCommentByComment.
     * @param httpServletRequest HttpServletRequest
     * @param session HttpSession
     * @param replyId String
     * @param replyCommentDTO ReplyCommentDTO
     * @return ResponseEntity<ApiResponse<CommentDTO>>
     */
    @PostMapping("reply/{replyId}/edit")
    public ResponseEntity<ApiResponse<ReplyCommentDTO>> editReplyComment(
            HttpServletRequest httpServletRequest,
            HttpSession session,
            @PathVariable("replyId") String replyId,
            @RequestBody ReplyCommentDTO replyCommentDTO
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "EditComment");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "EditComment");
        logger.info("{}", messageStart);
        String userName = (String) session.getAttribute(SessionConstants.USER_NAME);
        // Execute Edit Comment
        ReplyCommentDTO result = commentService.editReply(replyId, replyCommentDTO, userName);
        logger.info("{}", messageEnd);
        return ResponseEntity.ok().body(new ApiResponse<>(result, "Edit comment successfully"));
    }

    /**
     * Delete Comment.
     * @param httpServletRequest HttpServletRequest
     * @param session HttpSession
     * @param commentId String
     * @return ResponseEntity<ApiResponse<Boolean>>
     */
    @DeleteMapping("comments/{commentId}/delete")
    public ResponseEntity<ApiResponse<Boolean>> deleteComment(
            HttpServletRequest httpServletRequest,
            HttpSession session,
            @PathVariable("commentId") String commentId
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "DeleteComment");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "DeleteComment");
        logger.info("{}", messageStart);
        String userName = (String) session.getAttribute(SessionConstants.USER_NAME);
        // Execute Like Comment
        Boolean result = commentService.deleteComment(commentId, userName);
        String message = result ? "Delete comment successfully" : "Delete comment failed";
        return ResponseEntity.ok()
                .body(new ApiResponse<>(result, message));
    }
}
