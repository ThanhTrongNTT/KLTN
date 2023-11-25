package hcmute.nhom.kltn.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class AbstractDTO.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Getter
@Setter
@NoArgsConstructor
public class AbstractDTO extends AbstractNonAuditDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String createdBy;

    protected Date createdDate;

    protected String modifiedBy;

    protected Date modifiedDate;

    /**
     * Create constructor with parameter.
     * @param createdBy    String
     * @param createdDate  Date
     * @param modifiedBy   String
     * @param modifiedDate Date
     */
    public AbstractDTO(String createdBy, Date createdDate, String modifiedBy, Date modifiedDate) {
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
    }

}
