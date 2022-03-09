package ru.philit.ufs.model.converter.esb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetCashOrderRs.SrvGetCashOrderRsMessage.CashOrderItem;
import ru.philit.ufs.model.entity.order.CashOrder;

@Mapper(
    uses = {
        CashSymbolMapper.class,
        CashOrderStatusMapper.class})
public interface CashOrderItemMapper {

  @Mapping(source = "cashOrder.representative.repFio", target = "repFIO")
  @Mapping(source = "cashOrder.representative.inn", target = "INN")
  @Mapping(source = "cashOrder.clientTypeFk", target = "clientTypeFK")
  @Mapping(source = "cashOrder.destName", target = "FDestLEName")
  @Mapping(source = "cashOrder.operator.subbranch.subbranchCode", target = "subbranchCode")
  @Mapping(source = "cashOrder.operator.operatorFullName", target = "userFullName")
  @Mapping(source = "cashOrder.cashSymbols", target = "cashSymbols.cashSymbolItem")
  @Mapping(source = "cashOrder.senderBankBic", target = "senderBankBIC")
  @Mapping(source = "cashOrder.recipientBankBic", target = "recipientBankBIC")
  CashOrderItem toDto(CashOrder cashOrder);
}
