package hcmute.nhom.kltn.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import hcmute.nhom.kltn.enums.FriendshipStatus;

/**
 * Class FriendDTO.
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
public class FriendDTO extends AbstractNonAuditDTO {
    private static final long serialVersionUID = 1L;
    private UUID id;
    private UserDTO user;
    private UserDTO friend;
    private FriendshipStatus status;
    private Boolean removalFlag;
}
