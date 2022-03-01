package ru.philit.ufs.model.entity.order;

import java.io.Serializable;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Объект запроса кассового ордера (условия отбора).
 */
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class CashOrderRequest implements Serializable {

  private Date createdFrom;
  private Date createdTo;
}
