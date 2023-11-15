package hcmute.nhom.kltn.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import hcmute.nhom.kltn.dto.ReplyCommentDTO;
import hcmute.nhom.kltn.mapper.helper.CycleAvoidingMappingContext;
import hcmute.nhom.kltn.model.ReplyComment;

/**
 * Class ReplyCommentMapper.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface ReplyCommentMapper extends AbstractMapper<ReplyCommentDTO, ReplyComment> {
    ReplyCommentMapper INSTANCE = Mappers.getMapper(ReplyCommentMapper.class);

    @Mapping(target = "author")
    ReplyCommentDTO toDto(ReplyComment entity, @Context CycleAvoidingMappingContext context);
}
