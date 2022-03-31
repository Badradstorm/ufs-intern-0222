package ru.philit.ufs.web.mapping;

import ru.philit.ufs.model.entity.order.CashOrder;
import ru.philit.ufs.model.entity.order.CashOrderRequest;
import ru.philit.ufs.web.dto.CashOrderBookDto;
import ru.philit.ufs.web.view.GetCashOrderBookReq;

/**
 * Конвертер для кассовой книги.
 */
public interface CashOrderBookMapper {

  CashOrderBookDto asDto(CashOrder in);

  CashOrderRequest asEntity(GetCashOrderBookReq in);

}
