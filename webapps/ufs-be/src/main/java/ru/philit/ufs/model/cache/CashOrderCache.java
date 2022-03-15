package ru.philit.ufs.model.cache;

import java.math.BigDecimal;
import ru.philit.ufs.model.entity.user.ClientInfo;

/**
 * Интерфейс доступа к кешу данных для кассовых ордеров.
 */
public interface CashOrderCache {

  boolean checkOverLimit(BigDecimal amount, ClientInfo clientInfo);
}
