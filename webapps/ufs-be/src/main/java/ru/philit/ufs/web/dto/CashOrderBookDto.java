package ru.philit.ufs.web.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.model.entity.order.CashOrderStatus;
import ru.philit.ufs.model.entity.order.CashOrderType;

/**
 * Объект записи кассовой книги.
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class CashOrderBookDto implements Serializable {

  /**
   * Номер КО.
   */
  private String cashOrderId;
  /**
   * Дата КО.
   */
  private Date createdDate;
  /**
   * Тип КО.
   */
  private CashOrderType cashOrderType;
  /**
   * Статус КО.
   */
  private CashOrderStatus cashOrderStatus;
  /**
   * От кого получено.
   */
  private String senderBank;
  /**
   * Кому выдано.
   */
  private String recipientBank;
  /**
   * ИНН.
   */
  private String inn;
  /**
   * Сумма.
   */
  private BigDecimal amount;

}
