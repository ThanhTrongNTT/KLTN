package hcmute.nhom.kltn.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import hcmute.nhom.kltn.dto.RoleDTO;
import hcmute.nhom.kltn.mapper.helper.CycleAvoidingMappingContext;
import hcmute.nhom.kltn.model.Role;

/**
 * Class RoleMapper.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface RoleMapper extends AbstractMapper<RoleDTO, Role> {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    @Mapping(target = "users")
    RoleDTO toDto(Role entity, @Context CycleAvoidingMappingContext context);
}
