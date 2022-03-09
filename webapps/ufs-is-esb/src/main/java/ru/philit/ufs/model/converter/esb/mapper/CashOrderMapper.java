package ru.philit.ufs.model.converter.esb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.philit.ufs.model.entity.common.ExternalEntityContainer;
import ru.philit.ufs.model.entity.common.ExternalEntityList;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRq.SrvCheckOverLimitRqMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRs.SrvCheckOverLimitRsMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetCashOrderRq.SrvGetCashOrderRqMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetCashOrderRs.SrvGetCashOrderRsMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetCashOrderRs.SrvGetCashOrderRsMessage.CashOrderItem;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRq.SrvUpdCashOrderRqMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRs.SrvUpdCashOrderRsMessage;
import ru.philit.ufs.model.entity.order.CashOrder;
import ru.philit.ufs.model.entity.order.CashOrderRequest;

@Mapper(
    uses = {
        OperationTypeCodeMapper.class,
        CashSymbolMapper.class,
        CashOrderStatusMapper.class,
        RepresentativeMapper.class,
        LimitStatusTypeMapper.class,
        CashOrderItemMapper.class})
public interface CashOrderMapper {

  CashOrderMapper INSTANCE = Mappers.getMapper(CashOrderMapper.class);

  @Mapping(source = "cashOrder.operator.workplaceId", target = "workPlaceUId")
  @Mapping(source = "cashOrder.comment", target = "additionalInfo.comment")
  @Mapping(source = "cashOrder.account20202Num", target = "additionalInfo.account20202Num")
  @Mapping(source = "cashOrder.userLogin", target = "additionalInfo.userLogin")
  @Mapping(source = "cashOrder.cashSymbols", target = "additionalInfo.cashSymbols.cashSymbolItem")
  @Mapping(source = "cashOrder.representative", target = "repData")
  @Mapping(source = "cashOrder.operator.subbranch", target = "additionalInfo")
  @Mapping(
      source = "cashOrder.operator.subbranch.subbranchCode",
      target = "additionalInfo.subbranchCode")
  @Mapping(source = "cashOrder.operator.subbranch.tbCode", target = "additionalInfo.TBCode")
  @Mapping(source = "cashOrder.operator.subbranch.gosbCode", target = "additionalInfo.GOSBCode")
  @Mapping(source = "cashOrder.operator.subbranch.osbCode", target = "additionalInfo.OSBCode")
  @Mapping(source = "cashOrder.operator.subbranch.vspCode", target = "additionalInfo.VSPCode")
  SrvCreateCashOrderRqMessage toCreateCashOrderRqMessage(CashOrder cashOrder);

  SrvGetCashOrderRqMessage toGetCashOrderRqMessage(CashOrderRequest cashOrderRequest);

  SrvUpdCashOrderRqMessage toUpdStCashOrderRqMessage(CashOrder cashOrder);

  SrvCheckOverLimitRqMessage toCheckOverLimitRqMessage(CashOrder cashOrder);

  @Mapping(source = "cashOrder.representative.repFio", target = "repFIO")
  @Mapping(source = "cashOrder.representative.inn", target = "INN")
  @Mapping(source = "cashOrder.clientTypeFk", target = "clientTypeFK")
  @Mapping(source = "cashOrder.destName", target = "FDestLEName")
  @Mapping(source = "cashOrder.operator.subbranch.subbranchCode", target = "subbranchCode")
  @Mapping(source = "cashOrder.operator.operatorFullName", target = "userFullName")
  @Mapping(source = "cashOrder.cashSymbols", target = "cashSymbols.cashSymbolItem")
  @Mapping(source = "cashOrder.senderBankBic", target = "senderBankBIC")
  @Mapping(source = "cashOrder.recipientBankBic", target = "recipientBankBIC")
  SrvCreateCashOrderRsMessage toCreateCashOrderRsMessage(CashOrder cashOrder);

  SrvUpdCashOrderRsMessage toUpdStCashOrderRs(CashOrder cashOrder);

  @Mapping(source = "items", target = "cashOrderItem")
  SrvGetCashOrderRsMessage toGetStCashOrderRs(ExternalEntityList<CashOrder> cashOrders);

  @Mapping(source = "messageDto.cashSymbols.cashSymbolItem", target = "cashSymbols")
  @Mapping(source = "messageDto.INN", target = "representative.inn")
  @Mapping(source = "messageDto.subbranchCode", target = "operator.subbranch.subbranchCode")
  @Mapping(source = "messageDto.userFullName", target = "operator.operatorFullName")
  @Mapping(source = "messageDto.repFIO", target = "representative.repFio")
  @Mapping(source = "messageDto.senderBankBIC", target = "senderBankBic")
  @Mapping(source = "messageDto.recipientBankBIC", target = "recipientBankBic")
  @Mapping(source = "messageDto.clientTypeFK", target = "clientTypeFk")
  @Mapping(source = "messageDto.FDestLEName", target = "destName")
  CashOrder toModel(SrvCreateCashOrderRsMessage messageDto);

  @Mapping(source = "itemDto.cashSymbols.cashSymbolItem", target = "cashSymbols")
  @Mapping(source = "itemDto.INN", target = "representative.inn")
  @Mapping(source = "itemDto.subbranchCode", target = "operator.subbranch.subbranchCode")
  @Mapping(source = "itemDto.userFullName", target = "operator.operatorFullName")
  @Mapping(source = "itemDto.repFIO", target = "representative.repFio")
  @Mapping(source = "itemDto.senderBankBIC", target = "senderBankBic")
  @Mapping(source = "itemDto.recipientBankBIC", target = "recipientBankBic")
  @Mapping(source = "itemDto.clientTypeFK", target = "clientTypeFk")
  @Mapping(source = "itemDto.FDestLEName", target = "destName")
  CashOrder toModel(CashOrderItem itemDto);

  CashOrder toModel(SrvUpdCashOrderRsMessage messageDto);

  @Mapping(source = "messageDto.status", target = "data")
  ExternalEntityContainer<Boolean> toModel(SrvCheckOverLimitRsMessage messageDto);

  @Mapping(target = "responseCode", constant = "200")
  @Mapping(target = "responseMsg", constant = "ok")
  @Mapping(source = "messageDto.workPlaceUId", target = "operator.workplaceId")
  @Mapping(source = "messageDto.repData", target = "representative")
  @Mapping(source = "messageDto.additionalInfo.comment", target = "comment")
  @Mapping(source = "messageDto.additionalInfo.cashSymbols.cashSymbolItem", target = "cashSymbols")
  @Mapping(
      source = "messageDto.additionalInfo.subbranchCode",
      target = "operator.subbranch.subbranchCode")
  @Mapping(source = "messageDto.additionalInfo.VSPCode", target = "operator.subbranch.vspCode")
  @Mapping(source = "messageDto.additionalInfo.OSBCode", target = "operator.subbranch.osbCode")
  @Mapping(source = "messageDto.additionalInfo.GOSBCode", target = "operator.subbranch.gosbCode")
  @Mapping(source = "messageDto.additionalInfo.TBCode", target = "operator.subbranch.tbCode")
  @Mapping(source = "messageDto.additionalInfo.account20202Num", target = "account20202Num")
  @Mapping(source = "messageDto.additionalInfo.userLogin", target = "userLogin")
  CashOrder toModel(SrvCreateCashOrderRqMessage messageDto);
}
