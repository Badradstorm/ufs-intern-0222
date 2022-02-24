package ru.philit.ufs.model.converter.esb.asfs;

import ru.philit.ufs.model.converter.esb.mapper.CashOrderMapper;
import ru.philit.ufs.model.entity.common.ExternalEntityList;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetCashOrderRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetCashOrderRq.SrvGetCashOrderRqMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetCashOrderRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetCashOrderRs.SrvGetCashOrderRsMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRq.SrvUpdCashOrderRqMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRs.SrvUpdCashOrderRsMessage;
import ru.philit.ufs.model.entity.order.CashOrder;

/**
 * Преобразователь между сущностью CashOrder и соответствующим транспортным объектом.
 */
public class CashOrderAdapter extends AsfsAdapter {

  private static final CashOrderMapper mapper = CashOrderMapper.INSTANCE;

  /**
   * Преобразует транспортный объект SrvCreateCashOrderRs во внутреннюю сущность CashOrder.
   */
  public static CashOrder convert(SrvCreateCashOrderRs response) {
    SrvCreateCashOrderRsMessage messageDTO = response.getSrvCreateCashOrderRsMessage();
    CashOrder cashOrder = mapper.toModel(messageDTO);
    map(response.getHeaderInfo(), cashOrder);
    return cashOrder;
  }

  /**
   * Преобразует транспортный объект SrvGetCashOrderRs в контейнер для списка внутренних сущностей.
   */
  public static ExternalEntityList<CashOrder> convert(SrvGetCashOrderRs response) {
    ExternalEntityList<CashOrder> cashOrders = new ExternalEntityList<>();
    for (SrvGetCashOrderRsMessage.CashOrderItem cashOrderItem
        : response.getSrvGetCashOrderRsMessage().getCashOrderItem()) {
      CashOrder cashOrder = mapper.toModel(cashOrderItem);
      map(response.getHeaderInfo(), cashOrder);
      cashOrders.getItems().add(cashOrder);
    }
    map(response.getHeaderInfo(), cashOrders);
    return cashOrders;
  }

  /**
   * Преобразует транспортный объект SrvUpdCashOrderRs во внутреннюю сущность CashOrder.
   */
  public static CashOrder convert(SrvUpdStCashOrderRs response) {
    SrvUpdCashOrderRsMessage messageDTO = response.getSrvUpdCashOrderRsMessage();
    CashOrder cashOrder = mapper.toModel(messageDTO);
    map(response.getHeaderInfo(), cashOrder);
    return cashOrder;
  }

  /**
   * Возвращает объект запроса создания кассового ордера.
   */
  public static SrvCreateCashOrderRq requestCreate(CashOrder cashOrder) {
    SrvCreateCashOrderRq request = new SrvCreateCashOrderRq();
    SrvCreateCashOrderRqMessage message = mapper.toCreateCashOrderRqMessage(cashOrder);
    request.setHeaderInfo(headerInfo());
    request.setSrvCreateCashOrderRqMessage(message);
    return request;
  }

  /**
   * Возвращает объект запроса получения кассового ордера.
   */
  public static SrvGetCashOrderRq requestGet(CashOrder cashOrder) {
    SrvGetCashOrderRq request = new SrvGetCashOrderRq();
    SrvGetCashOrderRqMessage message = mapper.toGetCashOrderRqMessage(cashOrder);
    request.setHeaderInfo(headerInfo());
    request.setSrvGetCashOrderRqMessage(message);
    return request;
  }

  /**
   * Возвращает объект запроса обновления статуса кассового ордера.
   */
  public static SrvUpdStCashOrderRq requestUpdate(CashOrder cashOrder) {
    SrvUpdStCashOrderRq request = new SrvUpdStCashOrderRq();
    SrvUpdCashOrderRqMessage message = mapper.toUpdStCashOrderRqMessage(cashOrder);
    request.setHeaderInfo(headerInfo());
    request.setSrvUpdCashOrderRqMessage(message);
    return request;
  }
}