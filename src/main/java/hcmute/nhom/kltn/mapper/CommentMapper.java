package hcmute.nhom.kltn.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import hcmute.nhom.kltn.dto.CommentDTO;
import hcmute.nhom.kltn.mapper.helper.CycleAvoidingMappingContext;
import hcmute.nhom.kltn.model.Comment;

/**
 * Class CommentMapper.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface CommentMapper extends AbstractMapper<CommentDTO, Comment> {
    CommentMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(CommentMapper.class);

    @Mapping(target = "content")
    CommentDTO toDto(Comment entity, @Context CycleAvoidingMappingContext context);
}
