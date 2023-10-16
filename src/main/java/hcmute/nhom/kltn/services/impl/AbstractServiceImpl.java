package hcmute.nhom.kltn.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import hcmute.nhom.kltn.common.AbstractMessage;
import hcmute.nhom.kltn.dto.AbstractNonAuditDTO;
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
public class AbstractServiceImpl<R extends JpaRepository, M extends AbstractMapper,
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

    public R getRepository() {
        return repository;
    }

    public M getMapper() {
        return mapper;
    }

    public CycleAvoidingMappingContext getCycleAvoidingMappingContext() {
        return new CycleAvoidingMappingContext();
    }

    protected D getDto() {
        if (getMapper() == null) {
            throw new RuntimeException("Can not load Mapper");
        }
        return (D) getMapper().toDto(entity, getCycleAvoidingMappingContext());
    }
    protected E getEntity() {
        if (getMapper() == null) {
            throw new RuntimeException("Can not load Mapper");
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
    public D save(D dto) {
        if (dto == null) {
            throw new RuntimeException("Save not success. DTO is null");
        }

        var entity = getMapper().toEntity(dto, getCycleAvoidingMappingContext());
        entity = (AbstractModel) getRepository().save(entity);
        return (D) getMapper().toDto(entity, getCycleAvoidingMappingContext());
    }

    @Override
    @Transactional
    public E save(E entity) {
        if (entity == null) {
            throw new RuntimeException("Save not success. Entity is null");
        }
        return (E) getRepository().save(entity);
    }

    @Override
    public List<D> save(List<D> dtos) {
        if (dtos == null) {
            throw new RuntimeException("Save not success. DTOs is null");
        }

        List<E> entities =
                (List<E>) dtos.stream().map(dto -> mapper.toEntity(dto, getCycleAvoidingMappingContext()))
                        .collect(Collectors.toList());
        entities = getRepository().saveAll(entities);

        return (List<D>) entities.stream()
                .map(entity -> mapper.toDto(entity, getCycleAvoidingMappingContext()))
                .collect(Collectors.toList());
    }

    @Override
    public D findById(long id) {
        Optional<E> optional = getRepository().findById(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("Not found entity with id: " + id);
        }
        return (D) getMapper().toDto(optional.get(), getCycleAvoidingMappingContext());
    }

    @Override
    public void delete(long id) {
        try {
            getRepository().deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete not success. Error: " + e.getMessage());
        }
    }

    @Override
    public void delete(D dto) {
        try {
            getRepository().delete(getMapper().toEntity(dto, getCycleAvoidingMappingContext()));
        } catch (Exception e) {
            throw new RuntimeException("Delete not success. Error: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<D> findAll() {
        List<E> list = getRepository().findAll();
        return (List<D>) list.stream()
                .map(entity -> getMapper().toDto(entity, getCycleAvoidingMappingContext()))
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
