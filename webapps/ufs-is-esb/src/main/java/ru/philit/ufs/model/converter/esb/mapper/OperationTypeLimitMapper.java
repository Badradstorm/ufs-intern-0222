package ru.philit.ufs.model.converter.esb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRs.SrvGetWorkPlaceInfoRsMessage.WorkPlaceOperationTypeLimit.OperationTypeLimitItem;
import ru.philit.ufs.model.entity.oper.OperationTypeLimit;

@Mapper
public interface OperationTypeLimitMapper {

  @Mapping(source = "operationTypeLimitItem.operationCategory", target = "categoryId")
  @Mapping(source = "operationTypeLimitItem.operationLimit", target = "limit")
  OperationTypeLimit toModel(OperationTypeLimitItem operationTypeLimitItem);
}
