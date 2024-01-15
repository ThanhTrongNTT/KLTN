package hcmute.nhom.kltn.services.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import hcmute.nhom.kltn.dto.ReplyCommentDTO;
import hcmute.nhom.kltn.exception.SystemErrorException;
import hcmute.nhom.kltn.mapper.ReplyCommentMapper;
import hcmute.nhom.kltn.model.ReplyComment;
import hcmute.nhom.kltn.repository.ReplyCommentRepository;
import hcmute.nhom.kltn.services.ReplyCommentService;

/**
 * Class ReplyCommentServiceImpl.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Service
@RequiredArgsConstructor
public class ReplyCommentServiceImpl
        extends AbstractServiceImpl<ReplyCommentRepository, ReplyCommentMapper, ReplyCommentDTO, ReplyComment>
        implements ReplyCommentService {
    private static final Logger logger = LoggerFactory.getLogger(ReplyCommentServiceImpl.class);
    private static final String SERVICE_NAME = "ReplyCommentService";
    private final ReplyCommentRepository replyCommentRepository;

    @Override
    public ReplyCommentDTO editReply(String replyId, ReplyCommentDTO replyCommentDTO, String userName) {
        String method = "editReply";
        logger.info(getMessageStart(SERVICE_NAME, method));
        logger.info(getMessageInputParam(SERVICE_NAME, "replyId", replyId));
        logger.info(getMessageInputParam(SERVICE_NAME, "replyCommentDTO", replyCommentDTO.toString()));
        logger.info(getMessageInputParam(SERVICE_NAME, "userName", userName));
        try {
            ReplyCommentDTO reply = findById(replyId);
            if (reply == null) {
                logger.error("{}:", "reply not found");
                logger.info(getMessageEnd(SERVICE_NAME, method));
                throw new SystemErrorException("reply not found");
            }
            if (!reply.getAuthor().getUserName().equals(userName)) {
                logger.error("{}:", "Reply not belong to user");
                logger.info(getMessageEnd(SERVICE_NAME, method));
                throw new SystemErrorException("reply not belong to user");
            }
            reply.setContent(replyCommentDTO.getContent());
            reply.setImage(replyCommentDTO.getImage());
            reply.setVideo(replyCommentDTO.getVideo());
            reply = save(reply);
            logger.debug(getMessageOutputParam(SERVICE_NAME, "reply", reply.toString()));
            logger.info(getMessageEnd(SERVICE_NAME, method));
            return reply;
        } catch (Exception e) {
            String message = e.getMessage();
            logger.error("{}:", message);
            logger.info(getMessageEnd(SERVICE_NAME, method));
            throw new SystemErrorException(message);
        }
    }

    @Override
    public ReplyCommentRepository getRepository() {
        return replyCommentRepository;
    }

    @Override
    public ReplyCommentMapper getMapper() {
        return ReplyCommentMapper.INSTANCE;
    }
}
