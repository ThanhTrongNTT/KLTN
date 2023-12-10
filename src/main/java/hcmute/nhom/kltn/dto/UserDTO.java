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
public class UserDTO extends AbstractDTO {
    private static final long serialVersionUID = 1L;

    private String id;
    @NotNull
    @Email
    private String email;
    @NotNull
    @JsonIgnore
    private String password;
    private String userName;
    private Boolean isActive;
    private UserProfileDTO userProfile;
    @JsonIgnore
    private Set<Role> roles;
    @JsonIgnore
    private List<PostDTO> likedPosts;
    @JsonIgnore
    private List<CommentDTO> likedComments;
    private Boolean removalFlag;
}
