package ru.philit.ufs.web.mapping.impl;

import java.text.ParseException;
import java.util.Date;
import org.springframework.stereotype.Component;
import ru.philit.ufs.model.entity.order.CashOrder;
import ru.philit.ufs.model.entity.order.CashOrderRequest;
import ru.philit.ufs.web.dto.CashOrderBookDto;
import ru.philit.ufs.web.mapping.CashOrderBookMapper;
import ru.philit.ufs.web.view.GetCashOrderBookReq;

@Component
public class CashOrderBookMapperImpl extends CommonMapperImpl implements CashOrderBookMapper {

  @Override
  public CashOrderRequest asEntity(GetCashOrderBookReq in) {
    if (in == null) {
      return null;
    }
    CashOrderRequest out = new CashOrderRequest();

    if (in.getFromDate() != null) {
      out.setCreatedFrom(convert(in.getFromDate()));
    }
    if (in.getToDate() != null) {
      out.setCreatedTo(convert(in.getToDate()));
    }

    return out;
  }

  @Override
  public CashOrderBookDto asDto(CashOrder in) {
    if (in == null) {
      return null;
    }
    CashOrderBookDto out = new CashOrderBookDto();
    out.setCashOrderId(in.getCashOrderId());
    out.setCashOrderStatus(in.getCashOrderStatus());
    out.setCashOrderType(in.getCashOrderType());
    out.setAmount(in.getAmount());
    out.setRecipientBank(in.getRecipientBank());
    out.setSenderBank(in.getSenderBank());
    out.setCreatedDate(in.getCreatedDttm());
    out.setInn(in.getRepresentative().getInn());

    return out;
  }

  private Date convert(String in) {
    try {
      return asShortDateEntity(in);
    } catch (ParseException e) {
      return null;
    }
  }
}
