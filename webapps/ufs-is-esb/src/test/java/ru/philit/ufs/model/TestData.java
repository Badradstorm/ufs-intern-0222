package ru.philit.ufs.model;

import static ru.philit.ufs.model.converter.ConverterBaseTest.date;
import static ru.philit.ufs.model.converter.ConverterBaseTest.xmlCalendar;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import lombok.Getter;
import org.junit.Assert;
import ru.philit.ufs.model.converter.esb.eks.EksAdapterBaseTest;
import ru.philit.ufs.model.entity.account.Account;
import ru.philit.ufs.model.entity.account.AccountType;
import ru.philit.ufs.model.entity.account.AccountancyType;
import ru.philit.ufs.model.entity.account.IdentityDocument;
import ru.philit.ufs.model.entity.account.IdentityDocumentType;
import ru.philit.ufs.model.entity.account.Representative;
import ru.philit.ufs.model.entity.common.ExternalEntityList;
import ru.philit.ufs.model.entity.common.ExternalEntityRequest;
import ru.philit.ufs.model.entity.common.OperationTypeCode;
import ru.philit.ufs.model.entity.esb.asfs.CashOrderStatusType;
import ru.philit.ufs.model.entity.esb.asfs.CashOrderType;
import ru.philit.ufs.model.entity.esb.asfs.IDDtype;
import ru.philit.ufs.model.entity.esb.asfs.OperTypeLabel;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountByCardNumRs;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountByCardNumRs.SrvAccountByCardNumRsMessage;
import ru.philit.ufs.model.entity.oper.CashSymbol;
import ru.philit.ufs.model.entity.order.CashOrder;
import ru.philit.ufs.model.entity.order.CashOrderStatus;
import ru.philit.ufs.model.entity.order.CheckOverLimitRequest;
import ru.philit.ufs.model.entity.request.RequestType;
import ru.philit.ufs.model.entity.user.Operator;
import ru.philit.ufs.model.entity.user.Subbranch;

/**
 * Контейнер данных используемых в тестировании интеграции.
 */
public class TestData {

  public static final String RESPONSE_CODE = "1222";
  public static final String RESPONSE_MESSAGE = "ok";

  public static final String CASH_ORDER_ID = "1212";
  public static final String CASH_ORDER_I_NUM = "0102";
  public static final CashOrderStatusType CASH_ORDER_STATUS = CashOrderStatusType.CREATED;
  public static final CashOrderType CASH_ORDER_TYPE = CashOrderType.KO_1;

  public static final Date CREATED_DTTM = date(2017, 4, 17, 17, 0);
  public static final XMLGregorianCalendar CREATED_DTTM_XML = xmlCalendar(2017, 4, 17, 17, 0);
  public static final XMLGregorianCalendar CREATED_FROM = xmlCalendar(2017, 1, 17, 17, 0);
  public static final XMLGregorianCalendar CREATED_TO = xmlCalendar(2017, 5, 17, 17, 0);
  public static final String OPERATION_ID = "1254";

  public static final String REP_FIO = "FIO";
  public static final String REP_ID = "777";
  public static final String REP_Address = "Москва";
  public static final Date REP_BIRTH_DATE = date(2000, 5, 4, 0, 0);
  public static final XMLGregorianCalendar REP_BIRTH_DATE_XML = xmlCalendar(2000, 5, 4, 0, 0);
  public static final String REP_PLACE_OF_BIRTH = "Мурманск";
  public static final Boolean REP_RESIDENT = true;
  public static final String LEGAL_ENTITY_SHORT_NAME = "UFO";
  public static final String INN = "15445";

  public static final IDDtype IDENTITY_DOCUMENT_TYPE = IDDtype.PASSPORT;
  public static final String IDENTITY_DOCUMENT_ISSUED_BY = "ОВД Москвы";
  public static final Date IDENTITY_DOCUMENT_ISSUED_DATE = date(2014, 5, 4, 0, 0);
  public static final XMLGregorianCalendar IDENTITY_DOCUMENT_ISSUED_DATE_XML = xmlCalendar(2014, 5,
      4, 0, 0);
  public static final String IDENTITY_DOCUMENT_NUMBER = "1111111";
  public static final String IDENTITY_DOCUMENT_SERIES = "5101";

  public static final BigDecimal AMOUNT = BigDecimal.valueOf(3.0);
  public static final String AMOUNT_IN_WORDS = "сто";
  public static final String SENDER_BANK = "sndbnk";
  public static final String SENDER_BANK_BIC = "145544";
  public static final String RECIPIENT_BANK = "rcpbnk";
  public static final String RECIPIENT_BANK_BIC = "54879";
  public static final Boolean CLIENT_TYPE_FK = true;
  public static final String F_DEST_LE_NAME = "name";

  public static final String SUBBRANCH_CODE = "114554";

  public static final String USER_LOGIN = "ser";
  public static final String USER_FULLNAME = "erjan";
  public static final String USER_POSITION = "oper";

  public static final String CASH_SYMBOL = "rub";
  public static final BigDecimal CASH_SYMBOL_AMOUNT = BigDecimal.valueOf(2.0);
  public static final OperTypeLabel OPERATION_TYPE = OperTypeLabel.TO_CARD_DEPOSIT;

  public static final String WORKPLACE_ID = "1212222";
  public static final String COMMENT = "коммент";

  public static final String SUBBRANCH_VSP_CODE = "789";
  public static final String SUBBRANCH_OSB_CODE = "456";
  public static final String SUBBRANCH_GOSB_CODE = "123";
  public static final String SUBBRANCH_TB_CODE = "741";
  public static final String ACCOUNT_20202_NUM = "753";

  public static final String SESSION_ID = "444";
  public static final String CARD_NUMBER = "4894123569871254";
  public static final String ACCOUNT_ID = "111111";
  public static final String LEGAL_ENTITY_ID = "112";
  public static final String AGREEMENT_ID = "45678";

  public static final String ACCOUNT_TYPE_ID = "01";
  public static final String ACCOUNT_STATUS = "status1";
  public static final int ACCOUNTANCY_TYPE_ID = 2;

  public static final String BANK_BIC = "04857512";
  public static final String BANK_NAME = "Отделение № 2";
  public static final String BANK_CORR_ACC = "30845214660004854009";
  public static final String BANK_INN = "07754854112";
  public static final String BANK_TB = "13";
  public static final String BANK_GOSB = "8456";
  public static final String BANK_OSB = "8463";
  public static final String BANK_VSP = "13587";
  public static final String BANK_SUBBRANCH_ID = "1234567";
  public static final String BANK_SUBBRANCH_CODE = "8463/13587";
  public static final Long BANK_SUBBRANCH_LEVEL = 4L;

  public static final String CURRENCY_TYPE = "RUB";
  public static final String CURRENCY_CODE = "642";

  public static final String ACCOUNT_REQUEST_FIX_UUID = "4f04ce04-ac37-4ec9-9923-6a9a5a882a97";
  public static final String ACCOUNT_REQUEST_TIME = "2017-05-12T17:57:00.000+03:00";
  public static final String ACCOUNT_REQUEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" "
      + "standalone=\"yes\"?><SrvAccountByCardNumRq><HeaderInfo><rqUID>" + ACCOUNT_REQUEST_FIX_UUID
      + "</rqUID><rqTm>" + ACCOUNT_REQUEST_TIME + "</rqTm><spName>ufs</spName><systemId>eks"
      + "</systemId></HeaderInfo><SrvAccountByCardNumRqMessage><cardNumber>" + CARD_NUMBER
      + "</cardNumber></SrvAccountByCardNumRqMessage></SrvAccountByCardNumRq>";
  public static final String ACCOUNT_RESPONSE_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" "
      + "standalone=\"yes\"?><SrvAccountByCardNumRs><HeaderInfo><rqUID>" + ACCOUNT_REQUEST_FIX_UUID
      + "</rqUID><rqTm>" + ACCOUNT_REQUEST_TIME + "</rqTm><spName>ufs</spName><systemId>eks"
      + "</systemId></HeaderInfo><SrvAccountByCardNumRsMessage><accountId>" + ACCOUNT_ID
      + "</accountId><accountStatus>" + ACCOUNT_STATUS + "</accountStatus><accountStatusDesc>"
      + "Счет активен</accountStatusDesc><accountTypeId>" + ACCOUNT_TYPE_ID + "</accountTypeId>"
      + "<accountCurrencyCode>" + CURRENCY_CODE + "</accountCurrencyCode><accountCurrencyType>"
      + CURRENCY_TYPE + "</accountCurrencyType><bankBIC>" + BANK_BIC + "</bankBIC><agreementInfo>"
      + "<agreementId>" + AGREEMENT_ID + "</agreementId><dateFrom>2017-05-01+03:00</dateFrom>"
      + "<dateTo>2017-07-01+03:00</dateTo></agreementInfo><bankInfo><bankBIC>" + BANK_BIC
      + "</bankBIC><correspondentAccount>" + BANK_CORR_ACC + "</correspondentAccount><bankName>"
      + BANK_NAME + "</bankName><locationTitle>Москва, ул. Академика Янгеля, д. 13 к. 3"
      + "</locationTitle><locationType>type3</locationType><TBCode>" + BANK_TB + "</TBCode>"
      + "<GOSBCode>" + BANK_GOSB + "</GOSBCode><OSBCode>" + BANK_OSB + "</OSBCode><VSPCode>"
      + BANK_VSP + "</VSPCode><subbranchCode>" + BANK_SUBBRANCH_CODE + "</subbranchCode></bankInfo>"
      + "<accountIdentity><IDtype>Unknown identity</IDtype><ID>112255</ID></accountIdentity>"
      + "<accountTitle>Счет клиента</accountTitle><accountancyTypeId>" + ACCOUNTANCY_TYPE_ID
      + "</accountancyTypeId><lastTransactionDt>2017-05-04+03:00</lastTransactionDt>"
      + "</SrvAccountByCardNumRsMessage></SrvAccountByCardNumRs>";

  @Getter
  private ExternalEntityRequest accountByCardNumberRequest;
  @Getter
  private Account account;
  @Getter
  private SrvAccountByCardNumRs accountResponse;
  @Getter
  private CashOrder cashOrder;
  @Getter
  private CheckOverLimitRequest checkOverLimitRequest;
  @Getter
  private ExternalEntityList<CashOrder> cashOrders;

  /**
   * Конструктор контейнера данных.
   */
  public TestData() {
    accountByCardNumberRequest = new ExternalEntityRequest();
    accountByCardNumberRequest.setSessionId(SESSION_ID);
    accountByCardNumberRequest.setEntityType(RequestType.ACCOUNT_BY_CARD_NUMBER);
    accountByCardNumberRequest.setRequestData(CARD_NUMBER);

    account = new Account();
    account.setRequestUid(ACCOUNT_REQUEST_FIX_UUID);
    account.setReceiveDate(new Date());
    account.setId(ACCOUNT_ID);
    account.setStatus(ACCOUNT_STATUS);
    account.setChangeStatusDescription("Счет активен");
    account.setType(AccountType.getByCode(ACCOUNT_TYPE_ID));
    account.setAccountancyType(AccountancyType.getByCode(ACCOUNTANCY_TYPE_ID));
    account.setCurrencyType(CURRENCY_TYPE);
    account.setCurrencyCode(CURRENCY_CODE);
    account.setTitle("Счет клиента");
    account.setLastTransactionDate(date(2017, 5, 4, 0, 0));
    account.getAgreement().setId(AGREEMENT_ID);
    account.getAgreement().setOpenDate(date(2017, 5, 1, 0, 0));
    account.getAgreement().setCloseDate(date(2017, 7, 1, 0, 0));
    account.getSubbranch().setId(BANK_SUBBRANCH_ID);
    account.getSubbranch().setTbCode(BANK_TB);
    account.getSubbranch().setGosbCode(BANK_GOSB);
    account.getSubbranch().setOsbCode(BANK_OSB);
    account.getSubbranch().setVspCode(BANK_VSP);
    account.getSubbranch().setSubbranchCode(BANK_SUBBRANCH_CODE);
    account.getSubbranch().setLevel(BANK_SUBBRANCH_LEVEL);
    account.getSubbranch().setInn(BANK_INN);
    account.getSubbranch().setBic(BANK_BIC);
    account.getSubbranch().setBankName(BANK_NAME);
    account.getSubbranch().setCorrespondentAccount(BANK_CORR_ACC);
    account.getSubbranch().setLocationTitle("Москва, ул. Академика Янгеля, д. 13 к. 3");
    account.getSubbranch().setLocationType("type3");
    account.getIdentity().setId("112255");
    account.getIdentity().setType("Unknown identity");

    accountResponse = new SrvAccountByCardNumRs();
    accountResponse.setHeaderInfo(EksAdapterBaseTest.headerInfo(ACCOUNT_REQUEST_FIX_UUID));
    accountResponse.getHeaderInfo().setRqTm(xmlCalendar(2017, 5, 12, 17, 57));
    accountResponse.setSrvAccountByCardNumRsMessage(new SrvAccountByCardNumRsMessage());
    accountResponse.getSrvAccountByCardNumRsMessage().setAccountId(ACCOUNT_ID);
    accountResponse.getSrvAccountByCardNumRsMessage().setAccountStatus(ACCOUNT_STATUS);
    accountResponse.getSrvAccountByCardNumRsMessage().setAccountStatusDesc("Счет активен");
    accountResponse.getSrvAccountByCardNumRsMessage().setAccountTypeId(ACCOUNT_TYPE_ID);
    accountResponse.getSrvAccountByCardNumRsMessage().setAccountCurrencyType(CURRENCY_TYPE);
    accountResponse.getSrvAccountByCardNumRsMessage().setAccountCurrencyCode(CURRENCY_CODE);
    accountResponse.getSrvAccountByCardNumRsMessage().setBankBIC(BANK_BIC);
    accountResponse.getSrvAccountByCardNumRsMessage().setAccountTitle("Счет клиента");
    accountResponse.getSrvAccountByCardNumRsMessage().setAccountancyTypeId(
        BigInteger.valueOf(ACCOUNTANCY_TYPE_ID));
    accountResponse.getSrvAccountByCardNumRsMessage().setLastTransactionDt(
        xmlCalendar(2017, 5, 4, 0, 0));
    SrvAccountByCardNumRsMessage.AgreementInfo agreementInfo =
        new SrvAccountByCardNumRsMessage.AgreementInfo();
    agreementInfo.setAgreementId(AGREEMENT_ID);
    agreementInfo.setDateFrom(xmlCalendar(2017, 5, 1, 0, 0));
    agreementInfo.setDateTo(xmlCalendar(2017, 7, 1, 0, 0));
    accountResponse.getSrvAccountByCardNumRsMessage().setAgreementInfo(agreementInfo);
    SrvAccountByCardNumRsMessage.BankInfo bankInfo = new SrvAccountByCardNumRsMessage.BankInfo();
    bankInfo.setBankBIC(BANK_BIC);
    bankInfo.setCorrespondentAccount(BANK_CORR_ACC);
    bankInfo.setBankName(BANK_NAME);
    bankInfo.setLocationTitle("Москва, ул. Академика Янгеля, д. 13 к. 3");
    bankInfo.setLocationType("type3");
    bankInfo.setTBCode(BANK_TB);
    bankInfo.setGOSBCode(BANK_GOSB);
    bankInfo.setOSBCode(BANK_OSB);
    bankInfo.setVSPCode(BANK_VSP);
    bankInfo.setSubbranchCode(BANK_SUBBRANCH_CODE);
    accountResponse.getSrvAccountByCardNumRsMessage().setBankInfo(bankInfo);
    SrvAccountByCardNumRsMessage.AccountIdentity accountIdentity =
        new SrvAccountByCardNumRsMessage.AccountIdentity();
    accountIdentity.setID("112255");
    accountIdentity.setIDtype("Unknown identity");
    accountResponse.getSrvAccountByCardNumRsMessage().setAccountIdentity(accountIdentity);

    cashOrder = new CashOrder();
    cashOrder.setResponseCode(RESPONSE_CODE);
    cashOrder.setResponseMsg(RESPONSE_MESSAGE);
    cashOrder.setCashOrderId(CASH_ORDER_ID);
    cashOrder.setCashOrderINum(CASH_ORDER_I_NUM);
    cashOrder.setAccountId(ACCOUNT_ID);
    cashOrder.setAmount(AMOUNT);
    cashOrder.setCashOrderStatus(CashOrderStatus.CREATED);
    cashOrder.setCashOrderType(ru.philit.ufs.model.entity.order.CashOrderType.KO_1);
    cashOrder.setCreatedDttm(date(2017, 4, 17, 17, 0));
    cashOrder.setOperationId(OPERATION_ID);
    cashOrder.setSenderBank(SENDER_BANK);
    cashOrder.setSenderBankBic(SENDER_BANK_BIC);
    cashOrder.setRecipientBank(RECIPIENT_BANK);
    cashOrder.setRecipientBankBic(RECIPIENT_BANK_BIC);
    cashOrder.setClientTypeFk(CLIENT_TYPE_FK);
    cashOrder.setDestName(F_DEST_LE_NAME);
    cashOrder.setLegalEntityShortName(LEGAL_ENTITY_SHORT_NAME);
    cashOrder.setRepresentative(new Representative());
    cashOrder.getRepresentative().setInn(INN);
    cashOrder.setOperator(new Operator());
    cashOrder.getOperator().setSubbranch(new Subbranch());
    cashOrder.getOperator().getSubbranch().setSubbranchCode(SUBBRANCH_CODE);
    cashOrder.setUserLogin(USER_LOGIN);
    cashOrder.getOperator().setOperatorFullName(USER_FULLNAME);
    cashOrder.setUserPosition(USER_POSITION);
    cashOrder.setCashSymbols(new ArrayList<>());
    CashSymbol cashSymbol = new CashSymbol();
    cashSymbol.setAmount(CASH_SYMBOL_AMOUNT);
    cashSymbol.setCode(CASH_SYMBOL);
    cashOrder.getCashSymbols().add(cashSymbol);
    cashOrder.setOperationType(OperationTypeCode.TO_CARD_DEPOSIT);
    cashOrder.setAmountInWords(AMOUNT_IN_WORDS);
    cashOrder.setCurrencyType(CURRENCY_TYPE);
    cashOrder.getOperator().setWorkplaceId(WORKPLACE_ID);
    cashOrder.getRepresentative().setId(REP_ID);
    cashOrder.getRepresentative().setRepFio(REP_FIO);
    cashOrder.getRepresentative().setAddress(REP_Address);
    cashOrder.getRepresentative().setBirthDate(REP_BIRTH_DATE);
    cashOrder.getRepresentative().setPlaceOfBirth(REP_PLACE_OF_BIRTH);
    cashOrder.getRepresentative().setResident(REP_RESIDENT);
    IdentityDocument identityDocument = new IdentityDocument();
    identityDocument.setType(IdentityDocumentType.PASSPORT);
    identityDocument.setIssuedBy(IDENTITY_DOCUMENT_ISSUED_BY);
    identityDocument.setIssuedDate(IDENTITY_DOCUMENT_ISSUED_DATE);
    identityDocument.setNumber(IDENTITY_DOCUMENT_NUMBER);
    identityDocument.setSeries(IDENTITY_DOCUMENT_SERIES);
    List<IdentityDocument> identityDocuments = new ArrayList<>();
    identityDocuments.add(identityDocument);
    cashOrder.getRepresentative().setIdentityDocuments(identityDocuments);
    cashOrder.setComment(COMMENT);
    cashOrder.getOperator().getSubbranch().setVspCode(SUBBRANCH_VSP_CODE);
    cashOrder.getOperator().getSubbranch().setOsbCode(SUBBRANCH_OSB_CODE);
    cashOrder.getOperator().getSubbranch().setGosbCode(SUBBRANCH_GOSB_CODE);
    cashOrder.getOperator().getSubbranch().setTbCode(SUBBRANCH_TB_CODE);
    cashOrder.setAccount20202Num(ACCOUNT_20202_NUM);

    cashOrders = new ExternalEntityList<>();
    cashOrders.getItems().add(cashOrder);

    checkOverLimitRequest = new CheckOverLimitRequest();
    checkOverLimitRequest.setTobeIncreased(true);
    checkOverLimitRequest.setUserLogin(USER_LOGIN);
    checkOverLimitRequest.setAmount(AMOUNT);
  }
}
