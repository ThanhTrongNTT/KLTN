package hcmute.nhom.kltn.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import hcmute.nhom.kltn.enums.LikeStatusType;

/**
 * Class PostDTO.
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
public class PostDTO extends AbstractNonAuditDTO {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String title;
    private String content;
    private String image;
    private String video;
    private String category;
    private UserDTO author;
    private List<CommentDTO> comments;
    private List<UserDTO> likedByUsers;
    private LikeStatusType likeStatus;
    private Boolean removalFlag;
}
