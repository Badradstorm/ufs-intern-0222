package ru.philit.ufs.model.converter.esb.asfs;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.philit.ufs.model.TestData;
import ru.philit.ufs.model.converter.esb.multi.MultiAdapter;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.common.ExternalEntityContainer;
import ru.philit.ufs.model.entity.common.ExternalEntityList;
import ru.philit.ufs.model.entity.esb.asfs.LimitStatusType;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRs.SrvCheckOverLimitRsMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage.AdditionalInfo;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage.RepData;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage.CashSymbols;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage.CashSymbols.CashSymbolItem;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetCashOrderRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetCashOrderRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetCashOrderRs.SrvGetCashOrderRsMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetCashOrderRs.SrvGetCashOrderRsMessage.CashOrderItem;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRs.SrvUpdCashOrderRsMessage;
import ru.philit.ufs.model.entity.order.CashOrder;
import ru.philit.ufs.model.entity.order.CashOrderRequest;

public class CashOrderAdapterTest extends AsfsAdapterBaseTest {

  private SrvCreateCashOrderRs responseCreate;
  private SrvGetCashOrderRs responseGet;
  private SrvUpdStCashOrderRs responseUpdate;
  private SrvCheckOverLimitRs responseLimit;
  private TestData testData;

  /**
   * Set up test data.
   */
  @Before
  public void setUp() {
    testData = new TestData();

    responseCreate = new SrvCreateCashOrderRs();
    responseCreate.setHeaderInfo(headerInfo());
    responseCreate.setSrvCreateCashOrderRsMessage(new SrvCreateCashOrderRsMessage());

    SrvCreateCashOrderRsMessage messageCreate = responseCreate.getSrvCreateCashOrderRsMessage();
    messageCreate.setResponseCode(TestData.RESPONSE_CODE);
    messageCreate.setResponseMsg(TestData.RESPONSE_MESSAGE);
    messageCreate.setCashOrderId(TestData.CASH_ORDER_ID);
    messageCreate.setCashOrderINum(TestData.CASH_ORDER_I_NUM);
    messageCreate.setCashOrderStatus(TestData.CASH_ORDER_STATUS);
    messageCreate.setCashOrderType(TestData.CASH_ORDER_TYPE);
    messageCreate.setCreatedDttm(xmlCalendar(2017, 4, 17, 17, 0));
    messageCreate.setOperationId(TestData.OPERATION_ID);
    messageCreate.setRepFIO(TestData.REP_FIO);
    messageCreate.setLegalEntityShortName(TestData.LEGAL_ENTITY_SHORT_NAME);
    messageCreate.setINN(TestData.INN);
    messageCreate.setAmount(TestData.AMOUNT);
    messageCreate.setAccountId(TestData.ACCOUNT_ID);
    messageCreate.setSenderBank(TestData.SENDER_BANK);
    messageCreate.setSenderBankBIC(TestData.SENDER_BANK_BIC);
    messageCreate.setRecipientBank(TestData.RECIPIENT_BANK);
    messageCreate.setRecipientBankBIC(TestData.RECIPIENT_BANK_BIC);
    messageCreate.setClientTypeFK(TestData.CLIENT_TYPE_FK);
    messageCreate.setFDestLEName(TestData.F_DEST_LE_NAME);
    messageCreate.setSubbranchCode(TestData.SUBBRANCH_CODE);
    messageCreate.setUserLogin(TestData.USER_LOGIN);
    messageCreate.setUserFullName(TestData.USER_FULLNAME);
    messageCreate.setUserPosition(TestData.USER_POSITION);
    messageCreate.setCashSymbols(new CashSymbols());
    CashSymbolItem cashSymbolItemCreate = new CashSymbolItem();
    cashSymbolItemCreate.setCashSymbol(TestData.CASH_SYMBOL);
    cashSymbolItemCreate.setCashSymbolAmount(TestData.CASH_SYMBOL_AMOUNT);
    messageCreate.getCashSymbols().getCashSymbolItem().add(cashSymbolItemCreate);

    responseGet = new SrvGetCashOrderRs();
    responseGet.setHeaderInfo(headerInfo());
    responseGet.setSrvGetCashOrderRsMessage(new SrvGetCashOrderRsMessage());

    CashOrderItem cashOrderItem = new CashOrderItem();
    cashOrderItem.setResponseCode(TestData.RESPONSE_CODE);
    cashOrderItem.setResponseMsg(TestData.RESPONSE_MESSAGE);
    cashOrderItem.setCashOrderId(TestData.CASH_ORDER_ID);
    cashOrderItem.setCashOrderINum(TestData.CASH_ORDER_I_NUM);
    cashOrderItem.setCashOrderStatus(TestData.CASH_ORDER_STATUS);
    cashOrderItem.setCashOrderType(TestData.CASH_ORDER_TYPE);
    cashOrderItem.setCreatedDttm(xmlCalendar(2017, 4, 17, 17, 0));
    cashOrderItem.setOperationId(TestData.OPERATION_ID);
    cashOrderItem.setRepFIO(TestData.REP_FIO);
    cashOrderItem.setLegalEntityShortName(TestData.LEGAL_ENTITY_SHORT_NAME);
    cashOrderItem.setINN(TestData.INN);
    cashOrderItem.setAmount(TestData.AMOUNT);
    cashOrderItem.setAccountId(TestData.ACCOUNT_ID);
    cashOrderItem.setSenderBank(TestData.SENDER_BANK);
    cashOrderItem.setSenderBankBIC(TestData.SENDER_BANK_BIC);
    cashOrderItem.setRecipientBank(TestData.RECIPIENT_BANK);
    cashOrderItem.setRecipientBankBIC(TestData.RECIPIENT_BANK_BIC);
    cashOrderItem.setClientTypeFK(TestData.CLIENT_TYPE_FK);
    cashOrderItem.setFDestLEName(TestData.F_DEST_LE_NAME);
    cashOrderItem.setSubbranchCode(TestData.SUBBRANCH_CODE);
    cashOrderItem.setUserLogin(TestData.USER_LOGIN);
    cashOrderItem.setUserFullName(TestData.USER_FULLNAME);
    cashOrderItem.setUserPosition(TestData.USER_POSITION);
    cashOrderItem.setCashSymbols(new CashOrderItem.CashSymbols());
    CashOrderItem.CashSymbols.CashSymbolItem item = new CashOrderItem.CashSymbols.CashSymbolItem();
    item.setCashSymbol(TestData.CASH_SYMBOL);
    item.setCashSymbolAmount(TestData.CASH_SYMBOL_AMOUNT);
    cashOrderItem.getCashSymbols().getCashSymbolItem().add(item);
    SrvGetCashOrderRsMessage messageGet = responseGet.getSrvGetCashOrderRsMessage();
    messageGet.getCashOrderItem().add(cashOrderItem);

    responseUpdate = new SrvUpdStCashOrderRs();
    responseUpdate.setHeaderInfo(headerInfo());
    responseUpdate.setSrvUpdCashOrderRsMessage(new SrvUpdCashOrderRsMessage());

    SrvUpdCashOrderRsMessage messageUpdate = responseUpdate.getSrvUpdCashOrderRsMessage();
    messageUpdate.setResponseCode(TestData.RESPONSE_CODE);
    messageUpdate.setResponseMsg(TestData.RESPONSE_MESSAGE);
    messageUpdate.setCashOrderId(TestData.CASH_ORDER_ID);
    messageUpdate.setCashOrderINum(TestData.CASH_ORDER_I_NUM);
    messageUpdate.setCashOrderStatus(TestData.CASH_ORDER_STATUS);
    messageUpdate.setCashOrderType(TestData.CASH_ORDER_TYPE);

    responseLimit = new SrvCheckOverLimitRs();
    responseLimit.setHeaderInfo(headerInfo());
    responseLimit.setSrvCheckOverLimitRsMessage(new SrvCheckOverLimitRsMessage());

    SrvCheckOverLimitRsMessage messageLimit = responseLimit.getSrvCheckOverLimitRsMessage();
    messageLimit.setResponseCode(TestData.RESPONSE_CODE);
    messageLimit.setStatus(LimitStatusType.LIMIT_PASSED);
  }

  @Test
  public void testRequestCreate() {
    SrvCreateCashOrderRq request = CashOrderAdapter.toRequestCreate(testData.getCashOrder());
    testRequestCreateAnyAdapter(request);
  }

  @Test
  public void testRequestCreateMapstruct() {
    SrvCreateCashOrderRq request = CashOrderMapstructAdapter.toRequestCreate(
        testData.getCashOrder());
    testRequestCreateAnyAdapter(request);
  }

  @Test
  public void testRequestGet() {
    CashOrderRequest cashOrderRequest = new CashOrderRequest();
    cashOrderRequest.setCreatedFrom(date(2017, 1, 17, 17, 0));
    cashOrderRequest.setCreatedTo(date(2017, 5, 17, 17, 0));
    SrvGetCashOrderRq request = CashOrderAdapter.toRequestGet(cashOrderRequest);
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertNotNull(request.getSrvGetCashOrderRqMessage());
    Assert.assertEquals(request.getSrvGetCashOrderRqMessage().getCreatedFrom(),
        TestData.CREATED_FROM);
    Assert.assertEquals(request.getSrvGetCashOrderRqMessage().getCreatedTo(), TestData.CREATED_TO);
  }

  @Test
  public void testRequestGetMapstruct() {
    CashOrderRequest cashOrderRequest = new CashOrderRequest();
    cashOrderRequest.setCreatedFrom(date(2017, 1, 17, 17, 0));
    cashOrderRequest.setCreatedTo(date(2017, 5, 17, 17, 0));
    SrvGetCashOrderRq request = CashOrderMapstructAdapter.toRequestGet(cashOrderRequest);
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertNotNull(request.getSrvGetCashOrderRqMessage());
    Assert.assertEquals(request.getSrvGetCashOrderRqMessage().getCreatedFrom(),
        TestData.CREATED_FROM);
    Assert.assertEquals(request.getSrvGetCashOrderRqMessage().getCreatedTo(), TestData.CREATED_TO);
  }

  @Test
  public void testRequestUpdate() {
    SrvUpdStCashOrderRq request = CashOrderAdapter.toRequestUpdate(testData.getCashOrder());
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertNotNull(request.getSrvUpdCashOrderRqMessage());
    Assert.assertEquals(request.getSrvUpdCashOrderRqMessage().getCashOrderId(),
        TestData.CASH_ORDER_ID);
    Assert.assertEquals(request.getSrvUpdCashOrderRqMessage().getCashOrderStatus(),
        TestData.CASH_ORDER_STATUS);
  }

  @Test
  public void testRequestUpdateMapstruct() {
    SrvUpdStCashOrderRq request = CashOrderMapstructAdapter.toRequestUpdate(
        testData.getCashOrder());
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertNotNull(request.getSrvUpdCashOrderRqMessage());
    Assert.assertEquals(request.getSrvUpdCashOrderRqMessage().getCashOrderId(),
        TestData.CASH_ORDER_ID);
    Assert.assertEquals(request.getSrvUpdCashOrderRqMessage().getCashOrderStatus(),
        TestData.CASH_ORDER_STATUS);
  }

  @Test
  public void testRequestCheckOverLimit() {
    SrvCheckOverLimitRq request = CashOrderAdapter.toRequestCheckOverLimit(testData.getCashOrder());
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertEquals(request.getSrvCheckOverLimitRqMessage().getAmount(), TestData.AMOUNT);
    Assert.assertEquals(request.getSrvCheckOverLimitRqMessage().getUserLogin(),
        TestData.USER_LOGIN);
  }

  @Test
  public void testRequestCheckOverLimitMapstruct() {
    SrvCheckOverLimitRq request = CashOrderMapstructAdapter.toCheckOverLimitRequest(
        testData.getCheckOverLimitRequest());
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertEquals(request.getSrvCheckOverLimitRqMessage().getAmount(), TestData.AMOUNT);
    Assert.assertEquals(request.getSrvCheckOverLimitRqMessage().getUserLogin(),
        TestData.USER_LOGIN);
  }

  @Test
  public void testConvertSrvCreateCashOrderRs() {
    CashOrder cashOrder = CashOrderAdapter.convert(responseCreate);
    testCreateGetCashOrderRq(cashOrder);
  }

  @Test
  public void testConvertSrvCreateCashOrderRsMapstruct() {
    CashOrder cashOrder = CashOrderMapstructAdapter.convert(responseCreate);
    testCreateGetCashOrderRq(cashOrder);
  }

  @Test
  public void testConvertSrvGetCashOrderRs() {
    ExternalEntityList<CashOrder> cashOrders = CashOrderAdapter.convert(responseGet);
    assertHeaderInfo(cashOrders);
    Assert.assertEquals(cashOrders.getItems().size(), 1);
    testCreateGetCashOrderRq(cashOrders.getItems().get(0));
  }

  @Test
  public void testConvertSrvGetCashOrderRsMapstruct() {
    ExternalEntityList<CashOrder> cashOrders = CashOrderMapstructAdapter.convert(responseGet);
    assertHeaderInfo(cashOrders);
    Assert.assertEquals(cashOrders.getItems().size(), 1);
    testCreateGetCashOrderRq(cashOrders.getItems().get(0));
  }

  @Test
  public void testConvertSrvUpdStCashOrderRs() {
    CashOrder cashOrder = CashOrderAdapter.convert(responseUpdate);
    testCreateGetCashOrderRs(cashOrder);
  }

  @Test
  public void testConvertSrvUpdStCashOrderRsMapstruct() {
    CashOrder cashOrder = CashOrderMapstructAdapter.convert(responseUpdate);
    testCreateGetCashOrderRs(cashOrder);
  }

  @Test
  public void testConvertSrvCheckOverLimitRs() {
    ExternalEntityContainer<Boolean> container = CashOrderAdapter.convert(responseLimit);
    assertHeaderInfo(container);
    Assert.assertEquals(container.getResponseCode(), TestData.RESPONSE_CODE);
    Assert.assertTrue(container.getData());
  }

  @Test
  public void testConvertSrvCheckOverLimitRsMapstruct() {
    ExternalEntityContainer<Boolean> container = CashOrderMapstructAdapter.convert(responseLimit);
    assertHeaderInfo(container);
    Assert.assertEquals(container.getResponseCode(), TestData.RESPONSE_CODE);
    Assert.assertTrue(container.getData());
  }

  @Test
  public void testMultiAdapter() {
    ExternalEntity externalEntityCreate = MultiAdapter.convert(responseCreate);
    Assert.assertNotNull(externalEntityCreate);
    Assert.assertEquals(externalEntityCreate.getClass(), CashOrder.class);

    ExternalEntity externalEntityGet = MultiAdapter.convert(responseGet);
    Assert.assertNotNull(externalEntityGet);
    Assert.assertEquals(externalEntityGet.getClass(), ExternalEntityList.class);
    Assert.assertEquals(((ExternalEntityList) externalEntityGet).getItems().size(), 1);
    Assert.assertEquals(((ExternalEntityList) externalEntityGet).getItems().get(0).getClass(),
        CashOrder.class);

    ExternalEntity externalEntityUpdate = MultiAdapter.convert(responseUpdate);
    Assert.assertNotNull(externalEntityUpdate);
    Assert.assertEquals(externalEntityUpdate.getClass(), CashOrder.class);

    ExternalEntity externalEntityCheckOverLimit = MultiAdapter.convert(responseLimit);
    Assert.assertNotNull(externalEntityCheckOverLimit);
    Assert.assertEquals(externalEntityCheckOverLimit.getClass(), ExternalEntityContainer.class);
  }

  private void testCreateGetCashOrderRq(CashOrder cashOrder) {
    testCreateGetCashOrderRs(cashOrder);
    Assert.assertEquals(cashOrder.getCreatedDttm(), TestData.CREATED_DTTM);
    Assert.assertEquals(cashOrder.getOperationId(), TestData.OPERATION_ID);
    Assert.assertEquals(cashOrder.getRepresentative().getRepFio(), TestData.REP_FIO);
    Assert.assertEquals(cashOrder.getLegalEntityShortName(), TestData.LEGAL_ENTITY_SHORT_NAME);
    Assert.assertEquals(cashOrder.getRepresentative().getInn(), TestData.INN);
    Assert.assertEquals(cashOrder.getAmount(), TestData.AMOUNT);
    Assert.assertEquals(cashOrder.getAccountId(), TestData.ACCOUNT_ID);
    Assert.assertEquals(cashOrder.getCashSymbols().get(0).getCode(), TestData.CASH_SYMBOL);
    Assert.assertEquals(cashOrder.getCashSymbols().get(0).getAmount(), TestData.CASH_SYMBOL_AMOUNT);
    Assert.assertEquals(cashOrder.getSenderBank(), TestData.SENDER_BANK);
    Assert.assertEquals(cashOrder.getSenderBankBic(), TestData.SENDER_BANK_BIC);
    Assert.assertEquals(cashOrder.getRecipientBank(), TestData.RECIPIENT_BANK);
    Assert.assertEquals(cashOrder.getRecipientBankBic(), TestData.RECIPIENT_BANK_BIC);
    Assert.assertEquals(cashOrder.getClientTypeFk(), TestData.CLIENT_TYPE_FK);
    Assert.assertEquals(cashOrder.getDestName(), TestData.F_DEST_LE_NAME);
    Assert.assertEquals(cashOrder.getOperator().getSubbranch().getSubbranchCode(),
        TestData.SUBBRANCH_CODE);
    Assert.assertEquals(cashOrder.getUserLogin(), TestData.USER_LOGIN);
    Assert.assertEquals(cashOrder.getOperator().getOperatorFullName(), TestData.USER_FULLNAME);
    Assert.assertEquals(cashOrder.getUserPosition(), TestData.USER_POSITION);
  }

  private void testCreateGetCashOrderRs(CashOrder cashOrder) {
    assertHeaderInfo(cashOrder);
    Assert.assertEquals(cashOrder.getResponseCode(), TestData.RESPONSE_CODE);
    Assert.assertEquals(cashOrder.getResponseMsg(), TestData.RESPONSE_MESSAGE);
    Assert.assertEquals(cashOrder.getCashOrderId(), TestData.CASH_ORDER_ID);
    Assert.assertEquals(cashOrder.getCashOrderINum(), TestData.CASH_ORDER_I_NUM);
    Assert.assertEquals(cashOrder.getCashOrderStatus().value(), TestData.CASH_ORDER_STATUS.value());
    Assert.assertEquals(cashOrder.getCashOrderType().value(), TestData.CASH_ORDER_TYPE.value());
  }

  private void testRequestCreateAnyAdapter(SrvCreateCashOrderRq request) {
    assertHeaderInfo(request.getHeaderInfo());
    SrvCreateCashOrderRqMessage message = request.getSrvCreateCashOrderRqMessage();
    List<RepData.IdentityDocumentType> identityDocuments = message.getRepData()
        .getIdentityDocumentType();
    List<AdditionalInfo.CashSymbols.CashSymbolItem> cashSymbolItems = message.getAdditionalInfo()
        .getCashSymbols().getCashSymbolItem();
    Assert.assertNotNull(message);
    Assert.assertEquals(message.getCashOrderId(), TestData.CASH_ORDER_ID);
    Assert.assertEquals(message.getOperationType(), TestData.OPERATION_TYPE);
    Assert.assertEquals(message.getCashOrderINum(), TestData.CASH_ORDER_I_NUM);
    Assert.assertEquals(message.getAccountId(), TestData.ACCOUNT_ID);
    Assert.assertEquals(message.getAmount(), TestData.AMOUNT);
    Assert.assertEquals(message.getAmountInWords(), TestData.AMOUNT_IN_WORDS);
    Assert.assertEquals(message.getCurrencyType(), TestData.CURRENCY_TYPE);
    Assert.assertEquals(message.getCashOrderStatus(), TestData.CASH_ORDER_STATUS);
    Assert.assertEquals(message.getWorkPlaceUId(), TestData.WORKPLACE_ID);
    Assert.assertEquals(message.getRepData().getRepID(), TestData.REP_ID);
    Assert.assertEquals(message.getRepData().getRepFIO(), TestData.REP_FIO);
    Assert.assertEquals(message.getRepData().getAddress(), TestData.REP_Address);
    Assert.assertEquals(message.getRepData().getDateOfBirth(), TestData.REP_BIRTH_DATE_XML);
    Assert.assertEquals(message.getRepData().getPlaceOfBirth(), TestData.REP_PLACE_OF_BIRTH);
    Assert.assertTrue(message.getRepData().isResident());
    Assert.assertEquals(message.getRepData().getINN(), TestData.INN);
    Assert.assertEquals(identityDocuments.get(0).getValue(), TestData.IDENTITY_DOCUMENT_TYPE);
    Assert.assertEquals(identityDocuments.get(0).getSeries(), TestData.IDENTITY_DOCUMENT_SERIES);
    Assert.assertEquals(identityDocuments.get(0).getNumber(), TestData.IDENTITY_DOCUMENT_NUMBER);
    Assert.assertEquals(identityDocuments.get(0).getIssuedBy(),
        TestData.IDENTITY_DOCUMENT_ISSUED_BY);
    Assert.assertEquals(identityDocuments.get(0).getIssuedDate(),
        TestData.IDENTITY_DOCUMENT_ISSUED_DATE_XML);
    Assert.assertEquals(message.getAdditionalInfo().getComment(), TestData.COMMENT);
    Assert.assertEquals(message.getAdditionalInfo().getSubbranchCode(), TestData.SUBBRANCH_CODE);
    Assert.assertEquals(message.getAdditionalInfo().getVSPCode(), TestData.SUBBRANCH_VSP_CODE);
    Assert.assertEquals(message.getAdditionalInfo().getOSBCode(), TestData.SUBBRANCH_OSB_CODE);
    Assert.assertEquals(message.getAdditionalInfo().getGOSBCode(), TestData.SUBBRANCH_GOSB_CODE);
    Assert.assertEquals(message.getAdditionalInfo().getTBCode(), TestData.SUBBRANCH_TB_CODE);
    Assert.assertEquals(message.getAdditionalInfo().getAccount20202Num(),
        TestData.ACCOUNT_20202_NUM);
    Assert.assertEquals(message.getAdditionalInfo().getUserLogin(), TestData.USER_LOGIN);
    Assert.assertEquals(cashSymbolItems.get(0).getCashSymbol(), TestData.CASH_SYMBOL);
    Assert.assertEquals(cashSymbolItems.get(0).getCashSymbolAmount(), TestData.CASH_SYMBOL_AMOUNT);
  }
}
