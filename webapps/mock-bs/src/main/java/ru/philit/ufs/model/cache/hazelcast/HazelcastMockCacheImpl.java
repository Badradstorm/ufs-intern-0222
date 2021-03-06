package ru.philit.ufs.model.cache.hazelcast;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.IMap;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.xml.datatype.XMLGregorianCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.philit.ufs.model.cache.MockCache;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRs.SrvUpdCashOrderRsMessage;
import ru.philit.ufs.model.entity.esb.eks.PkgStatusType;
import ru.philit.ufs.model.entity.esb.eks.PkgTaskStatusType;
import ru.philit.ufs.model.entity.esb.eks.SrvGetTaskClOperPkgRs.SrvGetTaskClOperPkgRsMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvGetTaskClOperPkgRs.SrvGetTaskClOperPkgRsMessage.PkgItem;
import ru.philit.ufs.model.entity.oper.OperationPackageInfo;

/**
 * Реализация доступа к кешу мокируемых данных.
 */
@Service
public class HazelcastMockCacheImpl implements MockCache {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final HazelcastMockServer hazelcastServer;
  private final ObjectMapper jsonMapper;

  private Pattern createDatePattern = Pattern.compile("\"createdDttm\":(\\d+)");

  /**
   * Конструктор бина.
   */
  @Autowired
  public HazelcastMockCacheImpl(HazelcastMockServer hazelcastServer) {
    this.hazelcastServer = hazelcastServer;
    this.jsonMapper = new ObjectMapper();
    this.jsonMapper.setSerializationInclusion(Include.NON_NULL);
    this.jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  @Override
  public Long saveTaskCardDeposit(Long packageId, Long taskId, Object taskBody) {
    return saveTask(hazelcastServer.getTasksCardDepositByPackageId(), packageId, taskId, taskBody);
  }

  @Override
  public Long saveTaskCardWithdraw(Long packageId, Long taskId, Object taskBody) {
    return saveTask(hazelcastServer.getTasksCardWithdrawByPackageId(), packageId, taskId, taskBody);
  }

  @Override
  public Long saveTaskAccountDeposit(Long packageId, Long taskId, Object taskBody) {
    return saveTask(hazelcastServer.getTasksAccountDepositByPackageId(), packageId, taskId,
        taskBody);
  }

  @Override
  public Long saveTaskAccountWithdraw(Long packageId, Long taskId, Object taskBody) {
    return saveTask(hazelcastServer.getTasksAccountWithdrawByPackageId(), packageId, taskId,
        taskBody);
  }

  @Override
  public Long saveTaskCheckbookIssuing(Long packageId, Long taskId, Object taskBody) {
    return saveTask(hazelcastServer.getTasksCheckbookIssuingByPackageId(), packageId, taskId,
        taskBody);
  }

  private synchronized Long saveTask(IMap<Long, Map<Long, String>> collection, Long packageId,
      Long taskId, Object taskBody) {
    if (!collection.containsKey(packageId)) {
      collection.put(packageId, new HashMap<Long, String>());
    }
    Long realTaskId = taskId == null ? (long) (Math.random() * 1000000) : taskId;
    try {
      final Map<Long, String> taskMap = collection.get(packageId);
      String taskJson = jsonMapper.writeValueAsString(taskBody);
      taskJson = taskJson.replaceFirst("^\\{", "{\"changedDttm\":" + System.currentTimeMillis()
          + ",\"statusChangedDttm\":" + System.currentTimeMillis() + ",");
      if (taskId == null) {
        taskJson = taskJson.replaceFirst("^\\{", "{\"pkgTaskId\":" + realTaskId + ",");
      }
      Matcher matcher1 = createDatePattern.matcher(taskJson);
      if (!matcher1.find()) {
        String taskJson0 = taskMap.get(realTaskId);
        if (!StringUtils.isEmpty(taskJson0)) {
          Matcher matcher = createDatePattern.matcher(taskJson0);
          if (matcher.find() && matcher.groupCount() > 0) {
            taskJson = taskJson.replaceFirst("^\\{", "{\"createdDttm\":" + matcher.group(1) + ",");
          }
        }
      }
      taskMap.put(realTaskId, taskJson);
      collection.put(packageId, taskMap);
    } catch (JsonProcessingException e) {
      logger.error("Error on serialize {}", e, taskBody);
    }
    return realTaskId;
  }

  @Override
  public void saveTaskStatus(Long taskId, PkgTaskStatusType status) {
    hazelcastServer.getTaskStatuses().put(taskId, status);
  }

  @Override
  public Long checkPackage(String inn) {
    return hazelcastServer.getPackageIdByInn().getOrDefault(inn, null);
  }

  @Override
  public synchronized OperationPackageInfo createPackage(String inn, String workplaceId,
      String userLogin) {
    Long packageId = (long) (Math.random() * 10000);
    OperationPackageInfo packageInfo = new OperationPackageInfo();
    packageInfo.setId(packageId);
    packageInfo.setInn(inn);
    packageInfo.setWorkPlaceUid(workplaceId);
    packageInfo.setUserLogin(userLogin);
    packageInfo.setStatus(PkgStatusType.NEW);
    packageInfo.setCreatedDate(new Date());
    hazelcastServer.getPackageById().put(packageId, packageInfo);
    hazelcastServer.getPackageIdByInn().put(inn, packageId);
    return hazelcastServer.getPackageById().get(hazelcastServer.getPackageIdByInn().get(inn));
  }

  @Override
  public OperationPackageInfo getPackageInfo(Long packageId) {
    return hazelcastServer.getPackageById().get(packageId);
  }

  @Override
  public Map<Long, List<PkgItem.ToCardDeposit.TaskItem>> searchTasksCardDeposit(Long packageId,
      PkgTaskStatusType taskStatus, Date fromDate, Date toDate, List<Long> taskIds) {
    Map<Long, List<PkgItem.ToCardDeposit.TaskItem>> targetLists = new HashMap<>();
    Collection<Long> packageIds = (packageId != null)
        ? Collections.singletonList(packageId)
        : hazelcastServer.getTasksCardDepositByPackageId().keySet();
    for (Long packageIdKey : packageIds) {
      if (fromDate != null || toDate != null) {
        OperationPackageInfo packageInfo = hazelcastServer.getPackageById().get(packageIdKey);
        Date packageDate = truncateTime(packageInfo.getCreatedDate());
        if ((fromDate != null && fromDate.after(packageDate))
            || (toDate != null && toDate.before(packageDate))) {
          continue;
        }
      }
      List<String> roughList = searchTasks(hazelcastServer.getTasksCardDepositByPackageId()
          .get(packageIdKey), taskStatus, taskIds);
      if (!roughList.isEmpty()) {
        targetLists.put(packageIdKey,
            new ArrayList<SrvGetTaskClOperPkgRsMessage.PkgItem.ToCardDeposit.TaskItem>());
        deserializeTasksCardDeposit(roughList, targetLists.get(packageIdKey));
      }
    }
    return targetLists;
  }

  @Override
  public void saveCashOrder(SrvCreateCashOrderRs cashOrder) {
    XMLGregorianCalendar date = cashOrder.getSrvCreateCashOrderRsMessage().getCreatedDttm();
    hazelcastServer.getCashOrdersByDate().putIfAbsent(date, new HashMap<>());
    Map<String, SrvCreateCashOrderRs> innerMap = hazelcastServer.getCashOrdersByDate().get(date);
    innerMap.put(cashOrder.getSrvCreateCashOrderRsMessage().getCashOrderId(), cashOrder);
    hazelcastServer.getCashOrdersByDate().put(date, innerMap);
  }

  @Override
  public List<SrvCreateCashOrderRs> getCashOrders(XMLGregorianCalendar from,
      XMLGregorianCalendar to) {
    List<XMLGregorianCalendar> dates = hazelcastServer.getCashOrdersByDate().keySet().stream()
        .filter(date -> date.toGregorianCalendar().after(from.toGregorianCalendar())
            && date.toGregorianCalendar().before(to.toGregorianCalendar()))
        .collect(Collectors.toList());
    return dates.stream()
        .map(date -> hazelcastServer.getCashOrdersByDate().get(date))
        .map(Map::values)
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }

  @Override
  public SrvUpdStCashOrderRs updateCashOrder(SrvUpdStCashOrderRq request) {
    SrvUpdStCashOrderRs updatedCashOrder = new SrvUpdStCashOrderRs();
    updatedCashOrder.setSrvUpdCashOrderRsMessage(new SrvUpdCashOrderRsMessage());
    String requestCashOrderId = request.getSrvUpdCashOrderRqMessage().getCashOrderId();

    XMLGregorianCalendar date = hazelcastServer.getCashOrdersByDate().entrySet().stream()
        .filter(pair -> pair.getValue().keySet().stream()
            .anyMatch(innerV -> innerV.equals(requestCashOrderId)))
        .map(Entry::getKey)
        .findFirst()
        .orElse(null);

    if (date != null) {
      SrvCreateCashOrderRs cashOrderFromCache = hazelcastServer.getCashOrdersByDate()
          .get(date).get(requestCashOrderId);
      cashOrderFromCache.getSrvCreateCashOrderRsMessage()
          .setCashOrderStatus(request.getSrvUpdCashOrderRqMessage()
              .getCashOrderStatus());
      updatedCashOrder.getSrvUpdCashOrderRsMessage()
          .setCashOrderId(cashOrderFromCache.getSrvCreateCashOrderRsMessage().getCashOrderId());
      updatedCashOrder.getSrvUpdCashOrderRsMessage().setResponseCode("200");
      cashOrderFromCache.getSrvCreateCashOrderRsMessage().setResponseCode("200");
      updatedCashOrder.getSrvUpdCashOrderRsMessage().setResponseMsg("ok");
      cashOrderFromCache.getSrvCreateCashOrderRsMessage().setResponseMsg("ok");
      updatedCashOrder.getSrvUpdCashOrderRsMessage()
          .setCashOrderType(cashOrderFromCache.getSrvCreateCashOrderRsMessage()
              .getCashOrderType());
      updatedCashOrder.getSrvUpdCashOrderRsMessage()
          .setCashOrderINum(cashOrderFromCache.getSrvCreateCashOrderRsMessage().getCashOrderINum());
      updatedCashOrder.getSrvUpdCashOrderRsMessage()
          .setCashOrderStatus(cashOrderFromCache.getSrvCreateCashOrderRsMessage()
              .getCashOrderStatus());
      saveCashOrder(cashOrderFromCache);
    } else {
      updatedCashOrder.getSrvUpdCashOrderRsMessage().setResponseCode("404");
      updatedCashOrder.getSrvUpdCashOrderRsMessage().setResponseMsg("Not Found");
    }
    return updatedCashOrder;
  }

  @Override
  public Boolean checkOverLimit(String userLogin, BigDecimal amount,
      XMLGregorianCalendar requestDate) {
    BigDecimal result = hazelcastServer.getCashOrdersByDate().get(requestDate).values().stream()
        .filter(cashOrder -> userLogin.equals(
            cashOrder.getSrvCreateCashOrderRsMessage().getUserLogin()))
        .map(cashOrderRs -> cashOrderRs.getSrvCreateCashOrderRsMessage().getAmount())
        .reduce(BigDecimal::add)
        .orElse(BigDecimal.valueOf(0));
    BigDecimal limit = new BigDecimal("1000000.0");
    return result.add(amount).compareTo(limit) <= 0;
  }

  private List<String> searchTasks(Map<Long, String> tasks, PkgTaskStatusType taskStatus,
      List<Long> taskIds) {
    List<String> resultList = new ArrayList<>();
    for (Entry<Long, String> taskEntry : tasks.entrySet()) {
      PkgTaskStatusType currentTaskStatus =
          hazelcastServer.getTaskStatuses().get(taskEntry.getKey());
      if ((taskStatus == null || currentTaskStatus == null || taskStatus.equals(currentTaskStatus))
          && (taskIds == null || taskIds.contains(taskEntry.getKey()))) {
        resultList.add(taskEntry.getValue());
      }
    }
    return resultList;
  }

  private void deserializeTasksCardDeposit(List<String> roughList,
      List<SrvGetTaskClOperPkgRsMessage.PkgItem.ToCardDeposit.TaskItem> targetList) {
    for (String wrappedTask : roughList) {
      try {
        targetList.add(jsonMapper.readValue(wrappedTask,
            SrvGetTaskClOperPkgRsMessage.PkgItem.ToCardDeposit.TaskItem.class));
      } catch (IOException e) {
        logger.error("Error on deserialize", e);
      }
    }
  }

  private Date truncateTime(Date date) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.set(Calendar.HOUR_OF_DAY, c.getActualMinimum(Calendar.HOUR_OF_DAY));
    c.set(Calendar.MINUTE, c.getActualMinimum(Calendar.MINUTE));
    c.set(Calendar.SECOND, c.getActualMinimum(Calendar.SECOND));
    c.set(Calendar.MILLISECOND, c.getActualMinimum(Calendar.MILLISECOND));
    return c.getTime();
  }
}
