package ru.philit.ufs.model.converter.esb.mapper;

import org.mapstruct.Mapper;
import ru.philit.ufs.model.entity.esb.asfs.CashOrderStatusType;
import ru.philit.ufs.model.entity.order.CashOrderStatus;

@Mapper
public interface CashOrderStatusMapper {

  CashOrderStatusType toDTO(CashOrderStatus cashOrderStatus);

  CashOrderStatus toModel(CashOrderStatusType cashOrderStatusType);
}
