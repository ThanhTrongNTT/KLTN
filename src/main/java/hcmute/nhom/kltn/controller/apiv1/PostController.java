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
import org.springframework.http.HttpStatus;
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
import hcmute.nhom.kltn.common.payload.ListResponse;
import hcmute.nhom.kltn.common.payload.PagingResponseCustom;
import hcmute.nhom.kltn.controller.AbstractController;
import hcmute.nhom.kltn.dto.PostDTO;
import hcmute.nhom.kltn.services.PostService;
import hcmute.nhom.kltn.util.Constants;
import hcmute.nhom.kltn.util.SessionConstants;

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
    @GetMapping("posts/paging")
    public ResponseEntity<ApiResponse<PagingResponseCustom<PostDTO>>> getAllPostPaging(
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
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "getAllPostPaging");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "getAllPostPaging");
        logger.info("{}", messageStart);
        // Execute getAllPost
        PagingResponseCustom<PostDTO> posts = postService.getAllPostPaging(pageNo, pageSize, sortBy, sortDir);
        logger.info("{}", messageEnd);
        return ResponseEntity.ok().body(new ApiResponse<>(posts,
                "Get all post paging successfully"));
    }

    /**
     * Get all post.
     * @param httpServletRequest HttpServletRequest
     * @return ResponseEntity<ApiResponse<List<PostDTO>>>
     */
    @GetMapping("posts")
    public ResponseEntity<ApiResponse<ListResponse<PostDTO>>> getAllPost(
            HttpServletRequest httpServletRequest
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "getAllPost");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "getAllPost");
        logger.info("{}", messageStart);
        // Execute getAllPost
        ListResponse<PostDTO> posts = postService.getAllPost();
        logger.info("{}", messageEnd);
        return ResponseEntity.ok().body(new ApiResponse<>(posts, "Get all post successfully"));
    }

    /**
     * Search post.
     *
     * @param httpServletRequest HttpServletRequest
     * @return ResponseEntity<ApiResponse<Page<PostDTO>>>
     */
    @GetMapping("posts/search")
    public ResponseEntity<ApiResponse<ListResponse<PostDTO>>> searchPost(
            HttpServletRequest httpServletRequest,
            @RequestBody PostDTO postDTO
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "searchPost");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "searchPost");
        logger.info("{}", messageStart);
        // Execute searchPost
        ListResponse<PostDTO> posts = postService.searchPost(postDTO);
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
    @GetMapping("posts/{userName}")
    public ResponseEntity<ApiResponse<PagingResponseCustom<PostDTO>>> getPostsByUser(
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
        PagingResponseCustom<PostDTO> posts =
                postService.getPostByUserPaging(pageNo, pageSize, sortBy, sortDir, userName);
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
    @GetMapping("posts/{userName}/{id}")
    public ResponseEntity<ApiResponse<PostDTO>> getPostByUser(
            HttpServletRequest httpServletRequest,
            @PathVariable("id") String id,
            @PathVariable("userName") String userName
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "getPostByUser");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "getPostByUser");
        // Execute get one post by user
        logger.info("{}", messageStart);
        PostDTO postDTO = postService.getPostByUserAndId(id, userName);
        logger.info("{}", messageEnd);
        return ResponseEntity.ok().body(new ApiResponse<>(postDTO, "Get post successfully"));
    }

    /**
     * Create post.
     * @param httpServletRequest HttpServletRequest
     * @param postDTO           PostDTO
     * @return ResponseEntity<ApiResponse<PostDTO>>
     */
    @PostMapping("posts/create")
    public ResponseEntity<ApiResponse<PostDTO>> createPost(
            HttpServletRequest httpServletRequest,
            HttpSession session,
            @RequestBody PostDTO postDTO
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "createPost");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "createPost");
        logger.info("{}", messageStart);
        try {
            String email = session.getAttribute(SessionConstants.USER_EMAIL).toString();
            // Execute getAllPost
            PostDTO savePost = postService.createPost(postDTO, email);
            logger.info("{}", messageEnd);
            return ResponseEntity.ok().body(new ApiResponse<>(savePost, "Create post successfully"));
        } catch (Exception e) {
            if (!Objects.isNull(e.getMessage())) {
                logger.info("{}", messageEnd);
                return new ResponseEntity<>(new ApiResponse<>(false, null, e.getMessage()), HttpStatus.BAD_REQUEST);
            }
            logger.info("{}", messageEnd);
            return new ResponseEntity<>(new ApiResponse<>(false, null, "Create Post Failed!"), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update post.
     * @param httpServletRequest HttpServletRequest
     * @param postDTO          PostDTO
     * @return ResponseEntity<ApiResponse<PostDTO>>
     */
    @PutMapping("post/{userName}/{id}")
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
            PostDTO updatePost = postService.updatePost(id, postDTO);
            logger.info("{}", messageEnd);
            return ResponseEntity.ok().body(new ApiResponse<>(updatePost, "Update post successfully"));
        } else {
            logger.info("{}", messageEnd);
            return new ResponseEntity<>(new ApiResponse<>(false, null, "Update post failed"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Delete post.
     * @param httpServletRequest HttpServletRequest
     * @param id                UUID
     * @return ResponseEntity<ApiResponse<Boolean>>
     */
    @DeleteMapping("posts/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deletePost(
            HttpServletRequest httpServletRequest,
            HttpSession session,
            @PathVariable("id") String id) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "deletePost");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "deletePost");
        logger.info("{}", messageStart);
        // Execute getAllPost
        String userName = session.getAttribute(SessionConstants.USER_NAME).toString();
        Boolean isDelete = postService.deletePost(id, userName);
        String message = Boolean.TRUE.equals(isDelete) ? "Delete post successfully" : "Delete post failed";
        logger.info("{}", messageEnd);
        return ResponseEntity.ok().body(new ApiResponse<>(isDelete, message));
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
     * @param id              String
     * @return ResponseEntity<ApiResponse<?>>
     */
    @PostMapping("posts/{id}/like")
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
        Map<String, Boolean> result = postService.likePost(id, userName);
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
     * Get post by id.
     * @param httpServletRequest HttpServletRequest
     * @param id               String
     * @return ResponseEntity<ApiResponse<PostDTO>>
     */
    @GetMapping("posts/{id}")
    public ResponseEntity<ApiResponse<PostDTO>> getPostById(
            HttpServletRequest httpServletRequest,
            @PathVariable("id") String id
    ) {
        String messageStart = getMessageStart(httpServletRequest.getRequestURL().toString(), "getPostById");
        String messageEnd = getMessageEnd(httpServletRequest.getRequestURL().toString(), "getPostById");
        logger.info("{}", messageStart);
        // Execute get post by id
        PostDTO postDTO = postService.getPostById(id);
        logger.info("{}", messageEnd);
        return ResponseEntity.ok().body(new ApiResponse<>(postDTO, "Get post successfully"));
    }
}
