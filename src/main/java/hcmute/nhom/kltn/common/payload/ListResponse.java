package hcmute.nhom.kltn.common.payload;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class ListResponse.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListResponse<T> {
    private List<T> content = new ArrayList<>();
    private Integer totalElements;
    private Boolean empty;
}
