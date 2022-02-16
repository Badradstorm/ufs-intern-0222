package ru.philit.ufs.model.entity.order;

import com.google.common.base.MoreObjects;

/**
 * Статус кассового ордера.
 */
public enum CashOrderStatus {

  CREATED("Created"),
  COMMITTED("Committed");

  private final String value;

  CashOrderStatus(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("value", value()).toString();
  }

  public String value() {
    return value;
  }

}
