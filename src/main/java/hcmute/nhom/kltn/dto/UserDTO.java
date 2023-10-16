package hcmute.nhom.kltn.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import hcmute.nhom.kltn.enums.GenderType;
import hcmute.nhom.kltn.model.Address;
import hcmute.nhom.kltn.model.Role;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO extends AbstractNonAuditDTO{
    private static final long serialVersionUID = 1L;

    private Long userId;

    private String email;

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

    private Set<Role> roles;

    private Boolean removalFlag;
}
