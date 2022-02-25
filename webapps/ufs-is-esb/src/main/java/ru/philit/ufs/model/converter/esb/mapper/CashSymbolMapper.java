package ru.philit.ufs.model.converter.esb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage.AdditionalInfo.CashSymbols.CashSymbolItem;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetCashOrderRs.SrvGetCashOrderRsMessage.CashOrderItem;
import ru.philit.ufs.model.entity.oper.CashSymbol;

@Mapper
public interface CashSymbolMapper {

  @Mapping(source = "cashSymbol.code", target = "cashSymbol")
  @Mapping(source = "cashSymbol.amount", target = "cashSymbolAmount")
  CashSymbolItem toDto(CashSymbol cashSymbol);

  @Mapping(source = "cashSymbolItem.cashSymbol", target = "code")
  @Mapping(source = "cashSymbolItem.cashSymbolAmount", target = "amount")
  CashSymbol toModel(SrvCreateCashOrderRsMessage.CashSymbols.CashSymbolItem cashSymbolItem);

  @Mapping(source = "cashOrderItem.cashSymbol", target = "code")
  @Mapping(source = "cashOrderItem.cashSymbolAmount", target = "amount")
  CashSymbol toModel(CashOrderItem.CashSymbols.CashSymbolItem cashOrderItem);
}
