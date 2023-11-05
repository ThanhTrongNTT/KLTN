package hcmute.nhom.kltn.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import hcmute.nhom.kltn.common.AbstractMessage;
import hcmute.nhom.kltn.dto.AbstractNonAuditDTO;
import hcmute.nhom.kltn.exception.SystemErrorException;
import hcmute.nhom.kltn.mapper.AbstractMapper;
import hcmute.nhom.kltn.mapper.helper.CycleAvoidingMappingContext;
import hcmute.nhom.kltn.model.AbstractModel;
import hcmute.nhom.kltn.services.AbstractService;

/**
 * Class AbstractServiceImpl.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Getter
@Setter
public class AbstractServiceImpl<R extends JpaRepository<E, Long>, M extends AbstractMapper<D, E>,
        D extends AbstractNonAuditDTO, E extends AbstractModel>
        extends AbstractMessage
        implements AbstractService<D, E>, MessageSourceAware {
    protected R repository;
    protected D dto;
    protected E entity;
    protected M mapper;
    protected MessageSource messageSource;

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public CycleAvoidingMappingContext getCycleAvoidingMappingContext() {
        return new CycleAvoidingMappingContext();
    }

    protected D getDto() throws SystemErrorException {
        if (getMapper() == null) {
            throw new SystemErrorException("Can not load Mapper");
        }
        return (D) getMapper().toDto(entity, getCycleAvoidingMappingContext());
    }

    protected E getEntity() throws SystemErrorException {
        if (getMapper() == null) {
            throw new SystemErrorException("Can not load Mapper");
        }
        return (E) getMapper().toEntity(dto, getCycleAvoidingMappingContext());
    }

    public void setDTO(D dto) {
        this.dto = dto;
    }

    public void setEntity(E entity) {
        this.entity = entity;
    }

    @Override
    @Transactional
    public D save(D dto) throws SystemErrorException {
        if (dto == null) {
            throw new SystemErrorException("Save not success. DTO is null");
        }

        E item = getMapper().toEntity(dto, getCycleAvoidingMappingContext());
        entity = getRepository().save(item);
        return (D) getMapper().toDto(entity, getCycleAvoidingMappingContext());
    }

    @Override
    @Transactional
    public E save(E entity) throws SystemErrorException {
        if (entity == null) {
            throw new SystemErrorException("Save not success. Entity is null");
        }
        return (E) getRepository().save(entity);
    }

    @Override
    public List<D> save(List<D> listDto) throws SystemErrorException {
        if (listDto == null) {
            throw new SystemErrorException("Save not success. DTOs is null");
        }

        List<E> entities =
                listDto.stream().map(item -> mapper.toEntity(item, getCycleAvoidingMappingContext()))
                        .collect(Collectors.toList());
        entities = getRepository().saveAll(entities);

        return entities.stream()
                .map(item -> mapper.toDto(item, getCycleAvoidingMappingContext()))
                .collect(Collectors.toList());
    }

    @Override
    public D findById(long id) throws SystemErrorException {
        Optional<E> optional = getRepository().findById(id);
        if (optional.isEmpty()) {
            throw new SystemErrorException("Not found entity with id: " + id);
        }
        return (D) getMapper().toDto(optional.get(), getCycleAvoidingMappingContext());
    }

    @Override
    public void delete(long id) throws SystemErrorException {
        try {
            getRepository().deleteById(id);
        } catch (Exception e) {
            throw new SystemErrorException("Delete not success. Error: " + e.getMessage());
        }
    }

    @Override
    public void delete(D dto) throws SystemErrorException {
        try {
            getRepository().delete(getMapper().toEntity(dto, getCycleAvoidingMappingContext()));
        } catch (Exception e) {
            throw new SystemErrorException("Delete not success. Error: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<D> findAll() {
        List<E> list = getRepository().findAll();
        return list.stream()
                .map(item -> getMapper().toDto(item, getCycleAvoidingMappingContext()))
                .collect(Collectors.toList());
    }

    @Override
    public Pageable getPageable(Integer page, Integer size, boolean sortASC, String by) {
        Sort sortable;
        if (by != null) {
            if (sortASC) {
                sortable = Sort.by(by).ascending();
            } else {
                sortable = Sort.by(by).descending();
            }
            return PageRequest.of(page, size, sortable);
        }
        return PageRequest.of(page, size);
    }
}
