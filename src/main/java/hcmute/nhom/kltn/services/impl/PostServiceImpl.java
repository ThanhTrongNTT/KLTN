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
import org.springframework.stereotype.Service;
import hcmute.nhom.kltn.common.payload.ListResponse;
import hcmute.nhom.kltn.common.payload.PagingResponseCustom;
import hcmute.nhom.kltn.dto.FriendDTO;
import hcmute.nhom.kltn.dto.MediaFileDTO;
import hcmute.nhom.kltn.dto.PostDTO;
import hcmute.nhom.kltn.dto.UserDTO;
import hcmute.nhom.kltn.exception.NotFoundException;
import hcmute.nhom.kltn.exception.SystemErrorException;
import hcmute.nhom.kltn.mapper.PostMapper;
import hcmute.nhom.kltn.model.Post;
import hcmute.nhom.kltn.model.User;
import hcmute.nhom.kltn.repository.PostRepository;
import hcmute.nhom.kltn.services.FriendService;
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
    private static final String SERVICE = "PostService";
    private final UserService userService;
    private final PostRepository postRepository;
    private final FriendService friendService;

    /**
     * searchPostList.
     *
     * @param postDTO PostDTO
     * @return Page<PostDTO>
     */
    @Override
    public ListResponse<PostDTO> searchPost(PostDTO postDTO) {
        logger.info(getMessageStart(SERVICE, "SearchPost"));
        logger.debug(getMessageInputParam(SERVICE, "postDTO", postDTO));
        String searchContent = "%" + postDTO.getContent() + "%";
        List<Post> posts = getRepository().searchPost(searchContent);
        List<PostDTO> postDTOS = posts.stream().map(post -> getMapper().toDto(post,
                getCycleAvoidingMappingContext())).collect(Collectors.toList());
        logger.debug(getMessageOutputParam(SERVICE, "posts", posts.size()));
        logger.info(getMessageEnd(SERVICE, "SearchPost"));
        return Utilities.createListResponse(postDTOS);
    }

    @Override
    public ListResponse<PostDTO> getAllPost() {
        logger.info(getMessageStart(SERVICE, "getAllPost"));
        List<Post> posts = getRepository().findAll();
        List<PostDTO> postDTOS = posts.stream()
                .filter(item -> !item.getRemovalFlag())
                .map(post -> getMapper().toDto(post,
                getCycleAvoidingMappingContext())).collect(Collectors.toList());
        logger.debug(getMessageOutputParam(SERVICE, "posts", posts.size()));
        logger.info(getMessageEnd(SERVICE, "SearchPost"));
        return Utilities.createListResponse(postDTOS);
    }

    /**
     * getAllPost.
     *
     * @param pageNo  Page number
     * @param pageSize Page size
     * @param sortBy Sort by
     * @param sortDir Sort direction
     * @return Page<PostDTO>
     */
    @Override
    public PagingResponseCustom<PostDTO> getAllPostPaging(int pageNo, int pageSize, String sortBy, String sortDir) {
        logger.info(getMessageStart(SERVICE, "GetAllPost"));
        logger.debug(getMessageInputParam(SERVICE, "pageNo", pageNo));
        logger.debug(getMessageInputParam(SERVICE, "pageSize", pageSize));
        logger.debug(getMessageInputParam(SERVICE, "sortBy", sortBy));
        logger.debug(getMessageInputParam(SERVICE, "sortDir", sortDir));
        PageRequest pageRequest = Utilities.getPageRequest(pageNo, pageSize, sortBy, sortDir);
        try {
            List<Post> posts = getRepository().findAll();
            List<PostDTO> postDTOS = posts.stream()
                    .filter(item -> !item.getRemovalFlag())
                    .map(post -> getMapper().toDto(post,
                            getCycleAvoidingMappingContext())).collect(Collectors.toList());
            Page<PostDTO> postsPage = new PageImpl<>(postDTOS, pageRequest, postDTOS.size());
            PagingResponseCustom output = Utilities.createPagingResponse(postsPage);
            logger.debug(getMessageOutputParam(SERVICE, "posts", postsPage.getTotalElements()));
            logger.info(getMessageEnd(SERVICE, "GetAllPost"));
            return output;
        } catch (Exception e) {
            String message = "Get All Post Failed";
            logger.error("{}", message);
            logger.info(getMessageEnd(SERVICE, "GetAllPost"));
            throw new SystemErrorException(message);
        }

    }

    @Override
    public PagingResponseCustom<PostDTO> getPostByUserPaging(
            int pageNo, int pageSize, String sortBy, String sortDir, String userName) {
        logger.info(getMessageStart(SERVICE, "GetPostByUser"));
        logger.debug(getMessageInputParam(SERVICE, "pageNo", pageNo));
        logger.debug(getMessageInputParam(SERVICE, "pageSize", pageSize));
        logger.debug(getMessageInputParam(SERVICE, "sortBy", sortBy));
        logger.debug(getMessageInputParam(SERVICE, "sortDir", sortDir));
        PageRequest pageRequest = Utilities.getPageRequest(pageNo, pageSize, sortBy, sortDir);
        try {
            List<Post> posts = getRepository().getPostsByUser(userName);
            List<PostDTO> postDTOS = posts.stream()
                    .map(post -> getMapper().toDto(post,
                            getCycleAvoidingMappingContext())).collect(Collectors.toList());
            PageImpl<PostDTO> page = new PageImpl<>(postDTOS, pageRequest, posts.size());
            PagingResponseCustom<PostDTO> output = Utilities.createPagingResponse(page);
            logger.debug(getMessageOutputParam(SERVICE, "posts", posts.size()));
            logger.info(getMessageEnd(SERVICE, "GetPostByUser"));
            return output;
        } catch (Exception e) {
            String message = "Get Post By User Failed";
            logger.error("{}", message);
            logger.info(getMessageEnd(SERVICE, "GetPostByUser"));
            throw new SystemErrorException(message);
        }
    }

    @Override
    public PostDTO createPost(PostDTO postDTO, String email) {
        String method = "CreatePost";
        logger.info(getMessageStart(SERVICE, method));
        logger.debug(getMessageInputParam(SERVICE, "postDTO", postDTO));
        logger.debug(getMessageInputParam(SERVICE, "email", email));
        try {
            UserDTO userDTO = userService.findByEmail(email);
            postDTO.setAuthor(userDTO);
            postDTO.setCreatedBy(userDTO.getUserName());
            postDTO.setModifiedBy(userDTO.getUserName());
            Post post = getMapper().toEntity(postDTO, getCycleAvoidingMappingContext());
            post = getRepository().save(post);
            logger.debug(getMessageOutputParam(SERVICE, "post", post));
            logger.info(getMessageEnd(SERVICE, method));
            return getMapper().toDto(post, getCycleAvoidingMappingContext());
        } catch (SystemErrorException e) {
            String message = "Create Failed";
            logger.error("Create Failed: {}", message);
            logger.info(getMessageEnd(SERVICE, method));
            throw new SystemErrorException(message);
        } catch (NotFoundException e) {
            String message = "User not found";
            logger.error("User not found: {}", message);
            logger.info(getMessageEnd(SERVICE, method));
            throw new NotFoundException(message);
        }
    }

    @Override
    public PostDTO updatePost(String id, PostDTO postDTO) {
        String method = "UpdatePost";
        logger.info(getMessageStart(SERVICE, method));
        logger.debug(getMessageInputParam(SERVICE, "id", id));
        logger.debug(getMessageInputParam(SERVICE, "postDTO", postDTO));
        try {
            PostDTO postDTOOld = findById(id);
            postDTOOld = convertOldToNew(postDTOOld, postDTO);
            Post post = getMapper().toEntity(postDTOOld, getCycleAvoidingMappingContext());
            post = getRepository().save(post);
            logger.debug(getMessageOutputParam(SERVICE, "post", post));
            logger.info(getMessageEnd(SERVICE, method));
            return getMapper().toDto(post, getCycleAvoidingMappingContext());
        } catch (SystemErrorException e) {
            String message = "Update Failed";
            logger.error("Update Failed: {}", message);
            logger.info(getMessageEnd(SERVICE, method));
            throw new SystemErrorException(message);
        }
    }

    @Override
    public Boolean deletePost(String id, String userName) {
        String method = "DeletePost";
        logger.info(getMessageStart(SERVICE, method));
        logger.debug(getMessageInputParam(SERVICE, "id", id));
        try {
            Post post = getRepository().findById(id).orElse(null);
            if (post != null && Objects.equals(post.getAuthor().getUserName(), userName)) {
                post.setRemovalFlag(true);
                getRepository().save(post);
                logger.debug(getMessageOutputParam(SERVICE, "result", "Delete successfully!"));
                logger.info(getMessageEnd(SERVICE, method));
                return true;
            } else {
                String message = "Delete Failed";
                logger.error(message);
                throw new SystemErrorException(message);
            }
        } catch (SystemErrorException e) {
            String message = "Delete Failed";
            logger.error("{}", message);
            logger.info(getMessageEnd(SERVICE, method));
            throw new SystemErrorException(message);
        }
    }

    @Override
    public PostDTO getPostByUserAndId(String id, String userName) {
        String method = "GetPostByUserAndId";
        logger.info(getMessageStart(SERVICE, method));
        logger.debug(getMessageInputParam(SERVICE, "id", id));
        logger.debug(getMessageInputParam(SERVICE, "userName", userName));
        Post post = getRepository().getPostsByUserAndId(id, userName).orElse(null);
        if (Objects.isNull(post)) {
            String message = "Get Post Failed";
            logger.error(message);
            logger.info(getMessageEnd(SERVICE, method));
            throw new SystemErrorException(message);
        } else {
            logger.debug(getMessageOutputParam(SERVICE, "post", post));
            logger.info(getMessageEnd(SERVICE, method));
            return getMapper().toDto(post, getCycleAvoidingMappingContext());
        }
    }

    @Override
    public List<PostDTO> getFriendPosts(String userName) {
        String method = "GetFriendPosts";
        logger.info(getMessageStart(SERVICE, method));
        logger.debug(getMessageInputParam(SERVICE, "userName", userName));
        List<Post> output = new ArrayList<>();
        try {
            UserDTO userDTO = userService.findByUserName(userName);
            List<FriendDTO> friends = friendService.getFriendByUserName(userDTO.getId());
            for (FriendDTO friend : friends) {
                List<Post> posts = getRepository().getPostsByUser(friend.getFriendUser().getUserName());
                output.addAll(posts);
            }
            output.sort(Comparator.comparing(Post::getId));
            logger.debug(getMessageOutputParam(SERVICE, "output", output.size()));
            logger.info(getMessageEnd(SERVICE, method));
            return output.stream().map(post ->
                    getMapper().toDto(post, getCycleAvoidingMappingContext())).collect(Collectors.toList());
        } catch (SystemErrorException e) {
            String message = "Get Friend Posts Failed";
            logger.error("{}", message);
            logger.info(getMessageEnd(SERVICE, method));
            throw new SystemErrorException(message);
        }
    }

    @Override
    public Map<String, Boolean> likePost(String id, String userName) {
        String method = "LikePost";
        logger.info(getMessageStart(SERVICE, method));
        logger.debug(getMessageInputParam(SERVICE, "id", id));
        logger.debug(getMessageInputParam(SERVICE, "userName", userName));
        Map<String, Boolean> output = new HashMap<>();
        output.put("like", null);
        output.put("unlike", null);
        try {
            Post post = getRepository().findById(id).orElse(null);
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
                getRepository().save(post);
                logger.debug(getMessageOutputParam(SERVICE, "output", output));
                logger.info(getMessageEnd(SERVICE, method));
                return output;
            } else {
                String message = "Like Failed";
                logger.error(message);
                throw new SystemErrorException(message);
            }
        } catch (SystemErrorException e) {
            String message = "Like Failed";
            logger.error("{}", message);
            logger.info(getMessageEnd(SERVICE, method));
            throw new SystemErrorException(message);
        }
    }

    @Override
    public PostDTO getPostById(String id) {
        logger.info(getMessageStart(SERVICE, "GetPostById"));
        logger.debug(getMessageInputParam(SERVICE, "id", id));
        try {
            Post post = getRepository().findById(id).orElse(null);
            if (Objects.nonNull(post)) {
                logger.debug(getMessageOutputParam(SERVICE, "post", post));
                logger.info(getMessageEnd(SERVICE, "GetPostById"));
                return getMapper().toDto(post, getCycleAvoidingMappingContext());
            } else {
                String message = "Get Post Failed";
                logger.error(message);
                logger.info(getMessageEnd(SERVICE, "GetPostById"));
                throw new SystemErrorException(message);
            }
        } catch (SystemErrorException e) {
            String message = "Get Post Failed";
            logger.error("{}", message);
            logger.info(getMessageEnd(SERVICE, "GetPostById"));
            throw new SystemErrorException(message);
        }
    }

    @Override
    public ListResponse<MediaFileDTO> getAllMediaByUserName(String userName) {
        logger.info(getMessageStart(SERVICE, "GetAllMediaByUserName"));
        logger.debug(getMessageInputParam(SERVICE, "userName", userName));
        try {
            List<Post> posts = getRepository().getPostsByUser(userName);
            List<PostDTO> postDTOs = posts.stream()
                    .map(post -> getMapper().toDto(post, getCycleAvoidingMappingContext()))
                    .collect(Collectors.toList());
            List<MediaFileDTO> mediaFileDTOS = new ArrayList<>();
            for (PostDTO post : postDTOs) {
                if (Objects.nonNull(post.getImage())) {
                    mediaFileDTOS.add(post.getImage());
                }
                if (Objects.nonNull(post.getVideo())) {
                    mediaFileDTOS.add(post.getVideo());
                }
            }
            mediaFileDTOS.sort(Comparator.comparing(MediaFileDTO::getType));
            logger.debug(getMessageOutputParam(SERVICE, "mediaFileDTOS", mediaFileDTOS.size()));
            logger.info(getMessageEnd(SERVICE, "GetAllMediaByUserName"));
            return Utilities.createListResponse(mediaFileDTOS);
        } catch (SystemErrorException e) {
            String message = "Get All Media By User Name Failed";
            logger.error("{}", message);
            logger.info(getMessageEnd(SERVICE, "GetAllMediaByUserName"));
            throw new SystemErrorException(message);
        }
    }

    /**
     * convertOldToNew.
     * @param postDTOOld PostDTO
     * @return PostDTO
     */
    public PostDTO convertOldToNew(PostDTO postDTOOld, PostDTO postDTONew) {
        if (Objects.nonNull(postDTONew.getContent())) {
            postDTOOld.setContent(postDTONew.getContent());
        }
        if (Objects.nonNull(postDTONew.getImage())) {
            postDTOOld.setImage(postDTONew.getImage());
        }
        if (Objects.nonNull(postDTONew.getVideo())) {
            postDTOOld.setVideo(postDTONew.getVideo());
        }
        return postDTOOld;
    }

    @Override
    public PostMapper getMapper() {
        return PostMapper.INSTANCE;
    }

    @Override
    public PostRepository getRepository() {
        return postRepository;
    }
}
