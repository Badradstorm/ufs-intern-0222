package ru.philit.ufs.model.cache.hazelcast;

import static org.mockito.Mockito.when;

import com.hazelcast.core.IMap;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.philit.ufs.model.entity.esb.asfs.CashOrderStatusType;
import ru.philit.ufs.model.entity.esb.asfs.CashOrderType;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRq.SrvUpdCashOrderRqMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRs;
import ru.philit.ufs.model.entity.esb.eks.PkgTaskStatusType;
import ru.philit.ufs.model.entity.esb.eks.SrvGetTaskClOperPkgRs.SrvGetTaskClOperPkgRsMessage;
import ru.philit.ufs.model.entity.oper.OperationPackageInfo;

public class HazelcastMockCacheImplTest {

  private static final String CTRL_TASK_ELEMENT = "\"pkgTaskId\":10";
  private static final String INN = "77700044422232";

  static class TestTaskBody {

    public Long pkgTaskId;

    public TestTaskBody(Long pkgTaskId) {
      this.pkgTaskId = pkgTaskId;
    }
  }

  @Mock
  private HazelcastMockServer hazelcastMockServer;

  private HazelcastMockCacheImpl mockCache;

  private IMap<Long, Map<Long, String>> tasksCardDepositByPackageId = new MockIMap<>();
  private IMap<Long, Map<Long, String>> tasksCardWithdrawByPackageId = new MockIMap<>();
  private IMap<Long, Map<Long, String>> tasksAccountDepositByPackageId = new MockIMap<>();
  private IMap<Long, Map<Long, String>> tasksAccountWithdrawByPackageId = new MockIMap<>();
  private IMap<Long, Map<Long, String>> tasksCheckbookIssuingByPackageId = new MockIMap<>();
  private IMap<Long, PkgTaskStatusType> taskStatuses = new MockIMap<>();
  private IMap<Long, OperationPackageInfo> packageById = new MockIMap<>();
  private IMap<String, Long> packageIdByInn = new MockIMap<>();
  private IMap<String, SrvCreateCashOrderRs> cashOrderById = new MockIMap<>();

  /**
   * Set up test data.
   */
  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mockCache = new HazelcastMockCacheImpl(hazelcastMockServer);
    when(hazelcastMockServer.getTasksCardDepositByPackageId())
        .thenReturn(tasksCardDepositByPackageId);
    when(hazelcastMockServer.getTasksCardWithdrawByPackageId())
        .thenReturn(tasksCardWithdrawByPackageId);
    when(hazelcastMockServer.getTasksAccountDepositByPackageId())
        .thenReturn(tasksAccountDepositByPackageId);
    when(hazelcastMockServer.getTasksAccountWithdrawByPackageId())
        .thenReturn(tasksAccountWithdrawByPackageId);
    when(hazelcastMockServer.getTasksCheckbookIssuingByPackageId())
        .thenReturn(tasksCheckbookIssuingByPackageId);
    when(hazelcastMockServer.getTaskStatuses()).thenReturn(taskStatuses);
    when(hazelcastMockServer.getPackageById()).thenReturn(packageById);
    when(hazelcastMockServer.getPackageIdByInn()).thenReturn(packageIdByInn);
    when(hazelcastMockServer.getCashOrderById()).thenReturn(cashOrderById);
  }

  @Test
  public void testSaveTask() {
    // given
    TestTaskBody taskBody = new TestTaskBody(10L);

    // when
    mockCache.saveTaskCardDeposit(1L, 10L, taskBody);
    // then
    Assert.assertTrue(tasksCardDepositByPackageId.containsKey(1L));
    Assert.assertTrue(tasksCardDepositByPackageId.get(1L).containsKey(10L));
    Assert.assertTrue(tasksCardDepositByPackageId.get(1L).get(10L).contains(CTRL_TASK_ELEMENT));

    // when
    mockCache.saveTaskCardWithdraw(1L, 10L, taskBody);
    // then
    Assert.assertTrue(tasksCardWithdrawByPackageId.containsKey(1L));
    Assert.assertTrue(tasksCardWithdrawByPackageId.get(1L).containsKey(10L));
    Assert.assertTrue(tasksCardDepositByPackageId.get(1L).get(10L).contains(CTRL_TASK_ELEMENT));

    // when
    mockCache.saveTaskAccountDeposit(1L, 10L, taskBody);
    // then
    Assert.assertTrue(tasksAccountDepositByPackageId.containsKey(1L));
    Assert.assertTrue(tasksAccountDepositByPackageId.get(1L).containsKey(10L));
    Assert.assertTrue(tasksCardDepositByPackageId.get(1L).get(10L).contains(CTRL_TASK_ELEMENT));

    // when
    mockCache.saveTaskAccountWithdraw(1L, 10L, taskBody);
    // then
    Assert.assertTrue(tasksAccountWithdrawByPackageId.containsKey(1L));
    Assert.assertTrue(tasksAccountWithdrawByPackageId.get(1L).containsKey(10L));
    Assert.assertTrue(tasksCardDepositByPackageId.get(1L).get(10L).contains(CTRL_TASK_ELEMENT));

    // when
    mockCache.saveTaskCheckbookIssuing(1L, 10L, taskBody);
    // then
    Assert.assertTrue(tasksCheckbookIssuingByPackageId.containsKey(1L));
    Assert.assertTrue(tasksCheckbookIssuingByPackageId.get(1L).containsKey(10L));
    Assert.assertTrue(tasksCardDepositByPackageId.get(1L).get(10L).contains(CTRL_TASK_ELEMENT));
  }

  @Test
  public void testSaveTaskStatus() {
    // when
    mockCache.saveTaskStatus(1L, PkgTaskStatusType.ACTIVE);
    //then
    Assert.assertTrue(taskStatuses.containsKey(1L));
    Assert.assertEquals(taskStatuses.get(1L), PkgTaskStatusType.ACTIVE);
  }

  @Test
  public void testCreatePackage() {
    // when
    Long packageId = mockCache.checkPackage(INN);
    // then
    Assert.assertNull(packageId);

    // when
    OperationPackageInfo packageInfo = mockCache.createPackage(
        INN, "12345", "Sidorov_SS");
    // then
    Assert.assertNotNull(packageInfo.getId());

    // when
    Long packageId2 = mockCache.checkPackage(INN);
    // then
    Assert.assertEquals(packageInfo.getId(), packageId2);

    // when
    OperationPackageInfo packageInfo2 = mockCache.createPackage(
        INN, "12345", "Sidorov_SS");
    // then
    Assert.assertNotEquals(packageInfo.getId(), packageInfo2.getId());
  }

  @Test
  public void testSearchTaskCardDeposit() {
    // when
    Map<Long, List<SrvGetTaskClOperPkgRsMessage.PkgItem.ToCardDeposit.TaskItem>> resultMap =
        mockCache.searchTasksCardDeposit(null, null,
            null, null, null);
    // then
    Assert.assertNotNull(resultMap);
    Assert.assertTrue(resultMap.isEmpty());
  }

  @Test
  public void testSaveCashOrder() {
    // when
    SrvCreateCashOrderRs cashOrder = new SrvCreateCashOrderRs();
    cashOrder.setSrvCreateCashOrderRsMessage(new SrvCreateCashOrderRsMessage());
    cashOrder.getSrvCreateCashOrderRsMessage().setCashOrderId("1");
    mockCache.saveCashOrder(cashOrder);
    //then
    Assert.assertTrue(cashOrderById.containsKey("1"));
    Assert.assertEquals(cashOrderById.get("1"), cashOrder);
  }

  @Test
  public void testGetCashOrders() throws DatatypeConfigurationException {
    // when
    SrvCreateCashOrderRs cashOrder = new SrvCreateCashOrderRs();
    cashOrder.setSrvCreateCashOrderRsMessage(new SrvCreateCashOrderRsMessage());
    cashOrder.getSrvCreateCashOrderRsMessage().setCashOrderId("1");
    cashOrder.getSrvCreateCashOrderRsMessage().setCreatedDttm(xmlCalendar(2022, Calendar.MARCH, 8));
    mockCache.saveCashOrder(cashOrder);
    SrvCreateCashOrderRs cashOrder2 = new SrvCreateCashOrderRs();
    cashOrder2.setSrvCreateCashOrderRsMessage(new SrvCreateCashOrderRsMessage());
    cashOrder2.getSrvCreateCashOrderRsMessage().setCashOrderId("2");
    cashOrder2.getSrvCreateCashOrderRsMessage()
        .setCreatedDttm(xmlCalendar(2022, Calendar.APRIL, 8));
    mockCache.saveCashOrder(cashOrder2);
    List<SrvCreateCashOrderRs> result = mockCache.getCashOrders(
        xmlCalendar(2022, Calendar.MARCH, 7),
        xmlCalendar(2022, Calendar.MARCH, 9));
    // then
    Assert.assertNotNull(result);
    Assert.assertFalse(result.isEmpty());
    Assert.assertEquals(result.size(), 1);
    Assert.assertEquals(result.get(0).getSrvCreateCashOrderRsMessage().getCashOrderId(), "1");
  }

  @Test
  public void testUpdateCashOrder() {
    // when
    SrvCreateCashOrderRs cashOrder = new SrvCreateCashOrderRs();
    cashOrder.setSrvCreateCashOrderRsMessage(new SrvCreateCashOrderRsMessage());
    cashOrder.getSrvCreateCashOrderRsMessage().setCashOrderId("1");
    cashOrder.getSrvCreateCashOrderRsMessage().setCashOrderStatus(CashOrderStatusType.COMMITTED);
    cashOrder.getSrvCreateCashOrderRsMessage().setCashOrderType(CashOrderType.KO_1);
    cashOrder.getSrvCreateCashOrderRsMessage().setCashOrderINum("111");
    mockCache.saveCashOrder(cashOrder);
    SrvUpdStCashOrderRq request = new SrvUpdStCashOrderRq();
    request.setSrvUpdCashOrderRqMessage(new SrvUpdCashOrderRqMessage());
    request.getSrvUpdCashOrderRqMessage().setCashOrderId("1");
    request.getSrvUpdCashOrderRqMessage().setCashOrderStatus(CashOrderStatusType.CREATED);
    SrvUpdStCashOrderRs result = mockCache.updateCashOrder(request);
    // then
    Assert.assertNotNull(result);
    Assert.assertEquals(result.getSrvUpdCashOrderRsMessage().getCashOrderId(),
        cashOrder.getSrvCreateCashOrderRsMessage().getCashOrderId());
    Assert.assertEquals(result.getSrvUpdCashOrderRsMessage().getCashOrderINum(),
        cashOrder.getSrvCreateCashOrderRsMessage().getCashOrderINum());
    Assert.assertEquals(result.getSrvUpdCashOrderRsMessage().getCashOrderStatus(),
        CashOrderStatusType.CREATED);
    Assert.assertEquals(result.getSrvUpdCashOrderRsMessage().getCashOrderType(),
        cashOrder.getSrvCreateCashOrderRsMessage().getCashOrderType());
  }

  @Test
  public void testCheckOverLimit() {
    // when
    XMLGregorianCalendar createdDate = xmlCalendar(2022, Calendar.MARCH, 8);
    SrvCreateCashOrderRs cashOrder = new SrvCreateCashOrderRs();
    cashOrder.setSrvCreateCashOrderRsMessage(new SrvCreateCashOrderRsMessage());
    cashOrder.getSrvCreateCashOrderRsMessage().setCashOrderId("1");
    cashOrder.getSrvCreateCashOrderRsMessage().setCreatedDttm(createdDate);
    cashOrder.getSrvCreateCashOrderRsMessage().setAmount(BigDecimal.valueOf(5000));
    cashOrder.getSrvCreateCashOrderRsMessage().setUserLogin("Ann");
    mockCache.saveCashOrder(cashOrder);
    SrvCreateCashOrderRs cashOrder2 = new SrvCreateCashOrderRs();
    cashOrder2.setSrvCreateCashOrderRsMessage(new SrvCreateCashOrderRsMessage());
    cashOrder2.getSrvCreateCashOrderRsMessage().setCashOrderId("2");
    cashOrder2.getSrvCreateCashOrderRsMessage().setCreatedDttm(createdDate);
    cashOrder2.getSrvCreateCashOrderRsMessage().setAmount(BigDecimal.valueOf(10000));
    cashOrder2.getSrvCreateCashOrderRsMessage().setUserLogin("Ann");
    mockCache.saveCashOrder(cashOrder2);
    SrvCreateCashOrderRs cashOrder3 = new SrvCreateCashOrderRs();
    cashOrder3.setSrvCreateCashOrderRsMessage(new SrvCreateCashOrderRsMessage());
    cashOrder3.getSrvCreateCashOrderRsMessage().setCashOrderId("3");
    cashOrder3.getSrvCreateCashOrderRsMessage()
        .setCreatedDttm(xmlCalendar(2022, Calendar.MARCH, 9));
    cashOrder3.getSrvCreateCashOrderRsMessage().setAmount(BigDecimal.valueOf(1000000));
    cashOrder3.getSrvCreateCashOrderRsMessage().setUserLogin("Ann");
    mockCache.saveCashOrder(cashOrder3);
    SrvCreateCashOrderRs cashOrder4 = new SrvCreateCashOrderRs();
    cashOrder4.setSrvCreateCashOrderRsMessage(new SrvCreateCashOrderRsMessage());
    cashOrder4.getSrvCreateCashOrderRsMessage().setCashOrderId("4");
    cashOrder4.getSrvCreateCashOrderRsMessage().setCreatedDttm(createdDate);
    cashOrder4.getSrvCreateCashOrderRsMessage().setAmount(BigDecimal.valueOf(10000000));
    cashOrder4.getSrvCreateCashOrderRsMessage().setUserLogin("Ben");
    mockCache.saveCashOrder(cashOrder4);
    Boolean result = mockCache.checkOverLimit("Ann",
        BigDecimal.valueOf(10000), createdDate);
    Boolean result2 = mockCache.checkOverLimit("Ben",
        BigDecimal.valueOf(10000), createdDate);
    // then
    Assert.assertNotNull(result);
    Assert.assertNotNull(result2);
    Assert.assertTrue(result);
    Assert.assertFalse(result2);
  }

  protected static XMLGregorianCalendar xmlCalendar(int year, int month, int day) {
    try {
      return DatatypeFactory.newInstance()
          .newXMLGregorianCalendar(new GregorianCalendar(year, month, day));
    } catch (DatatypeConfigurationException e) {
      e.printStackTrace();
      return null;
    }
  }
}
