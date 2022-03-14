package ru.philit.ufs.model.cache;

import java.math.BigDecimal;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.model.entity.user.Workplace;

/**
 * Интерфейс доступа к кешу данных для рабочих мест.
 */
public interface WorkplaceCache {

  Workplace getWorkplace(String workplaceId, ClientInfo clientInfo);

  boolean checkOverLimit(BigDecimal amount, ClientInfo clientInfo);
}
