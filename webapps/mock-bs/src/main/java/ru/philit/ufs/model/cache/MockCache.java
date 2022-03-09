package ru.philit.ufs.model.cache;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import ru.philit.ufs.model.entity.esb.asfs.CashOrderStatusType;
import ru.philit.ufs.model.entity.esb.eks.PkgTaskStatusType;
import ru.philit.ufs.model.entity.esb.eks.SrvGetTaskClOperPkgRs.SrvGetTaskClOperPkgRsMessage;
import ru.philit.ufs.model.entity.oper.OperationPackageInfo;
import ru.philit.ufs.model.entity.order.CashOrder;
import ru.philit.ufs.model.entity.order.CashOrderStatus;
import ru.philit.ufs.model.entity.user.Workplace;

/**
 * Кеш данных Mock приложения.
 */
public interface MockCache {

  Long saveTaskCardDeposit(Long packageId, Long taskId, Object taskBody);

  Long saveTaskCardWithdraw(Long packageId, Long taskId, Object taskBody);

  Long saveTaskAccountDeposit(Long packageId, Long taskId, Object taskBody);

  Long saveTaskAccountWithdraw(Long packageId, Long taskId, Object taskBody);

  Long saveTaskCheckbookIssuing(Long packageId, Long taskId, Object taskBody);

  void saveTaskStatus(Long taskId, PkgTaskStatusType status);

  Long checkPackage(String inn);

  OperationPackageInfo createPackage(String inn, String workplaceId, String userLogin);

  OperationPackageInfo getPackageInfo(Long packageId);

  Map<Long, List<SrvGetTaskClOperPkgRsMessage.PkgItem.ToCardDeposit.TaskItem>>
        searchTasksCardDeposit(Long packageId, PkgTaskStatusType taskStatus, Date fromDate,
        Date toDate, List<Long> taskIds);

  void saveCashOrder(CashOrder cashOrder);

  List<CashOrder> getCashOrders(Date createdFrom, Date createdTo);

  CashOrder updateCashOrder(String cashOrderId, CashOrderStatusType cashOrderStatus);

  Boolean checkOverLimit(String userLogin, BigDecimal amount, Date requestDate);
}
