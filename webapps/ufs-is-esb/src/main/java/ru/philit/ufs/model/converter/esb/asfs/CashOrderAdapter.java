package ru.philit.ufs.model.converter.esb.asfs;

import java.util.List;
import ru.philit.ufs.model.converter.esb.mapper.CashOrderMapper;
import ru.philit.ufs.model.entity.common.ExternalEntityContainer;
import ru.philit.ufs.model.entity.common.ExternalEntityList;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRq.SrvCheckOverLimitRqMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRs.SrvCheckOverLimitRsMessage;
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
import ru.philit.ufs.model.entity.order.CashOrderRequest;

/**
 * Преобразователь между сущностью CashOrder и соответствующим транспортным объектом.
 */
public class CashOrderAdapter extends AsfsAdapter {

  private static final CashOrderMapper mapper = CashOrderMapper.INSTANCE;

  /**
   * Преобразует транспортный объект SrvCreateCashOrderRs во внутреннюю сущность CashOrder.
   */
  public static CashOrder convert(SrvCreateCashOrderRs response) {
    SrvCreateCashOrderRsMessage messageDto = response.getSrvCreateCashOrderRsMessage();
    CashOrder cashOrder = mapper.toModel(messageDto);
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
    SrvUpdCashOrderRsMessage messageDto = response.getSrvUpdCashOrderRsMessage();
    CashOrder cashOrder = mapper.toModel(messageDto);
    map(response.getHeaderInfo(), cashOrder);
    return cashOrder;
  }

  /**
   * Преобразует транспортный объект SrvCheckOverLimitRs в контейнер.
   */
  public static ExternalEntityContainer<Boolean> convert(SrvCheckOverLimitRs response) {
    SrvCheckOverLimitRsMessage messageDto = response.getSrvCheckOverLimitRsMessage();
    ExternalEntityContainer<Boolean> container = mapper.toModel(messageDto);
    map(response.getHeaderInfo(), container);
    return container;
  }

  /**
   * Преобразует транспортный объект SrvCreateCashOrderRq во внутреннюю сущность CashOrder.
   */
  public static CashOrder convert(SrvCreateCashOrderRq request) {
    CashOrder cashOrder = mapper.toModel(request.getSrvCreateCashOrderRqMessage());
    map(request.getHeaderInfo(), cashOrder);
    return cashOrder;
  }

  /**
   * Возвращает объект запроса создания кассового ордера.
   */
  public static SrvCreateCashOrderRq toRequestCreate(CashOrder cashOrder) {
    SrvCreateCashOrderRq request = new SrvCreateCashOrderRq();
    SrvCreateCashOrderRqMessage message = mapper.toCreateCashOrderRqMessage(cashOrder);
    request.setHeaderInfo(headerInfo());
    request.setSrvCreateCashOrderRqMessage(message);
    return request;
  }

  /**
   * Возвращает объект запроса получения кассовых ордеров.
   */
  public static SrvGetCashOrderRq toRequestGet(CashOrderRequest cashOrderRequest) {
    SrvGetCashOrderRq request = new SrvGetCashOrderRq();
    SrvGetCashOrderRqMessage message = mapper.toGetCashOrderRqMessage(cashOrderRequest);
    request.setHeaderInfo(headerInfo());
    request.setSrvGetCashOrderRqMessage(message);
    return request;
  }

  /**
   * Возвращает объект запроса обновления статуса кассового ордера.
   */
  public static SrvUpdStCashOrderRq toRequestUpdate(CashOrder cashOrder) {
    SrvUpdStCashOrderRq request = new SrvUpdStCashOrderRq();
    SrvUpdCashOrderRqMessage message = mapper.toUpdStCashOrderRqMessage(cashOrder);
    request.setHeaderInfo(headerInfo());
    request.setSrvUpdCashOrderRqMessage(message);
    return request;
  }

  /**
   * Возвращает объект запроса обновления статуса кассового ордера.
   */
  public static SrvCheckOverLimitRq toRequestCheckOverLimit(CashOrder cashOrder) {
    SrvCheckOverLimitRq request = new SrvCheckOverLimitRq();
    SrvCheckOverLimitRqMessage message = mapper.toCheckOverLimitRqMessage(cashOrder);
    request.setHeaderInfo(headerInfo());
    request.setSrvCheckOverLimitRqMessage(message);
    return request;
  }

  /**
   * Возвращает объект ответа создания кассового ордера.
   */
  public static SrvCreateCashOrderRs toResponseCreate(CashOrder cashOrder) {
    SrvCreateCashOrderRs response = new SrvCreateCashOrderRs();
    SrvCreateCashOrderRsMessage message = mapper.toCreateCashOrderRsMessage(cashOrder);
    response.setHeaderInfo(headerInfo());
    response.setSrvCreateCashOrderRsMessage(message);
    return response;
  }

  /**
   * Возвращает объект ответа обновления статуса кассового ордера.
   */
  public static SrvUpdStCashOrderRs toResponseUpdate(CashOrder cashOrder) {
    SrvUpdStCashOrderRs response = new SrvUpdStCashOrderRs();
    SrvUpdCashOrderRsMessage message = mapper.toUpdStCashOrderRs(cashOrder);
    response.setHeaderInfo(headerInfo());
    response.setSrvUpdCashOrderRsMessage(message);
    return response;
  }

  /**
   * Возвращает объект ответа получения кассовых ордеров.
   */
  public static SrvGetCashOrderRs toResponseGet(ExternalEntityList<CashOrder> cashOrders) {
    SrvGetCashOrderRs response = new SrvGetCashOrderRs();
    SrvGetCashOrderRsMessage message = mapper.toGetStCashOrderRs(cashOrders);
    response.setHeaderInfo(headerInfo());
    response.setSrvGetCashOrderRsMessage(message);
    return response;
  }
}