package hcmute.nhom.kltn.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import hcmute.nhom.kltn.dto.AddressDTO;
import hcmute.nhom.kltn.mapper.helper.CycleAvoidingMappingContext;
import hcmute.nhom.kltn.model.Address;

/**
 * Class AddressMapper.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface AddressMapper extends AbstractMapper<AddressDTO, Address> {
    AbstractMapper<AddressDTO, Address> INSTANCE = Mappers.getMapper(AddressMapper.class);


    AddressDTO toDto(Address entity, @Context CycleAvoidingMappingContext context);
}
