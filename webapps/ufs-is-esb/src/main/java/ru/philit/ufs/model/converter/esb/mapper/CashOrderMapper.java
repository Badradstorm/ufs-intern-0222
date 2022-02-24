package ru.philit.ufs.model.converter.esb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetCashOrderRq.SrvGetCashOrderRqMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetCashOrderRs.SrvGetCashOrderRsMessage.CashOrderItem;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRq.SrvUpdCashOrderRqMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRs.SrvUpdCashOrderRsMessage;
import ru.philit.ufs.model.entity.order.CashOrder;

@Mapper(
    uses = {
        OperationTypeCodeMapper.class,
        CashSymbolMapper.class,
        CashOrderStatusMapper.class,
        RepresentativeMapper.class})
public interface CashOrderMapper {

  CashOrderMapper INSTANCE = Mappers.getMapper(CashOrderMapper.class);

  @Mapping(source = "cashOrder.operator.workplaceId", target = "workPlaceUId")
  @Mapping(source = "cashOrder.comment", target = "additionalInfo.comment")
  @Mapping(source = "cashOrder.account20202Num", target = "additionalInfo.account20202Num")
  @Mapping(source = "cashOrder.userLogin", target = "additionalInfo.userLogin")
  @Mapping(source = "cashOrder.cashSymbols", target = "additionalInfo.cashSymbols.cashSymbolItem")
  @Mapping(source = "cashOrder.representative", target = "repData")
  @Mapping(source = "cashOrder.operator.subbranch", target = "additionalInfo")
  @Mapping(source = "cashOrder.operator.subbranch.subbranchCode", target = "additionalInfo.subbranchCode")
  @Mapping(source = "cashOrder.operator.subbranch.tbCode", target = "additionalInfo.TBCode")
  @Mapping(source = "cashOrder.operator.subbranch.gosbCode", target = "additionalInfo.GOSBCode")
  @Mapping(source = "cashOrder.operator.subbranch.osbCode", target = "additionalInfo.OSBCode")
  @Mapping(source = "cashOrder.operator.subbranch.vspCode", target = "additionalInfo.VSPCode")
  SrvCreateCashOrderRqMessage toCreateCashOrderRqMessage(CashOrder cashOrder);

  SrvGetCashOrderRqMessage toGetCashOrderRqMessage(CashOrder cashOrder);

  SrvUpdCashOrderRqMessage toUpdStCashOrderRqMessage(CashOrder cashOrder);

  @Mapping(source = "messageDTO.cashSymbols.cashSymbolItem", target = "cashSymbols")
  @Mapping(source = "messageDTO.INN", target = "representative.inn")
  @Mapping(source = "messageDTO.subbranchCode", target = "operator.subbranch.subbranchCode")
  @Mapping(source = "messageDTO.userFullName", target = "operator.operatorFullName")
  @Mapping(source = "messageDTO.repFIO", target = "representative.repFIO")
  CashOrder toModel(SrvCreateCashOrderRsMessage messageDTO);

  @Mapping(source = "itemDTO.cashSymbols.cashSymbolItem", target = "cashSymbols")
  @Mapping(source = "itemDTO.INN", target = "representative.inn")
  @Mapping(source = "itemDTO.subbranchCode", target = "operator.subbranch.subbranchCode")
  @Mapping(source = "itemDTO.userFullName", target = "operator.operatorFullName")
  @Mapping(source = "itemDTO.repFIO", target = "representative.repFIO")
  CashOrder toModel(CashOrderItem itemDTO);

  CashOrder toModel(SrvUpdCashOrderRsMessage messageDTO);
}
