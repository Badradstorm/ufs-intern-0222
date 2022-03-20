package ru.philit.ufs.model.converter.esb.asfs;

import ru.philit.ufs.model.converter.esb.mapper.WorkplaceMapper;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRs.SrvGetWorkPlaceInfoRsMessage;
import ru.philit.ufs.model.entity.user.Workplace;

/**
 * Преобразователь между сущностью Workplace и соответствующим транспортным объектом на основе
 * библиотеки mapstruct.
 */
public class WorkPlaceMapstructAdapter extends AsfsAdapter {

  private static final WorkplaceMapper mapper = WorkplaceMapper.INSTANCE;

  /**
   * Преобразует транспортный объект SrvGetWorkPlaceInfoRs во внутреннюю сущность Workplace.
   */
  public static Workplace convert(SrvGetWorkPlaceInfoRs response) {
    SrvGetWorkPlaceInfoRsMessage messageDto = response.getSrvGetWorkPlaceInfoRsMessage();
    Workplace workplace = mapper.toModel(messageDto);
    map(response.getHeaderInfo(), workplace);
    return workplace;
  }

  /**
   * Возвращает объект запроса SrvGetWorkPlaceInfoRq по уникальному номеру УРМ/Кассы.
   */
  public static SrvGetWorkPlaceInfoRq requestById(String workPlaceUId) {
    SrvGetWorkPlaceInfoRq request = new SrvGetWorkPlaceInfoRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvGetWorkPlaceInfoRqMessage(mapper.toDto(workPlaceUId));
    return request;
  }
}
