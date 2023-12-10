package hcmute.nhom.kltn.common.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import hcmute.nhom.kltn.dto.PostDTO;

/**
 * Class NewPostRequest.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class NewPostRequest {
    private PostDTO postDTO;
    private String userName;
}
