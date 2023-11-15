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
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import hcmute.nhom.kltn.common.payload.ApiResponse;
import hcmute.nhom.kltn.common.payload.NewPostRequest;
import hcmute.nhom.kltn.controller.AbstractController;
import hcmute.nhom.kltn.dto.PostDTO;
import hcmute.nhom.kltn.services.PostService;
import hcmute.nhom.kltn.util.Constants;
import hcmute.nhom.kltn.util.SessionConstants;
import hcmute.nhom.kltn.util.Utilities;

/**
 * Class PostController.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@RestController
@RequiredArgsConstructor
public class PostController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    private final PostService postService;

    /**
     * Get all post.
     *
     * @param httpServletRequest HttpServletRequest
     * @param pageNo             int
     * @param pageSize           int
     * @param sortBy             String
     * @param sortDir            String
     * @return ResponseEntity<ApiResponse<Page<PostDTO>>>
     */
    @GetMapping("/posts")
    public ResponseEntity<ApiResponse<Page<PostDTO>>> getAllPost(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "pageNo", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false)
            int pageNo,
            @RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false)
            int pageSize,
            @RequestParam(value = "sortBy", defaultValue = Constants.DEFAULT_SORT_BY, required = false)
            String sortBy,
            @RequestParam(value = "sortDir", defaultValue = Constants.DEFAULT_SORT_DIRECTION, required = false)
            String sortDir
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "getAllPost");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "getAllPost");
        logger.info("{}", messageStart);
        // Execute getAllPost
        Page<PostDTO> posts = postService.getAllPost(pageNo, pageSize, sortBy, sortDir);
        logger.info("{}", messageEnd);
        return ResponseEntity.ok().body(new ApiResponse<>(posts, "Get all post successfully"));
    }

    /**
     * Search post.
     *
     * @param httpServletRequest HttpServletRequest
     * @param pageNo             int
     * @param pageSize           int
     * @param sortBy             String
     * @param sortDir            String
     * @return ResponseEntity<ApiResponse<Page<PostDTO>>>
     */
    @GetMapping("/posts/search")
    public ResponseEntity<ApiResponse<Page<PostDTO>>> searchPost(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "pageNo", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false)
            int pageNo,
            @RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false)
            int pageSize,
            @RequestParam(value = "sortBy", defaultValue = Constants.DEFAULT_SORT_BY, required = false)
            String sortBy,
            @RequestParam(value = "sortDir", defaultValue = Constants.DEFAULT_SORT_DIRECTION, required = false)
            String sortDir,
            @RequestBody PostDTO postDTO
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "searchPost");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "searchPost");
        logger.info("{}", messageStart);
        // Execute searchPost
        Page<PostDTO> posts = postService.searchPost(postDTO,
                Utilities.getPageRequest(pageNo, pageSize, sortBy, sortDir));
        logger.info("{}", messageEnd);
        return ResponseEntity.ok().body(new ApiResponse<>(posts, "Search post successfully"));
    }

    /**
     * Get post by user.
     *
     * @param httpServletRequest HttpServletRequest
     * @param pageNo             int
     * @param pageSize           int
     * @param sortBy             String
     * @param sortDir            String
     * @return ResponseEntity<ApiResponse<Page<PostDTO>>>
     */
    @GetMapping("{userName}/posts")
    public ResponseEntity<ApiResponse<Page<PostDTO>>> getPostsByUser(
            HttpServletRequest httpServletRequest,
            @PathVariable("userName") String userName,
            @RequestParam(value = "pageNo", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false)
            int pageNo,
            @RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false)
            int pageSize,
            @RequestParam(value = "sortBy", defaultValue = Constants.DEFAULT_SORT_BY, required = false)
            String sortBy,
            @RequestParam(value = "sortDir", defaultValue = Constants.DEFAULT_SORT_DIRECTION, required = false)
            String sortDir
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "getPostsByUser");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "getPostsByUser");
        logger.info("{}", messageStart);
        // Execute getAllPost
        Page<PostDTO> posts = postService.getPostByUser(pageNo, pageSize, sortBy, sortDir, userName);
        logger.info("{}", messageEnd);
        return ResponseEntity.ok().body(new ApiResponse<>(posts, "Get all post successfully"));
    }

    /**
     * Get post by user.
     * @param httpServletRequest HttpServletRequest
     * @param id                UUID
     * @param userName         String
     * @return ResponseEntity<ApiResponse<PostDTO>>
     */
    @GetMapping("{userName}/posts/{id}")
    public ResponseEntity<ApiResponse<PostDTO>> getPostByUser(
            HttpServletRequest httpServletRequest,
            @PathVariable("id") String id,
            @PathVariable("userName") String userName
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "getPostByUser");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "getPostByUser");
        // Execute get one post by user
        logger.info("{}", messageStart);
        PostDTO postDTO = postService.getPostByUserAndId(UUID.fromString(id), userName);
        logger.info("{}", messageEnd);
        return ResponseEntity.ok().body(new ApiResponse<>(postDTO, "Get post successfully"));
    }

    /**
     * Create post.
     * @param httpServletRequest HttpServletRequest
     * @param newPostRequest           NewPostRequest
     * @return ResponseEntity<ApiResponse<PostDTO>>
     */
    @PostMapping("/post")
    public ResponseEntity<ApiResponse<PostDTO>> createPost(
            HttpServletRequest httpServletRequest, @RequestBody NewPostRequest newPostRequest
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "createPost");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "createPost");
        logger.info("{}", messageStart);
        // Execute getAllPost
        PostDTO savePost = postService.createPost(newPostRequest);
        logger.info("{}", messageEnd);
        return ResponseEntity.ok().body(new ApiResponse<>(savePost, "Create post successfully"));
    }

    /**
     * Update post.
     * @param httpServletRequest HttpServletRequest
     * @param postDTO          PostDTO
     * @return ResponseEntity<ApiResponse<PostDTO>>
     */
    @PutMapping("/{userName}/post/{id}")
    public ResponseEntity<ApiResponse<PostDTO>> updatePost(
            HttpServletRequest httpServletRequest,
            HttpSession session,
            @PathVariable("id") String id,
            @PathVariable("userName") String userName,
            @RequestBody PostDTO postDTO
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "updatePost");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "updatePost");
        if (Objects.equals(userName, session.getAttribute(SessionConstants.USER_NAME))) {
            logger.info("{}", messageStart);
            // Execute getAllPost
            PostDTO updatePost = postService.updatePost(UUID.fromString(id), postDTO);
            logger.info("{}", messageEnd);
            return ResponseEntity.ok().body(new ApiResponse<>(updatePost, "Update post successfully"));
        } else {
            logger.info("{}", messageEnd);
            return ResponseEntity.ok().body(new ApiResponse<>(null, "Update post failed"));
        }
    }

    /**
     * Delete post.
     * @param httpServletRequest HttpServletRequest
     * @param id                UUID
     * @return ResponseEntity<ApiResponse<Boolean>>
     */
    @DeleteMapping("/post/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deletePost(
            HttpServletRequest httpServletRequest,
            HttpSession session,
            @PathVariable("id") String id) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "deletePost");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "deletePost");
        logger.info("{}", messageStart);
        // Execute getAllPost
        String userName = session.getAttribute(SessionConstants.USER_NAME).toString();
        postService.deletePost(UUID.fromString(id), userName);
        logger.info("{}", messageEnd);
        return null;
    }

    /**
     * Get friend's posts.
     * @param httpServletRequest HttpServletRequest
     * @param session           HttpSession
     * @return ResponseEntity<ApiResponse<List<PostDTO>>>
     */
    @GetMapping("posts/friend")
    public ResponseEntity<ApiResponse<List<PostDTO>>> getFriendPosts(
            HttpServletRequest httpServletRequest,
            HttpSession session
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "getFriendPosts");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "getFriendPosts");
        logger.info("{}", messageStart);
        // Execute get Friend's Posts
        String userName = session.getAttribute(SessionConstants.USER_NAME).toString();
        List<PostDTO> posts = postService.getFriendPosts(userName);
        logger.info("{}", messageEnd);
        return ResponseEntity.ok().body(new ApiResponse<>(posts, "Get friend's posts successfully"));
    }

    /**
     * Like post.
     * @param httpServletRequest HttpServletRequest
     * @param session          HttpSession
     * @param id              UUID
     * @return ResponseEntity<ApiResponse<?>>
     */
    @PostMapping("/post/{id}/like")
    public ResponseEntity<ApiResponse<Boolean>> likePost(
            HttpServletRequest httpServletRequest,
            HttpSession session,
            @PathVariable("id") String id
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "likePost");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "likePost");
        String like = "like";
        String unlike = "unlike";
        logger.info("{}", messageStart);
        // Execute like post
        String userName = session.getAttribute(SessionConstants.USER_NAME).toString();
        Map<String, Boolean> result = postService.likePost(UUID.fromString(id), userName);
        String messageLike = Objects.nonNull(result.get(like)) && Boolean.TRUE.equals(result.get(like))
                ? "Like post successfully" : null;
        String messageUnlike = Objects.nonNull(result.get(unlike)) && Boolean.TRUE.equals(result.get(unlike))
                ? "Unlike post successfully" : null;
        logger.info("{}", messageEnd);
        return ResponseEntity.ok()
                .body(new ApiResponse<>(Objects.nonNull(result.get(like)) ? result.get(like) : result.get(unlike),
                        Objects.nonNull(messageLike) ? messageLike : messageUnlike));
    }
}
