package hcmute.nhom.kltn.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import hcmute.nhom.kltn.dto.MediaFileDTO;
import hcmute.nhom.kltn.mapper.helper.CycleAvoidingMappingContext;
import hcmute.nhom.kltn.model.MediaFile;

/**
 * Class MediaFileMapper.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface MediaFileMapper extends AbstractMapper<MediaFileDTO, MediaFile> {
    MediaFileMapper INSTANCE = Mappers.getMapper(MediaFileMapper.class);

    @Mapping(target = "removalFlag")
    MediaFileDTO toDto(MediaFile entity, @Context CycleAvoidingMappingContext context);
}
