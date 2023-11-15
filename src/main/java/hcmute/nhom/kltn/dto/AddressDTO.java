package hcmute.nhom.kltn.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class AddressDTO.
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
public class AddressDTO extends AbstractNonAuditDTO {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String street;
    private String district;
    private String city;
    private String country;
    private String postalCode;
    private Boolean removalFlag;
}
