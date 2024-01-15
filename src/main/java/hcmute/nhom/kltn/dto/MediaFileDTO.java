package hcmute.nhom.kltn.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import hcmute.nhom.kltn.enums.MediaFileType;

/**
 * Class MediaFileDTO.
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
public class MediaFileDTO extends AbstractNonAuditDTO {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private MediaFileType type;
    private String url;
    private Boolean removalFlag = false;
}
