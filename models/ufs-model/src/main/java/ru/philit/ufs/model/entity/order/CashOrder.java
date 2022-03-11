package ru.philit.ufs.model.entity.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.model.entity.account.Representative;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.common.OperationTypeCode;
import ru.philit.ufs.model.entity.oper.CashSymbol;
import ru.philit.ufs.model.entity.user.Operator;

/**
 * Сущность кассового ордера.
 */
@EqualsAndHashCode(of = {"cashOrderId"}, callSuper = false)
@ToString
@Getter
@Setter
public class CashOrder extends ExternalEntity {

  private String responseCode; // Код ответа
  private String responseMsg; // Сообщение ответа
  private String comment; // Комментарий (источник поступления/цель расхода)
  private String cashOrderId; // Номер заявки в ЕФС для кассового ордера
  private OperationTypeCode operationType; // Тип операции
  private String cashOrderINum; // Номер документа ( = номеру ОВН только для приходной операции)
  private String accountId; // Номер счета
  private BigDecimal amount; // Сумма
  private String amountInWords; // Сумма прописью
  private String currencyType; // Код валюты
  protected List<CashSymbol> cashSymbols; // Кассовый символ операции.
  private CashOrderStatus cashOrderStatus; // Статус кассового ордера
  private CashOrderType cashOrderType; // Тип кассового ордера
  private Date createdDttm; // Дата создания
  private String operationId; // Номер кассовой операции
  private String senderBank; // Банк-отправитель
  private String senderBankBic; // БИК банка-отправителя
  private String recipientBank; // Банк-получатель
  private String recipientBankBic; // БИК банка-получателя
  private Boolean clientTypeFk; // Признак типа клиента ФК/ОФК/УОВФ
  private String destName; // Наименование организации в ФК/ОФК/УОВФ
  private Representative representative; // Представитель корпоративного бизнеса
  private Operator operator; // Оператор
  private String account20202Num; // Счет № 20202 "Касса кредитных организаций"
  private String userLogin; // Логин оператора
  private String userPosition; // Должность кассового работника
  private String legalEntityShortName;

  /**
   * Конструктор кассового ордера.
   */
  public CashOrder() {
    this.operator = new Operator();
    this.representative = new Representative();
  }
}
