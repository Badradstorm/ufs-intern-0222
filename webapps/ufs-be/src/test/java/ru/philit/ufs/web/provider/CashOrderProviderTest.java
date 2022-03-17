package ru.philit.ufs.web.provider;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.philit.ufs.model.cache.CashOrderCache;
import ru.philit.ufs.model.entity.account.Representative;
import ru.philit.ufs.model.entity.common.OperationTypeCode;
import ru.philit.ufs.model.entity.oper.OperationTaskDeposit;
import ru.philit.ufs.model.entity.order.CashOrder;
import ru.philit.ufs.model.entity.order.CashOrderRequest;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.model.entity.user.Operator;
import ru.philit.ufs.model.entity.user.User;
import ru.philit.ufs.web.exception.InvalidDataException;

@RunWith(DataProviderRunner.class)
public class CashOrderProviderTest {

  private static final ClientInfo CLIENT_INFO = new ClientInfo("1", new User("login"), "2");
  private static final String TYPE_CODE = OperationTypeCode.TO_CARD_DEPOSIT.code();
  private static final CashOrderRequest CASH_ORDER_REQUEST = new CashOrderRequest();

  @Mock
  private CashOrderCache cashOrderCache;
  @Mock
  private RepresentativeProvider representativeProvider;
  @Mock
  private UserProvider userProvider;

  private CashOrderProvider provider;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    provider = new CashOrderProvider(cashOrderCache, representativeProvider, userProvider);
  }

  @Test
  public void testCreateCashOrder() throws Exception {
    // given
    OperationTaskDeposit operationTaskDeposit = new OperationTaskDeposit();
    operationTaskDeposit.setOvnNum(1L);

    // when
    when(userProvider.getOperator(any(ClientInfo.class))).thenReturn(new Operator());
    when(representativeProvider.getRepresentativeById(any(String.class),
        any(ClientInfo.class))).thenReturn(new Representative());
    when(cashOrderCache.saveCashOrder(any(CashOrder.class), any(ClientInfo.class))).thenReturn(
        new CashOrder());

    provider.createCashOrder(operationTaskDeposit, CLIENT_INFO, TYPE_CODE);

    // verify
    verify(userProvider).getOperator(any(ClientInfo.class));
    verify(representativeProvider).getRepresentativeById(any(String.class), any(ClientInfo.class));
    verify(cashOrderCache).saveCashOrder(any(CashOrder.class), any(ClientInfo.class));
    verifyNoMoreInteractions(cashOrderCache);
  }

  @Test
  public void testGetCashOrders() throws Exception {
    // given
    List<CashOrder> cashOrders = new ArrayList<>();
    CashOrder cashOrder = new CashOrder();
    cashOrders.add(cashOrder);

    // when
    when(cashOrderCache.getCashOrders(any(CashOrderRequest.class),
        any(ClientInfo.class))).thenReturn(
        cashOrders);

    provider.getCashOrders(CASH_ORDER_REQUEST, CLIENT_INFO);

    // verify
    verify(cashOrderCache).getCashOrders(any(CashOrderRequest.class), any(ClientInfo.class));
    verifyNoMoreInteractions(cashOrderCache);
  }

  @Test(expected = InvalidDataException.class)
  public void testGetCashOrders_NullCashOrders() throws Exception {
    // when
    when(cashOrderCache.getCashOrders(any(CashOrderRequest.class),
        any(ClientInfo.class))).thenReturn(null);

    provider.getCashOrders(CASH_ORDER_REQUEST, CLIENT_INFO);

    // verify
    verify(cashOrderCache).getCashOrders(any(CashOrderRequest.class), any(ClientInfo.class));
    verifyNoMoreInteractions(cashOrderCache);
  }

  @Test(expected = InvalidDataException.class)
  public void testGetCashOrders_EmptyCashOrders() throws Exception {
    // when
    when(cashOrderCache.getCashOrders(any(CashOrderRequest.class),
        any(ClientInfo.class))).thenReturn(new ArrayList<>());

    provider.getCashOrders(CASH_ORDER_REQUEST, CLIENT_INFO);

    // verify
    verify(cashOrderCache).getCashOrders(any(CashOrderRequest.class), any(ClientInfo.class));
    verifyNoMoreInteractions(cashOrderCache);
  }

  @Test
  public void testUpdateCashOrders() throws Exception {
    // given
    CashOrder cashOrder = new CashOrder();

    // when
    when(cashOrderCache.updateCashOrder(any(CashOrder.class),
        any(ClientInfo.class))).thenReturn(cashOrder);

    provider.updateCashOrder(cashOrder, CLIENT_INFO);

    // verify
    verify(cashOrderCache).updateCashOrder(any(CashOrder.class), any(ClientInfo.class));
    verifyNoMoreInteractions(cashOrderCache);
  }
}
