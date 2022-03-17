package ru.philit.ufs.web.provider;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.philit.ufs.model.cache.CashOrderCache;
import ru.philit.ufs.model.entity.common.OperationTypeCode;
import ru.philit.ufs.model.entity.oper.OperationTaskDeposit;
import ru.philit.ufs.model.entity.order.CashOrder;
import ru.philit.ufs.model.entity.order.CashOrderRequest;
import ru.philit.ufs.model.entity.order.CashOrderStatus;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.util.UuidUtils;
import ru.philit.ufs.web.exception.InvalidDataException;

/**
 * Бизнес-логика работы с кассовыми ордерами.
 */
@Service
public class CashOrderProvider {

  private final CashOrderCache cache;
  private final RepresentativeProvider representativeProvider;
  private final UserProvider userProvider;

  /**
   * Конструктор бина.
   */
  @Autowired
  public CashOrderProvider(CashOrderCache cache,
      RepresentativeProvider representativeProvider,
      UserProvider userProvider) {
    this.cache = cache;
    this.representativeProvider = representativeProvider;
    this.userProvider = userProvider;
  }

  /**
   * Получение информации о кассовых ордерах.
   *
   * @param cashOrderRequest период создания кассовых ордеров
   * @param clientInfo       информация о клиенте
   * @return список кассовых ордеров за запрошенный период
   */
  public List<CashOrder> getCashOrders(CashOrderRequest cashOrderRequest, ClientInfo clientInfo) {
    List<CashOrder> cashOrders = cache.getCashOrders(cashOrderRequest, clientInfo);
    if (cashOrders == null) {
      throw new InvalidDataException(
          "Произошла ошибка при получении");
    }
    if (cashOrders.isEmpty()) {
      throw new InvalidDataException(
          "Кассовые ордеры в указанный период не найдены");
    }
    return cashOrders;
  }

  /**
   * Создание кассового ордера.
   *
   * @param operationTaskDeposit объект операции взноса на счёт/карту
   * @param clientInfo           информация о клиенте
   * @return созданный кассовый ордер
   */
  public CashOrder createCashOrder(OperationTaskDeposit operationTaskDeposit,
      ClientInfo clientInfo, String operationTypeCode) {

    CashOrder cashOrder = new CashOrder();
    cashOrder.setCashOrderId(UuidUtils.getRandomUuid());
    cashOrder.setOperationType(OperationTypeCode.getByCode(operationTypeCode));
    cashOrder.setCashOrderINum(operationTaskDeposit.getOvnNum().toString());
    cashOrder.setAccountId(operationTaskDeposit.getAccountId());
    cashOrder.setAmount(operationTaskDeposit.getAmount());
    cashOrder.setCurrencyType(operationTaskDeposit.getCurrencyType());
    cashOrder.setCashOrderStatus(CashOrderStatus.CREATED);
    cashOrder.setOperator(userProvider.getOperator(clientInfo));

    cashOrder.setRepresentative(
        representativeProvider.getRepresentativeById(operationTaskDeposit.getRepresentativeId(),
            clientInfo));
    cashOrder.getRepresentative().setRepFio(operationTaskDeposit.getRepFio());

    cashOrder.setCashSymbols(operationTaskDeposit.getCashSymbols());
    cashOrder.setComment("no comment");

    cashOrder.setAccount20202Num("11457788");
    cashOrder.setUserLogin(clientInfo.getUser().getLogin());

    cashOrder = cache.saveCashOrder(cashOrder, clientInfo);
    return cashOrder;
  }

  /**
   * Обновление статуса кассового ордера.
   *
   * @param cashOrder  объект кассового ордера
   * @param clientInfo информация о клиенте
   */
  public void updateCashOrder(CashOrder cashOrder, ClientInfo clientInfo) {
    cache.updateCashOrder(cashOrder, clientInfo);
  }
}
