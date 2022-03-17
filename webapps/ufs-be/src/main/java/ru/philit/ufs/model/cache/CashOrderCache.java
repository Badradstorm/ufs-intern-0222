package ru.philit.ufs.model.cache;

import java.util.List;
import ru.philit.ufs.model.entity.order.CashOrder;
import ru.philit.ufs.model.entity.order.CashOrderRequest;
import ru.philit.ufs.model.entity.user.ClientInfo;

/**
 * Интерфейс доступа к кешу данных для кассовых ордеров.
 */
public interface CashOrderCache {

  CashOrder saveCashOrder(CashOrder cashOrder, ClientInfo clientInfo);

  CashOrder updateCashOrder(CashOrder cashOrder, ClientInfo clientInfo);

  List<CashOrder> getCashOrders(CashOrderRequest cashOrderRequest, ClientInfo clientInfo);
}
