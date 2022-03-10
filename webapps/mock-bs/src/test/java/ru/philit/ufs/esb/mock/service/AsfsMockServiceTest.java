package ru.philit.ufs.esb.mock.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.philit.ufs.esb.mock.client.EsbClient;
import ru.philit.ufs.model.cache.MockCache;

public class AsfsMockServiceTest {

  @Mock
  private EsbClient esbClient;
  @Mock
  private MockCache mockCache;

  private AsfsMockService service;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    service = new AsfsMockService(esbClient, mockCache);
  }


  @Test
  public void testInit() throws Exception {
    service.init();
  }

  @Test
  public void testProcessMessage_SrvCreateCashOrderRq() throws Exception {
    String requestMessage = "<SrvCreateCashOrderRq><HeaderInfo/>"
        + "<SrvCreateCashOrderRqMessage/>"
        + "</SrvCreateCashOrderRq>";
    assertTrue(service.processMessage(requestMessage));
  }

  @Test
  public void testProcessMessage_SrvUpdStCashOrderRq() throws Exception {
    String requestMessage = "<SrvUpdStCashOrderRq><HeaderInfo/>"
        + "<SrvUpdCashOrderRqMessage>"
        + "<cashOrderId>1</cashOrderId>"
        + "<cashOrderStatus>Committed</cashOrderStatus>"
        + "</SrvUpdCashOrderRqMessage>"
        + "</SrvUpdStCashOrderRq>";
    assertTrue(service.processMessage(requestMessage));
  }

  @Test
  public void testProcessMessage_SrvGetCashOrderRq() throws Exception {
    String requestMessage = "<SrvGetCashOrderRq><HeaderInfo/>"
        + "<SrvGetCashOrderRqMessage>"
        + "<createdFrom>2022-03-07T15:13:44.991+03:00</createdFrom>"
        + "<createdTo>2022-03-07T15:13:44.991+03:00</createdTo>"
        + "</SrvGetCashOrderRqMessage>"
        + "</SrvGetCashOrderRq>";
    assertTrue(service.processMessage(requestMessage));
  }

  @Test
  public void testProcessMessage_SrvCheckOverLimitRq() throws Exception {
    String requestMessage = "<SrvCheckOverLimitRq><HeaderInfo/>"
        + "<SrvCheckOverLimitRqMessage/>"
        + "</SrvCheckOverLimitRq>";
    assertTrue(service.processMessage(requestMessage));
  }

  @Test
  public void testProcessMessage_SrvGetWorkPlaceInfoRq() throws Exception {
    String requestMessage = "<SrvGetWorkPlaceInfoRq><HeaderInfo/>"
        + "</SrvGetWorkPlaceInfoRq>";
    assertTrue(service.processMessage(requestMessage));
  }

  @Test
  public void testProcessMessage_Other() throws Exception {
    String requestMessage = "Not valid xml";
    assertFalse(service.processMessage(requestMessage));
  }
}
