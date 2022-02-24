package ru.philit.ufs.model.converter.esb.mapper;

import org.mapstruct.Mapper;
import ru.philit.ufs.model.entity.common.OperationTypeCode;
import ru.philit.ufs.model.entity.esb.asfs.OperTypeLabel;

@Mapper
public interface OperationTypeCodeMapper {

  OperTypeLabel toDTO(OperationTypeCode operationTypeCode);
}
