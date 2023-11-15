package hcmute.nhom.kltn.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import hcmute.nhom.kltn.dto.PostDTO;
import hcmute.nhom.kltn.mapper.helper.CycleAvoidingMappingContext;
import hcmute.nhom.kltn.model.Post;

/**
 * Class PostMapper.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface PostMapper extends AbstractMapper<PostDTO, Post> {
    PostMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(PostMapper.class);

    @Mapping(target = "content")
    PostDTO toDto(Post entity, @Context CycleAvoidingMappingContext context);
}
