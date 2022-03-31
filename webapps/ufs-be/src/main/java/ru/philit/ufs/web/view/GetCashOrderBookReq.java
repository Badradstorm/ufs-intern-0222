package ru.philit.ufs.web.view;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.web.dto.BaseRequest;

/**
 * Запрос для метода {@link ru.philit.ufs.web.controller.ReportController#getCashOrderBook}
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class GetCashOrderBookReq  extends BaseRequest {

  /**
   * Кассовые ордера с даты.
   */
  private String fromDate;
  /**
   * Кассовые ордера по дату.
   */
  private String toDate;
}
