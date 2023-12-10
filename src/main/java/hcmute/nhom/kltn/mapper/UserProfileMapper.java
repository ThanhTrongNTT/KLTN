package hcmute.nhom.kltn.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import hcmute.nhom.kltn.dto.UserProfileDTO;
import hcmute.nhom.kltn.mapper.helper.CycleAvoidingMappingContext;
import hcmute.nhom.kltn.model.UserProfile;

/**
 * Class UserProfileMapper.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface UserProfileMapper extends AbstractMapper<UserProfileDTO, UserProfile> {
    UserProfileMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(UserProfileMapper.class);

    @Mapping(target = "user")
    UserProfileDTO toDto(UserProfile entity, @Context CycleAvoidingMappingContext context);
}
