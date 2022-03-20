package ru.philit.ufs.model.converter.esb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRq.SrvGetWorkPlaceInfoRqMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRs.SrvGetWorkPlaceInfoRsMessage;
import ru.philit.ufs.model.entity.user.Workplace;

@Mapper(
    uses = {
        WorkplaceTypeMapper.class,
        OperationTypeLimitMapper.class})
public interface WorkplaceMapper {

  WorkplaceMapper INSTANCE = Mappers.getMapper(WorkplaceMapper.class);

  @Mapping(source = "messageDto.workPlaceLimit", target = "limit")
  @Mapping(source = "messageDto.currentCurrencyType", target = "currencyType")
  @Mapping(source = "messageDto.cashboxOnBoardDevice", target = "cashboxDeviceId")
  @Mapping(source = "messageDto.workPlaceType", target = "type")
  @Mapping(
      source = "messageDto.workPlaceOperationTypeLimit.operationTypeLimitItem",
      target = "categoryLimits")
  Workplace toModel(SrvGetWorkPlaceInfoRsMessage messageDto);

  SrvGetWorkPlaceInfoRqMessage toDto(String workPlaceUId);
}
