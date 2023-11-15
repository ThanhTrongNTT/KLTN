package hcmute.nhom.kltn.services;

import java.util.List;
import java.util.UUID;
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
    /**
     * Save.
     * @param dto : dto
     * @return D
     */
    D save(D dto);

    /**
     * Save.
     * @param entity : entity
     * @return E
     */
    E save(E entity);

    /**
     * Save.
     * @param dtos : dtos
     * @return List<D>
     */
    List<D> save(List<D> dtos);

    /**
     * Save.
     * @param id : id
     * @return D
     */
    D findById(UUID id);

    /**
     * Save.
     * @param id : id
     */
    void delete(UUID id);

    /**
     * Save.
     * @param dto : dto
     */
    void delete(D dto);

    /**
     * Save.
     * @return List<D>
     */
    List<D> findAll();

    /**
     * Save.
     * @param page : page
     * @param size : size
     * @param sortASC : sortASC
     * @param by : by
     * @return
     */
    Pageable getPageable(Integer page, Integer size, boolean sortASC, String by);
}
