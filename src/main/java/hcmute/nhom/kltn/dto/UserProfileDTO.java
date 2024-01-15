package hcmute.nhom.kltn.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
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
public class UserProfileDTO extends AbstractDTO {
    private static final long serialVersionUID = 1L;
    private String id;
    private String lastName;
    private String firstName;
    private String phoneNumber;
    private Date birthDay;
    private MediaFileDTO avatar;
    private MediaFileDTO cover;
    private GenderType gender;
    private String description;
    private AddressDTO address;
    @JsonIgnore
    private UserDTO user;
    private Boolean removalFlag = false;
}
