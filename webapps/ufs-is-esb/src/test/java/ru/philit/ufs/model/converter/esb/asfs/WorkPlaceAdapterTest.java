package ru.philit.ufs.model.converter.esb.asfs;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.philit.ufs.model.converter.esb.multi.MultiAdapter;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRs.SrvGetWorkPlaceInfoRsMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRs.SrvGetWorkPlaceInfoRsMessage.WorkPlaceOperationTypeLimit;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRs.SrvGetWorkPlaceInfoRsMessage.WorkPlaceOperationTypeLimit.OperationTypeLimitItem;
import ru.philit.ufs.model.entity.oper.OperationTypeLimit;
import ru.philit.ufs.model.entity.user.Workplace;

public class WorkPlaceAdapterTest extends AsfsAdapterBaseTest {

  private static final String WORKPLACE_UID = "111";

  private SrvGetWorkPlaceInfoRs response;

  /**
   * Set up test data.
   */
  @Before
  public void setUp() {
    response = new SrvGetWorkPlaceInfoRs();
    response.setHeaderInfo(headerInfo());
    response.setSrvGetWorkPlaceInfoRsMessage(new SrvGetWorkPlaceInfoRsMessage());
    SrvGetWorkPlaceInfoRsMessage message = response.getSrvGetWorkPlaceInfoRsMessage();

    message.setWorkPlaceType(BigInteger.valueOf(0));
    message.setCashboxOnBoard(true);
    message.setSubbranchCode("123");
    message.setCashboxOnBoardDevice("333");
    message.setCashboxDeviceType("dev");
    message.setCurrentCurrencyType("rub");
    message.setAmount(BigDecimal.valueOf(2.000));
    message.setWorkPlaceLimit(BigDecimal.valueOf(3.000));
    message.setWorkPlaceOperationTypeLimit(new WorkPlaceOperationTypeLimit());
    OperationTypeLimitItem item = new OperationTypeLimitItem();
    item.setOperationCategory(BigInteger.valueOf(1));
    item.setOperationLimit(BigDecimal.valueOf(2.000));
    message.getWorkPlaceOperationTypeLimit().getOperationTypeLimitItem().add(item);
  }

  @Test
  public void testRequestById() {
    SrvGetWorkPlaceInfoRq request = WorkPlaceAdapter.requestById(WORKPLACE_UID);
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertNotNull(request.getSrvGetWorkPlaceInfoRqMessage());
    Assert.assertEquals(request.getSrvGetWorkPlaceInfoRqMessage().getWorkPlaceUId(), WORKPLACE_UID);
  }

  @Test
  public void testConvertSrvGetWorkPlaceInfoRs() {
    Workplace workplace = WorkPlaceAdapter.convert(response);
    List<OperationTypeLimit> categoryLimits = workplace.getCategoryLimits();

    assertHeaderInfo(workplace);
    Assert.assertEquals(workplace.getType().code(), 0);
    Assert.assertTrue(workplace.isCashboxOnBoard());
    Assert.assertEquals(workplace.getSubbranchCode(), "123");
    Assert.assertEquals(workplace.getCashboxDeviceId(), "333");
    Assert.assertEquals(workplace.getCashboxDeviceType(), "dev");
    Assert.assertEquals(workplace.getCurrencyType(), "rub");
    Assert.assertEquals(workplace.getAmount(), BigDecimal.valueOf(2.000));
    Assert.assertEquals(workplace.getLimit(), BigDecimal.valueOf(3.000));
    Assert.assertEquals(categoryLimits.size(), 1);
    Assert.assertEquals(categoryLimits.get(0).getCategoryId(), BigInteger.valueOf(1).toString());
    Assert.assertEquals(categoryLimits.get(0).getLimit(), BigDecimal.valueOf(2.000));
  }

  @Test
  public void testMultiAdapter() {
    ExternalEntity externalEntity = MultiAdapter.convert(response);
    Assert.assertNotNull(externalEntity);
    Assert.assertEquals(externalEntity.getClass(), Workplace.class);
  }
}
