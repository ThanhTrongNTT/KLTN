package hcmute.nhom.kltn.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import hcmute.nhom.kltn.dto.CommentDTO;
import hcmute.nhom.kltn.dto.PostDTO;
import hcmute.nhom.kltn.dto.UserDTO;
import hcmute.nhom.kltn.exception.SystemErrorException;
import hcmute.nhom.kltn.mapper.CommentMapper;
import hcmute.nhom.kltn.model.Comment;
import hcmute.nhom.kltn.repository.CommentRepository;
import hcmute.nhom.kltn.services.CommentService;
import hcmute.nhom.kltn.services.PostService;
import hcmute.nhom.kltn.services.UserService;

/**
 * Class CommentServiceImpl.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Service
@RequiredArgsConstructor
public class CommentServiceImpl
        extends AbstractServiceImpl<CommentRepository, CommentMapper, CommentDTO, Comment> implements CommentService {
    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);
    private static final String SERVICE = "CommentService";
    private final PostService postService;
    private final UserService userService;
    private final CommentRepository commentRepository;

    @Override
    public CommentDTO commentToPost(String postId, CommentDTO commentDTO, String userName) {
        logger.info(getMessageStart(SERVICE, "CommentToPost"));
        logger.debug(getMessageInputParam(SERVICE, "postId", postId));
        logger.debug(getMessageInputParam(SERVICE, "commentDTO", commentDTO));
        logger.debug(getMessageInputParam(SERVICE, "userName", userName));
        try {
            PostDTO postDTO = postService.findById(UUID.fromString(postId));
            UserDTO userDTO = userService.findByUserName(userName);
            commentDTO.setPost(postDTO);
            commentDTO.setAuthor(userDTO);
            Comment comment = getRepository().save(getMapper().toEntity(commentDTO, getCycleAvoidingMappingContext()));
            CommentDTO result = getMapper().toDto(comment, getCycleAvoidingMappingContext());
            logger.debug(getMessageOutputParam(SERVICE, "commentDTO", result));
            logger.info(getMessageEnd(SERVICE, "CommentToPost"));
            return result;
        } catch (SystemErrorException e) {
            String message = "Comment to post failed";
            logger.error("{}", message);
            logger.info(getMessageEnd(SERVICE, "CommentToPost"));
            throw new SystemErrorException(message);
        }
    }

    @Override
    public Map<String, Boolean> likeComment(String commentId, String userName) {
        logger.info(getMessageStart(SERVICE, "LikeComment"));
        logger.debug(getMessageInputParam(SERVICE, "commentId", commentId));
        logger.debug(getMessageInputParam(SERVICE, "userName", userName));
        Map<String, Boolean> output = new HashMap<>();
        try {
            UserDTO userDTO = userService.findByUserName(userName);
            CommentDTO commentDTO = findById(UUID.fromString(commentId));
            List<UserDTO> likes = commentDTO.getLikedByUsers();
            if (likes.contains(userDTO)) {
                likes.remove(userDTO);
                output.put("unlike", true);
            } else {
                likes.add(userDTO);
                output.put("like", true);
            }
        } catch (SystemErrorException e) {
            String message = "Like comment failed";
            logger.error("{}", message);
            logger.info(getMessageEnd(SERVICE, "LikeComment"));
            throw new SystemErrorException(message);
        }
        logger.debug(getMessageOutputParam(SERVICE, "output", output));
        logger.info(getMessageEnd(SERVICE, "LikeComment"));
        return output;
    }

    @Override
    public List<CommentDTO> getCommentByPost(String postId) {
        logger.info(getMessageStart(SERVICE, "GetCommentByPost"));
        logger.debug(getMessageInputParam(SERVICE, "postId", postId));
        try {
            List<Comment> result = commentRepository.getCommentsByPostId(UUID.fromString(postId));
            logger.debug(getMessageOutputParam(SERVICE, "result", result.size()));
            logger.info(getMessageEnd(SERVICE, "GetCommentByPost"));
            return result.stream().map(comment ->
                    getMapper().toDto(comment, getCycleAvoidingMappingContext())).collect(Collectors.toList());
        } catch (SystemErrorException e) {
            String message = "Get comment by post failed";
            logger.error("{}", message);
            logger.info(getMessageEnd(SERVICE, "GetCommentByPost"));
            throw new SystemErrorException(message);
        }
    }
}
