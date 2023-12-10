package hcmute.nhom.kltn.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import hcmute.nhom.kltn.dto.CommentDTO;
import hcmute.nhom.kltn.dto.PostDTO;
import hcmute.nhom.kltn.dto.ReplyCommentDTO;
import hcmute.nhom.kltn.dto.UserDTO;
import hcmute.nhom.kltn.exception.SystemErrorException;
import hcmute.nhom.kltn.mapper.CommentMapper;
import hcmute.nhom.kltn.model.Comment;
import hcmute.nhom.kltn.repository.CommentRepository;
import hcmute.nhom.kltn.services.CommentService;
import hcmute.nhom.kltn.services.PostService;
import hcmute.nhom.kltn.services.ReplyCommentService;
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
    private final ReplyCommentService replyCommentService;

    @Override
    public CommentDTO commentToPost(String postId, CommentDTO commentDTO, String userName) {
        logger.info(getMessageStart(SERVICE, "CommentToPost"));
        logger.debug(getMessageInputParam(SERVICE, "postId", postId));
        logger.debug(getMessageInputParam(SERVICE, "commentDTO", commentDTO));
        logger.debug(getMessageInputParam(SERVICE, "userName", userName));
        try {
            PostDTO postDTO = postService.findById(postId);
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
            CommentDTO commentDTO = findById(commentId);
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
            List<Comment> result = getRepository().getCommentsByPostId(UUID.fromString(postId));
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

    @Override
    public CommentDTO updateComment(String commentId, CommentDTO commentDTO, String userName) {
        String method = "UpdateComment";
        logger.info(getMessageStart(SERVICE, method));
        logger.debug(getMessageInputParam(SERVICE, "commentId", commentId));
        logger.debug(getMessageInputParam(SERVICE, "commentDTO", commentDTO));
        logger.debug(getMessageInputParam(SERVICE, "userName", userName));
        try {
            CommentDTO comment = findById(commentId);
            if (comment.getAuthor().getUserName().equals(userName)) {
                comment.setContent(commentDTO.getContent());
                Comment result = getRepository().save(getMapper().toEntity(comment, getCycleAvoidingMappingContext()));
                logger.debug(getMessageOutputParam(SERVICE, "result", result));
                logger.info(getMessageEnd(SERVICE, method));
                return getMapper().toDto(result, getCycleAvoidingMappingContext());
            } else {
                String message = "You are not author of this comment";
                logger.error("{}", message);
                logger.info(getMessageEnd(SERVICE, method));
                throw new SystemErrorException(message);
            }
        } catch (SystemErrorException e) {
            String message = "Update comment failed";
            logger.error("{}", message);
            logger.info(getMessageEnd(SERVICE, method));
            throw new SystemErrorException(message);
        }
    }

    @Override
    public ReplyCommentDTO replyComment(String commentId, ReplyCommentDTO replyCommentDTO, String userName) {
        logger.info(getMessageStart(SERVICE, "ReplyComment"));
        logger.debug(getMessageInputParam(SERVICE, "commentId", commentId));
        logger.debug(getMessageInputParam(SERVICE, "replyCommentDTO", replyCommentDTO));
        logger.debug(getMessageInputParam(SERVICE, "userName", userName));
        try {
            CommentDTO commentDTO = findById(commentId);
            UserDTO userDTO = userService.findByUserName(userName);
            replyCommentDTO.setComment(commentDTO);
            replyCommentDTO.setAuthor(userDTO);
            ReplyCommentDTO result = replyCommentService.save(replyCommentDTO);
            logger.debug(getMessageOutputParam(SERVICE, "comment:", result.getContent()));
            logger.info(getMessageEnd(SERVICE, "ReplyComment"));
            return result;
        } catch (SystemErrorException e) {
            String message = "Reply comment failed";
            logger.error("{}", message);
            logger.info(getMessageEnd(SERVICE, "ReplyComment"));
            throw new SystemErrorException(message);
        }
    }

    @Override
    public CommentDTO editComment(String commentId, CommentDTO commentDTO, String userName) {
        String method = "EditComment";
        logger.info(getMessageStart(SERVICE, method));
        logger.debug(getMessageInputParam(SERVICE, "commentId", commentId));
        logger.debug(getMessageInputParam(SERVICE, "commentDTO", commentDTO));
        logger.debug(getMessageInputParam(SERVICE, "userName", userName));
        try {
            CommentDTO comment = findById(commentId);
            CommentDTO result = save(setComment(comment));
            logger.debug(getMessageOutputParam(SERVICE, "result", result));
            logger.info(getMessageEnd(SERVICE, method));
            return result;
        } catch (SystemErrorException e) {
            String message = e.getMessage();
            logger.error("{}", message);
            logger.info(getMessageEnd(SERVICE, method));
            throw new SystemErrorException(message);
        }
    }

    @Override
    public Boolean deleteComment(String commentId, String userName) {
        logger.info(getMessageStart(SERVICE, "DeleteComment"));
        logger.debug(getMessageInputParam(SERVICE, "commentId", commentId));
        logger.debug(getMessageInputParam(SERVICE, "userName", userName));
        try {
            CommentDTO commentDTO = findById(commentId);
            if (commentDTO.getAuthor().getUserName().equals(userName)) {
                commentDTO.setRemovalFlag(true);
                getRepository().save(getMapper().toEntity(commentDTO, getCycleAvoidingMappingContext()));
                logger.info(getMessageEnd(SERVICE, "DeleteComment"));
                return true;
            } else {
                String message = "You are not author of this comment";
                logger.error("{}", message);
                logger.info(getMessageEnd(SERVICE, "DeleteComment"));
                throw new SystemErrorException(message);
            }
        } catch (SystemErrorException e) {
            String message = "Delete comment failed";
            logger.error("{}", message);
            logger.info(getMessageEnd(SERVICE, "DeleteComment"));
            throw new SystemErrorException(message);
        }
    }

    @Override
    public ReplyCommentDTO editReply(String replyCommentId, ReplyCommentDTO replyCommentDTO, String userName) {
        logger.info(getMessageStart(SERVICE, "EditReply"));
        logger.debug(getMessageInputParam(SERVICE, "replyCommentId", replyCommentId));
        logger.debug(getMessageInputParam(SERVICE, "replyCommentDTO", replyCommentDTO));
        logger.debug(getMessageInputParam(SERVICE, "userName", userName));
        try {
            ReplyCommentDTO replyComment = replyCommentService.editReply(replyCommentId, replyCommentDTO, userName);
            logger.debug(getMessageOutputParam(SERVICE, "replyComment", replyComment));
            logger.info(getMessageEnd(SERVICE, "EditReply"));
            return replyComment;
        } catch (SystemErrorException e) {
            String message = "Edit reply failed";
            logger.error("{}", message);
            logger.info(getMessageEnd(SERVICE, "EditReply"));
            throw new SystemErrorException(message);
        }
    }

    /**
     * setComment.
     * @param input CommentDTO
     * @return CommentDTO
     */
    public CommentDTO setComment(CommentDTO input) {
        CommentDTO output = new CommentDTO();
        if (Objects.nonNull(input)) {
            if (Objects.nonNull(input.getContent())) {
                output.setContent(input.getContent());
            }
            if (Objects.nonNull(input.getImage())) {
                output.setImage(input.getImage());
            }
            if (Objects.nonNull(input.getVideo())) {
                output.setVideo(input.getVideo());
            }
        }

        return output;
    }

    @Override
    public CommentMapper getMapper() {
        return CommentMapper.INSTANCE;
    }

    @Override
    public CommentRepository getRepository() {
        return commentRepository;
    }
}
