package ru.philit.ufs.model.converter.esb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.philit.ufs.model.entity.esb.asfs.CashOrderStatusType;
import ru.philit.ufs.model.entity.order.CashOrderStatus;

@Mapper
public interface CashOrderStatusMapper {

  CashOrderStatusMapper INSTANCE = Mappers.getMapper(CashOrderStatusMapper.class);

  CashOrderStatusType toDto(CashOrderStatus cashOrderStatus);

  CashOrderStatus toModel(CashOrderStatusType cashOrderStatusType);
}
