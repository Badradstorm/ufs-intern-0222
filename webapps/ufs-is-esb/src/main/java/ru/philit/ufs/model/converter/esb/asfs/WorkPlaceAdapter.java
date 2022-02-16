package ru.philit.ufs.model.converter.esb.asfs;

import java.util.ArrayList;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRq.SrvGetWorkPlaceInfoRqMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRs.SrvGetWorkPlaceInfoRsMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRs.SrvGetWorkPlaceInfoRsMessage.WorkPlaceOperationTypeLimit.OperationTypeLimitItem;
import ru.philit.ufs.model.entity.oper.OperationTypeLimit;
import ru.philit.ufs.model.entity.user.Workplace;
import ru.philit.ufs.model.entity.user.WorkplaceType;

/**
 * Преобразователь между сущностью Workplace и соответствующим транспортным объектом.
 */
public class WorkPlaceAdapter extends AsfsAdapter {

  private static void map(SrvGetWorkPlaceInfoRsMessage response,
      Workplace workplace) {
    workplace.setType(WorkplaceType.getByCode((response.getWorkPlaceType().intValue())));
    workplace.setCashboxOnBoard(response.isCashboxOnBoard());
    workplace.setSubbranchCode(response.getSubbranchCode());
    workplace.setCashboxDeviceId(response.getCashboxOnBoardDevice());
    workplace.setCashboxDeviceType(response.getCashboxDeviceType());
    workplace.setCurrencyType(response.getCurrentCurrencyType());
    workplace.setAmount(response.getAmount());
    workplace.setLimit(response.getWorkPlaceLimit());
    workplace.setCategoryLimits(new ArrayList<>());
    for (OperationTypeLimitItem operationTypeLimitItem :
        response.getWorkPlaceOperationTypeLimit().getOperationTypeLimitItem()) {
      OperationTypeLimit operationTypeLimit = new OperationTypeLimit();
      map(operationTypeLimitItem, operationTypeLimit);
      workplace.getCategoryLimits().add(operationTypeLimit);
    }
  }

  private static void map(
      OperationTypeLimitItem operationTypeLimitItem,
      OperationTypeLimit operationTypeLimit) {
    operationTypeLimit.setLimit(operationTypeLimitItem.getOperationLimit());
    operationTypeLimit.setCategoryId(operationTypeLimitItem.getOperationCategory().toString());
  }

  /**
   * Преобразует транспортный объект SrvGetWorkPlaceInfoRs во внутреннюю сущность Workplace.
   */
  public static Workplace convert(SrvGetWorkPlaceInfoRs response) {
    Workplace workplace = new Workplace();
    map(response.getHeaderInfo(), workplace);
    map(response.getSrvGetWorkPlaceInfoRsMessage(), workplace);
    return workplace;
  }

  /**
   * Возвращает объект запроса SrvGetWorkPlaceInfoRq по уникальному номеру УРМ/Кассы.
   */
  public static SrvGetWorkPlaceInfoRq requestById(String workPlaceUId) {
    SrvGetWorkPlaceInfoRq request = new SrvGetWorkPlaceInfoRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvGetWorkPlaceInfoRqMessage(new SrvGetWorkPlaceInfoRqMessage());
    request.getSrvGetWorkPlaceInfoRqMessage().setWorkPlaceUId(workPlaceUId);
    return request;
  }
}