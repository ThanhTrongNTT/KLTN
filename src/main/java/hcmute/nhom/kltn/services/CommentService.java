package hcmute.nhom.kltn.services;

import java.util.List;
import java.util.Map;
import hcmute.nhom.kltn.dto.CommentDTO;
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
}
