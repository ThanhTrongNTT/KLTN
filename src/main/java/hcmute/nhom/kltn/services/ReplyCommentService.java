package hcmute.nhom.kltn.services;

import hcmute.nhom.kltn.dto.ReplyCommentDTO;
import hcmute.nhom.kltn.model.ReplyComment;

/**
 * Class ReplyCommentService.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
public interface ReplyCommentService extends AbstractService<ReplyCommentDTO, ReplyComment> {

    /**
     * createReplyComment.
     * @param replyCommentDTO replyCommentDTO
     * @return ReplyCommentDTO
     */
    ReplyCommentDTO editReply(String replyId, ReplyCommentDTO replyCommentDTO, String userName);
}
