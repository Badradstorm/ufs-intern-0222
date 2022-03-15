package ru.philit.ufs.model.cache;

import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.model.entity.user.Workplace;

/**
 * Интерфейс доступа к кешу данных для рабочих мест.
 */
public interface WorkplaceCache {

  Workplace getWorkplace(String workplaceId, ClientInfo clientInfo);
}
