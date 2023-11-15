package hcmute.nhom.kltn.services.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import hcmute.nhom.kltn.common.payload.NewPostRequest;
import hcmute.nhom.kltn.dto.PostDTO;
import hcmute.nhom.kltn.dto.UserDTO;
import hcmute.nhom.kltn.exception.SystemErrorException;
import hcmute.nhom.kltn.mapper.PostMapper;
import hcmute.nhom.kltn.model.Post;
import hcmute.nhom.kltn.model.User;
import hcmute.nhom.kltn.repository.PostRepository;
import hcmute.nhom.kltn.services.PostService;
import hcmute.nhom.kltn.services.UserService;
import hcmute.nhom.kltn.util.Utilities;

/**
 * Class PostServiceImpl.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Service
@RequiredArgsConstructor
public class PostServiceImpl extends AbstractServiceImpl<PostRepository, PostMapper, PostDTO, Post>
        implements PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);
    private static final String METHOD = "PostService";
    private final PostRepository postRepository;
    private final UserService userService;

    /**
     * searchPostList.
     *
     * @param postDTO
     * @param pageable
     * @return
     */
    @Override
    public Page<PostDTO> searchPost(PostDTO postDTO, Pageable pageable) {
        return null;
    }

    /**
     * getAllPost.
     *
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @Override
    public Page<PostDTO> getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
        logger.info(getMessageStart(METHOD, "GetAllPost"));
        logger.debug(getMessageInputParam(METHOD, "pageNo", pageNo));
        logger.debug(getMessageInputParam(METHOD, "pageSize", pageSize));
        logger.debug(getMessageInputParam(METHOD, "sortBy", sortBy));
        logger.debug(getMessageInputParam(METHOD, "sortDir", sortDir));
        PageRequest pageRequest = Utilities.getPageRequest(pageNo, pageSize, sortBy, sortDir);
        Page<Post> posts = postRepository.findAll(pageRequest);
        logger.debug(getMessageOutputParam(METHOD, "posts", posts.getTotalElements()));
        logger.info(getMessageEnd(METHOD, "GetAllPost"));
        return posts.map(post -> getMapper().toDto(post, getCycleAvoidingMappingContext()));
    }

    @Override
    public Page<PostDTO> getPostByUser(int pageNo, int pageSize, String sortBy, String sortDir, String userName) {
        logger.info(getMessageStart(METHOD, "GetPostByUser"));
        logger.debug(getMessageInputParam(METHOD, "pageNo", pageNo));
        logger.debug(getMessageInputParam(METHOD, "pageSize", pageSize));
        logger.debug(getMessageInputParam(METHOD, "sortBy", sortBy));
        logger.debug(getMessageInputParam(METHOD, "sortDir", sortDir));
        PageRequest pageRequest = Utilities.getPageRequest(pageNo, pageSize, sortBy, sortDir);
        List<Post> posts = postRepository.getPostsByUser(userName);
        PageImpl<Post> page = new PageImpl<>(posts, pageRequest, posts.size());
        logger.debug(getMessageOutputParam(METHOD, "posts", posts.size()));
        logger.info(getMessageEnd(METHOD, "GetPostByUser"));
        return page.map(post -> getMapper().toDto(post, getCycleAvoidingMappingContext()));
    }

    @Override
    public PostDTO createPost(NewPostRequest newPostRequest) {
        logger.info(getMessageStart(METHOD, "CreatePost"));
        logger.debug(getMessageInputParam(METHOD, "newPostRequest", newPostRequest));
        UserDTO user = userService.findByEmail(newPostRequest.getUserName());
        newPostRequest.getPostDTO().setAuthor(user);
        Post post = getMapper().toEntity(newPostRequest.getPostDTO(), getCycleAvoidingMappingContext());
        post = postRepository.save(post);
        logger.debug(getMessageOutputParam(METHOD, "post", post));
        logger.info(getMessageEnd(METHOD, "CreatePost"));
        return getMapper().toDto(post, getCycleAvoidingMappingContext());
    }

    @Override
    public PostDTO updatePost(UUID id, PostDTO postDTO) {
        logger.info(getMessageStart(METHOD, "UpdatePost"));
        logger.debug(getMessageInputParam(METHOD, "id", id));
        logger.debug(getMessageInputParam(METHOD, "postDTO", postDTO));
        Post post = postRepository.findById(id).orElse(null);
        if (post != null) {
            post = getMapper().toEntity(postDTO, getCycleAvoidingMappingContext());
            post = postRepository.save(post);
            logger.debug(getMessageOutputParam(METHOD, "post", post));
            logger.info(getMessageEnd(METHOD, "UpdatePost"));
            return getMapper().toDto(post, getCycleAvoidingMappingContext());
        } else {
            String message = "Update Failed";
            logger.error("Update Failed: {}", message);
            logger.info(getMessageEnd(METHOD, "UpdatePost"));
            throw new SystemErrorException(message);
        }
    }

    @Override
    public Boolean deletePost(UUID id, String userName) {
        logger.info(getMessageStart(METHOD, "DeletePost"));
        logger.debug(getMessageInputParam(METHOD, "id", id));
        try {
            Post post = postRepository.findById(id).orElse(null);
            if (post != null && Objects.equals(post.getAuthor().getUserName(), userName)) {
                postRepository.delete(post);
                logger.debug(getMessageOutputParam(METHOD, "post", "Delete successfully!"));
                logger.info(getMessageEnd(METHOD, "DeletePost"));
                return true;
            } else {
                String message = "Delete Failed";
                logger.error(message);
                throw new SystemErrorException(message);
            }
        } catch (SystemErrorException e) {
            logger.error("{}", e.getMessage());
            logger.info(getMessageEnd(METHOD, "DeletePost"));
            throw new SystemErrorException(e.getMessage());
        }
    }

    @Override
    public PostDTO getPostByUserAndId(UUID id, String userName) {
        logger.info(getMessageStart(METHOD, "GetPostByUserAndId"));
        logger.debug(getMessageInputParam(METHOD, "id", id));
        logger.debug(getMessageInputParam(METHOD, "userName", userName));
        Post post = postRepository.getPostsByUserAndId(id, userName).orElse(null);
        if (Objects.isNull(post)) {
            String message = "Get Post Failed";
            logger.error(message);
            logger.info(getMessageEnd(METHOD, "GetPostByUserAndId"));
            throw new SystemErrorException(message);
        } else {
            logger.debug(getMessageOutputParam(METHOD, "post", post));
            logger.info(getMessageEnd(METHOD, "GetPostByUserAndId"));
            return getMapper().toDto(post, getCycleAvoidingMappingContext());
        }
    }

    @Override
    public List<PostDTO> getFriendPosts(String userName) {
        logger.info(getMessageStart(METHOD, "GetFriendPosts"));
        logger.debug(getMessageInputParam(METHOD, "userName", userName));
        List<Post> output = new ArrayList<>();
        try {
            UserDTO userDTO = userService.findByUserName(userName);
            for (UserDTO friend : userDTO.getFriends()) {
                List<Post> posts = postRepository.getPostsByUser(friend.getUserName());
                output.addAll(posts);
            }
            output.sort(Comparator.comparing(Post::getId));
            logger.debug(getMessageOutputParam(METHOD, "output", output.size()));
            logger.info(getMessageEnd(METHOD, "GetFriendPosts"));
            return output.stream().map(post ->
                    getMapper().toDto(post, getCycleAvoidingMappingContext())).collect(Collectors.toList());
        } catch (SystemErrorException e) {
            logger.error("{}", e.getMessage());
            logger.info(getMessageEnd(METHOD, "GetFriendPosts"));
            throw new SystemErrorException(e.getMessage());
        }
    }

    @Override
    public Map<String, Boolean> likePost(UUID id, String userName) {
        logger.info(getMessageStart(METHOD, "LikePost"));
        logger.debug(getMessageInputParam(METHOD, "id", id));
        logger.debug(getMessageInputParam(METHOD, "userName", userName));
        Map<String, Boolean> output = new HashMap<>();
        output.put("like", null);
        output.put("unlike", null);
        try {
            Post post = postRepository.findById(id).orElse(null);
            User user = userService.findByUserNameModel(userName);
            if (post != null) {
                List<User> likes = post.getLikedByUsers();
                if (likes.contains(user)) {
                    likes.remove(user);
                    output.put("unlike", true);
                } else {
                    likes.add(user);
                    output.put("like", true);
                }
                post.setLikedByUsers(likes);
                postRepository.save(post);
                logger.debug(getMessageOutputParam(METHOD, "output", output));
                logger.info(getMessageEnd(METHOD, "LikePost"));
                return output;
            } else {
                String message = "Like Failed";
                logger.error(message);
                throw new SystemErrorException(message);
            }
        } catch (SystemErrorException e) {
            logger.error("{}", e.getMessage());
            logger.info(getMessageEnd(METHOD, "LikePost"));
            throw new SystemErrorException(e.getMessage());
        }
    }
}
