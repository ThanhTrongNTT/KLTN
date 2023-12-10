package hcmute.nhom.kltn.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import hcmute.nhom.kltn.dto.UserDTO;
import hcmute.nhom.kltn.mapper.helper.CycleAvoidingMappingContext;
import hcmute.nhom.kltn.model.User;

/**
 * Class UserMapper.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface UserMapper extends AbstractMapper<UserDTO, User> {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "email")
    UserDTO toDto(User entity, @Context CycleAvoidingMappingContext context);
}
