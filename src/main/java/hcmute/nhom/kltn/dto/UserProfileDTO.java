package hcmute.nhom.kltn.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import hcmute.nhom.kltn.enums.GenderType;

/**
 * Class UserProfileDTO.
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
public class UserProfileDTO extends AbstractNonAuditDTO {
    private static final long serialVersionUID = 1L;
    private UUID id;
    private String lastName;
    private String firstName;
    private String phoneNumber;
    private Date birthDay;
    private String avatar;
    private GenderType gender;
    private String description;
    private UserDTO user;
    private Boolean removalFlag;
}
