package ru.philit.ufs.model.entity.order;

import com.google.common.base.MoreObjects;

/**
 * Тип кассового ордера.
 */
public enum CashOrderType {

  KO_1("KO1"),
  KO_2("KO2");

  private final String value;

  CashOrderType(String value) {
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
