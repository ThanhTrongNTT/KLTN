package hcmute.nhom.kltn.services;

import java.util.List;
import java.util.Map;
import hcmute.nhom.kltn.dto.CommentDTO;
import hcmute.nhom.kltn.dto.ReplyCommentDTO;
import hcmute.nhom.kltn.model.Comment;

/**
 * Class CommentService.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
public interface CommentService extends AbstractService<CommentDTO, Comment> {

    /**
     * commentToPost.
     * @param postId    postId
     * @param commentDTO commentDTO
     * @param userName userName
     * @return CommentDTO
     */
    CommentDTO commentToPost(String postId, CommentDTO commentDTO, String userName);

    /**
     * likeComment.
     * @param commentId commentId
     * @param userName userName
     * @return Map<String, String>
     */
    Map<String, Boolean> likeComment(String commentId, String userName);

    /**
     * getCommentByPost.
     * @param postId postId
     * @return List<CommentDTO>
     */
    List<CommentDTO> getCommentByPost(String postId);

    /**
     * Update Comment.
     * @param commentId commentId
     * @param commentDTO commentDTO
     * @param userName userName
     * @return CommentDTO
     */
    CommentDTO updateComment(String commentId, CommentDTO commentDTO, String userName);

    /**
     * Reply Comment.
     * @param commentId commentId
     * @param replyCommentDTO replyCommentDTO
     * @param userName userName
     * @return ReplyCommentDTO
     */
    ReplyCommentDTO replyComment(String commentId, ReplyCommentDTO replyCommentDTO, String userName);

    /**
     * Edit Comment.
     * @param commentId String
     * @param commentDTO CommentDTO
     * @param userName String
     * @return CommentDTO
     */
    CommentDTO editComment(String commentId, CommentDTO commentDTO, String userName);

    /**
     * Delete Comment.
     * @param commentId String
     * @param userName String
     * @return Boolean
     */
    Boolean deleteComment(String commentId, String userName);

    /**
     * Delete Reply.
     * @param replyCommentId String
     * @param replyCommentDTO ReplyCommentDTO
     * @param userName String
     * @return ReplyCommentDTO
     */
    ReplyCommentDTO editReply(String replyCommentId, ReplyCommentDTO replyCommentDTO, String userName);
}
