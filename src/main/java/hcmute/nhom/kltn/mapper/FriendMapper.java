package hcmute.nhom.kltn.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import hcmute.nhom.kltn.dto.FriendDTO;
import hcmute.nhom.kltn.mapper.helper.CycleAvoidingMappingContext;
import hcmute.nhom.kltn.model.Friend;

/**
 * Class FriendMapper.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface FriendMapper extends AbstractMapper<FriendDTO, Friend> {
    FriendMapper INSTANCE = Mappers.getMapper(FriendMapper.class);

    @Mapping(target = "user")
    FriendDTO toDto(Friend entity, @Context CycleAvoidingMappingContext context);
}
