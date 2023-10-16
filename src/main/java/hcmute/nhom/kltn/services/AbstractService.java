package hcmute.nhom.kltn.services;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.domain.Pageable;
import hcmute.nhom.kltn.dto.AbstractNonAuditDTO;
import hcmute.nhom.kltn.model.AbstractModel;

/**
 * Class AbstractService.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
public interface AbstractService<D extends AbstractNonAuditDTO, E extends AbstractModel> {
    D save(D dto);
    E save(E entity);
    List<D> save(List<D> dtos);
    D findById(long id);
    void delete(long id);
    void delete(D dto);
    List<D> findAll();
    Pageable getPageable(Integer page, Integer size, boolean sortASC, String by);
}
