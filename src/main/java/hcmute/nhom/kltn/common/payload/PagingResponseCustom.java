package hcmute.nhom.kltn.common.payload;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class PagingDataCustom.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagingResponseCustom<T> {
    private List<T> content = new ArrayList<>();
    private Boolean last;
    private Boolean first;
    private Integer totalPages;
    private Long totalElements;
    private Integer size;
    private Integer numberOfElements;
    private Boolean empty;
}
