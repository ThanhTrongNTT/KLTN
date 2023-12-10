package hcmute.nhom.kltn.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class RoleDTO.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDTO extends AbstractDTO {
    private static final long serialVersionUID = 1L;

    private String id;
    private String roleName;
    private Integer adminFlag;
    private Set<UserDTO> users;
    private Boolean removalFlag = false;
}

