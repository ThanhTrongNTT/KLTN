package hcmute.nhom.kltn.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import hcmute.nhom.kltn.model.Address;
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

    private Long userId;

    private String email;

    @JsonIgnore
    private String password;

    private Date pwdExpDate;

    private String lastName;

    private String firstName;

    private String phoneNumber;

    private Date birthDay;

    private String avatar;

    private String gender;

    private String description;

    private String userName;

    private Address address;

    @JsonIgnore
    private Set<Role> roles;

    private Boolean removalFlag;
}
