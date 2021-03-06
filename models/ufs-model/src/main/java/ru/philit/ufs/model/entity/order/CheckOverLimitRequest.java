package ru.philit.ufs.model.entity.order;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Объект запроса перелимита.
 */
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class CheckOverLimitRequest implements Serializable {

  private String userLogin;
  private boolean tobeIncreased;
  private BigDecimal amount;
}
