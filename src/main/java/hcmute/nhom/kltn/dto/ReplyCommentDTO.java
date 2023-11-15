package hcmute.nhom.kltn.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class ReplyCommentDTO.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReplyCommentDTO extends AbstractNonAuditDTO {
    private static final long serialVersionUID = 1L;
    private UUID id;
    private CommentDTO comment;
    private UserDTO author;
    private String content;
    private String image;
    private String video;
    private Integer likeStatus;
    private Integer removalFlag;
}
