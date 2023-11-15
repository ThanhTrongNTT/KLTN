package hcmute.nhom.kltn.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import hcmute.nhom.kltn.model.Role;

/**
 * Class UserDTO.
 * Create by: IntelliJ IDEA
 *
 * @author : ThanhTrong
 **/
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO extends AbstractNonAuditDTO {
    private static final long serialVersionUID = 1L;

    private UUID id;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
    private String userName;
    private Boolean isActive;
    private UserProfileDTO userProfile;
    private AddressDTO address;
    @JsonIgnore
    private Set<Role> roles;
    private List<CommentDTO> comments;
    private List<PostDTO> posts;
    private List<PostDTO> likedPosts;
    private List<CommentDTO> likedComments;
    private Set<UserDTO> friends;
    private List<ReplyCommentDTO> replies;
    private Boolean removalFlag;
}
