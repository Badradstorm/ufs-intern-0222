package ru.philit.ufs.model.converter.esb.asfs;

import java.util.ArrayList;
import ru.philit.ufs.model.entity.account.IdentityDocument;
import ru.philit.ufs.model.entity.account.Representative;
import ru.philit.ufs.model.entity.common.ExternalEntityContainer;
import ru.philit.ufs.model.entity.common.ExternalEntityList;
import ru.philit.ufs.model.entity.common.OperationTypeCode;
import ru.philit.ufs.model.entity.esb.asfs.CashOrderStatusType;
import ru.philit.ufs.model.entity.esb.asfs.CashOrderType;
import ru.philit.ufs.model.entity.esb.asfs.IDDtype;
import ru.philit.ufs.model.entity.esb.asfs.LimitStatusType;
import ru.philit.ufs.model.entity.esb.asfs.OperTypeLabel;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRq.SrvCheckOverLimitRqMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage.AdditionalInfo;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage.RepData;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage.RepData.IdentityDocumentType;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage.CashSymbols.CashSymbolItem;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetCashOrderRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetCashOrderRq.SrvGetCashOrderRqMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetCashOrderRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetCashOrderRs.SrvGetCashOrderRsMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetCashOrderRs.SrvGetCashOrderRsMessage.CashOrderItem;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetCashOrderRs.SrvGetCashOrderRsMessage.CashOrderItem.CashSymbols;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRq.SrvUpdCashOrderRqMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRs.SrvUpdCashOrderRsMessage;
import ru.philit.ufs.model.entity.oper.CashSymbol;
import ru.philit.ufs.model.entity.order.CashOrder;
import ru.philit.ufs.model.entity.order.CashOrderRequest;
import ru.philit.ufs.model.entity.order.CashOrderStatus;
import ru.philit.ufs.model.entity.user.Operator;
import ru.philit.ufs.model.entity.user.Subbranch;

/**
 * Преобразователь между сущностью CashOrder и соответствующим транспортным объектом.
 */
public class CashOrderAdapter extends AsfsAdapter {

  private static void map(SrvCreateCashOrderRsMessage response, CashOrder cashOrder) {
    cashOrder.setResponseCode(response.getResponseCode());
    cashOrder.setResponseMsg(response.getResponseMsg());
    cashOrder.setCashOrderId(response.getCashOrderId());
    cashOrder.setCashOrderINum(response.getCashOrderINum());
    cashOrder.setCashOrderStatus(convert(response.getCashOrderStatus()));
    cashOrder.setCashOrderType(convert(response.getCashOrderType()));
    cashOrder.setCreatedDttm(response.getCreatedDttm().toGregorianCalendar().getTime());
    cashOrder.setOperationId(response.getOperationId());
    cashOrder.setRepresentative(new Representative());
    cashOrder.getRepresentative().setRepFio(response.getRepFIO());
    cashOrder.setLegalEntityShortName(response.getLegalEntityShortName());
    cashOrder.setOperator(new Operator());
    cashOrder.getOperator().setSubbranch(new Subbranch());
    cashOrder.getRepresentative().setInn(response.getINN());
    cashOrder.setAmount(response.getAmount());
    cashOrder.setAccountId(response.getAccountId());
    cashOrder.setCashSymbols(new ArrayList<>());
    cashOrder.setSenderBank(response.getSenderBank());
    cashOrder.setSenderBankBic(response.getSenderBankBIC());
    cashOrder.setRecipientBank(response.getRecipientBank());
    cashOrder.setRecipientBankBic(response.getRecipientBankBIC());
    cashOrder.setClientTypeFk(response.isClientTypeFK());
    cashOrder.setDestName(response.getFDestLEName());
    cashOrder.getOperator().getSubbranch().setSubbranchCode(response.getSubbranchCode());
    cashOrder.setUserLogin(response.getUserLogin());
    cashOrder.getOperator().setOperatorFullName(response.getUserFullName());
    cashOrder.setUserPosition(response.getUserPosition());
    for (CashSymbolItem item : response.getCashSymbols().getCashSymbolItem()) {
      cashOrder.getCashSymbols().add(convert(item));
    }
  }

  private static void map(CashOrderItem response, CashOrder cashOrder) {
    cashOrder.setResponseCode(response.getResponseCode());
    cashOrder.setResponseMsg(response.getResponseMsg());
    cashOrder.setCashOrderId(response.getCashOrderId());
    cashOrder.setCashOrderINum(response.getCashOrderINum());
    cashOrder.setCashOrderStatus(convert(response.getCashOrderStatus()));
    cashOrder.setCashOrderType(convert(response.getCashOrderType()));
    cashOrder.setCreatedDttm(response.getCreatedDttm().toGregorianCalendar().getTime());
    cashOrder.setOperationId(response.getOperationId());
    cashOrder.setRepresentative(new Representative());
    cashOrder.getRepresentative().setRepFio(response.getRepFIO());
    cashOrder.setLegalEntityShortName(response.getLegalEntityShortName());
    cashOrder.setOperator(new Operator());
    cashOrder.getOperator().setSubbranch(new Subbranch());
    cashOrder.getRepresentative().setInn(response.getINN());
    cashOrder.setAmount(response.getAmount());
    cashOrder.setAccountId(response.getAccountId());
    cashOrder.setCashSymbols(new ArrayList<>());
    cashOrder.setSenderBank(response.getSenderBank());
    cashOrder.setSenderBankBic(response.getSenderBankBIC());
    cashOrder.setRecipientBank(response.getRecipientBank());
    cashOrder.setRecipientBankBic(response.getRecipientBankBIC());
    cashOrder.setClientTypeFk(response.isClientTypeFK());
    cashOrder.setDestName(response.getFDestLEName());
    cashOrder.getOperator().getSubbranch().setSubbranchCode(response.getSubbranchCode());
    cashOrder.setUserLogin(response.getUserLogin());
    cashOrder.getOperator().setOperatorFullName(response.getUserFullName());
    cashOrder.setUserPosition(response.getUserPosition());
    for (CashSymbols.CashSymbolItem item : response.getCashSymbols().getCashSymbolItem()) {
      cashOrder.getCashSymbols().add(convert(item));
    }
  }

  private static void map(SrvUpdCashOrderRsMessage response, CashOrder cashOrder) {
    cashOrder.setResponseCode(response.getResponseCode());
    cashOrder.setResponseMsg(response.getResponseMsg());
    cashOrder.setCashOrderId(response.getCashOrderId());
    cashOrder.setCashOrderINum(response.getCashOrderINum());
    cashOrder.setCashOrderStatus(convert(response.getCashOrderStatus()));
    cashOrder.setCashOrderType(convert(response.getCashOrderType()));
  }

  private static CashOrderStatus convert(CashOrderStatusType cashOrderStatusType) {
    switch (cashOrderStatusType.value()) {
      case "Created":
        return CashOrderStatus.CREATED;
      case "Committed":
        return CashOrderStatus.COMMITTED;
      default:
        return null;
    }
  }

  private static ru.philit.ufs.model.entity.order.CashOrderType convert(
      CashOrderType cashOrderType) {
    if (cashOrderType.value().equals(ru.philit.ufs.model.entity.order.CashOrderType.KO_1.value())) {
      return ru.philit.ufs.model.entity.order.CashOrderType.KO_1;
    }
    if (cashOrderType.value().equals(ru.philit.ufs.model.entity.order.CashOrderType.KO_2.value())) {
      return ru.philit.ufs.model.entity.order.CashOrderType.KO_2;
    }
    return null;
  }

  private static CashSymbol convert(CashSymbolItem cashSymbolItem) {
    CashSymbol cashSymbol = new CashSymbol();
    cashSymbol.setCode(cashSymbolItem.getCashSymbol());
    cashSymbol.setAmount(cashSymbolItem.getCashSymbolAmount());
    return cashSymbol;
  }

  private static CashSymbol convert(CashSymbols.CashSymbolItem cashSymbolItem) {
    CashSymbol cashSymbol = new CashSymbol();
    cashSymbol.setCode(cashSymbolItem.getCashSymbol());
    cashSymbol.setAmount(cashSymbolItem.getCashSymbolAmount());
    return cashSymbol;
  }

  private static AdditionalInfo.CashSymbols.CashSymbolItem convert(CashSymbol cashSymbol) {
    AdditionalInfo.CashSymbols.CashSymbolItem cashSymbolItem =
        new AdditionalInfo.CashSymbols.CashSymbolItem();
    cashSymbolItem.setCashSymbol(cashSymbol.getCode());
    cashSymbolItem.setCashSymbolAmount(cashSymbol.getAmount());
    return cashSymbolItem;
  }

  private static OperTypeLabel convert(OperationTypeCode operationTypeCode) {
    switch (operationTypeCode.code()) {
      case "ToCardDeposit":
        return OperTypeLabel.TO_CARD_DEPOSIT;
      case "FromCardWithdraw":
        return OperTypeLabel.FROM_CARD_WITHDRAW;
      case "ToAccountDepositRub":
        return OperTypeLabel.TO_ACCOUNT_DEPOSIT_RUB;
      case "FromAccountWithdrawRub":
        return OperTypeLabel.FROM_ACCOUNT_WITHDRAW_RUB;
      case "CheckbookIssuing":
        return OperTypeLabel.CHECKBOOK_ISSUING;
      default:
        return null;
    }
  }

  private static CashOrderStatusType convert(CashOrderStatus cashOrderStatus) {
    switch (cashOrderStatus.value()) {
      case "Created":
        return CashOrderStatusType.CREATED;
      case "Committed":
        return CashOrderStatusType.COMMITTED;
      default:
        return null;
    }
  }

  private static IdentityDocumentType convert(IdentityDocument identityDocument) {
    IdentityDocumentType identityDocumentType = new IdentityDocumentType();
    identityDocumentType.setValue(convert(identityDocument.getType()));
    identityDocumentType.setSeries(identityDocument.getSeries());
    identityDocumentType.setNumber(identityDocument.getNumber());
    identityDocumentType.setIssuedBy(identityDocument.getIssuedBy());
    identityDocumentType.setIssuedDate(xmlCalendar(identityDocument.getIssuedDate()));
    return identityDocumentType;
  }

  private static IDDtype convert(
      ru.philit.ufs.model.entity.account.IdentityDocumentType identityDocumentType) {
    switch (identityDocumentType.code()) {
      case "passport":
        return IDDtype.PASSPORT;
      case "internpassport":
        return IDDtype.INTERNPASSPORT;
      case "militaryID":
        return IDDtype.MILITARY_ID;
      case "seamenId":
        return IDDtype.SEAMEN_ID;
      default:
        return null;
    }
  }

  /**
   * Преобразует транспортный объект SrvCreateCashOrderRs во внутреннюю сущность CashOrder.
   */
  public static CashOrder convert(SrvCreateCashOrderRs response) {
    CashOrder cashOrder = new CashOrder();
    map(response.getHeaderInfo(), cashOrder);
    map(response.getSrvCreateCashOrderRsMessage(), cashOrder);
    return cashOrder;
  }

  /**
   * Преобразует транспортный объект SrvGetCashOrderRs в контейнер для списка внутренних сущностей.
   */
  public static ExternalEntityList<CashOrder> convert(SrvGetCashOrderRs response) {
    ExternalEntityList<CashOrder> cashOrders = new ExternalEntityList<>();
    for (SrvGetCashOrderRsMessage.CashOrderItem cashOrderItem
        : response.getSrvGetCashOrderRsMessage().getCashOrderItem()) {
      CashOrder cashOrder = new CashOrder();
      map(cashOrderItem, cashOrder);
      map(response.getHeaderInfo(), cashOrder);
      cashOrders.getItems().add(cashOrder);
    }
    map(response.getHeaderInfo(), cashOrders);
    return cashOrders;
  }

  /**
   * Преобразует транспортный объект SrvUpdCashOrderRs во внутреннюю сущность CashOrder.
   */
  public static CashOrder convert(SrvUpdStCashOrderRs response) {
    CashOrder cashOrder = new CashOrder();
    map(response.getHeaderInfo(), cashOrder);
    map(response.getSrvUpdCashOrderRsMessage(), cashOrder);
    return cashOrder;
  }

  /**
   * Преобразует транспортный объект SrvCheckOverLimitRs в контейнер.
   */
  public static ExternalEntityContainer<Boolean> convert(SrvCheckOverLimitRs response) {
    ExternalEntityContainer<Boolean> container = new ExternalEntityContainer<>();
    map(response.getHeaderInfo(), container);
    container.setResponseCode(response.getSrvCheckOverLimitRsMessage().getResponseCode());
    if (response.getSrvCheckOverLimitRsMessage().getStatus() == LimitStatusType.LIMIT_PASSED) {
      container.setData(true);
    } else {
      container.setData(false);
    }
    return container;
  }

  /**
   * Возвращает объект запроса создания кассового ордера.
   */
  public static SrvCreateCashOrderRq toRequestCreate(CashOrder cashOrder) {
    SrvCreateCashOrderRq request = new SrvCreateCashOrderRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvCreateCashOrderRqMessage(new SrvCreateCashOrderRqMessage());
    request.getSrvCreateCashOrderRqMessage().setCashOrderId(cashOrder.getCashOrderId());
    request.getSrvCreateCashOrderRqMessage()
        .setOperationType(convert(cashOrder.getOperationType()));
    request.getSrvCreateCashOrderRqMessage().setCashOrderINum(cashOrder.getCashOrderINum());
    request.getSrvCreateCashOrderRqMessage().setAccountId(cashOrder.getAccountId());
    request.getSrvCreateCashOrderRqMessage().setAmount(cashOrder.getAmount());
    request.getSrvCreateCashOrderRqMessage().setAmountInWords(cashOrder.getAmountInWords());
    request.getSrvCreateCashOrderRqMessage().setCurrencyType(cashOrder.getCurrencyType());
    request.getSrvCreateCashOrderRqMessage()
        .setCashOrderStatus(convert(cashOrder.getCashOrderStatus()));
    request.getSrvCreateCashOrderRqMessage()
        .setWorkPlaceUId(cashOrder.getOperator().getWorkplaceId());
    request.getSrvCreateCashOrderRqMessage().setRepData(new RepData());
    request.getSrvCreateCashOrderRqMessage().getRepData()
        .setRepID(cashOrder.getRepresentative().getId());
    request.getSrvCreateCashOrderRqMessage().getRepData()
        .setRepFIO(cashOrder.getRepresentative().getRepFio());
    request.getSrvCreateCashOrderRqMessage().getRepData()
        .setAddress(cashOrder.getRepresentative().getAddress());
    request.getSrvCreateCashOrderRqMessage().getRepData()
        .setDateOfBirth(xmlCalendar(cashOrder.getRepresentative().getBirthDate()));
    for (IdentityDocument document : cashOrder.getRepresentative().getIdentityDocuments()) {
      request.getSrvCreateCashOrderRqMessage().getRepData().getIdentityDocumentType()
          .add(convert(document));
    }
    request.getSrvCreateCashOrderRqMessage().getRepData()
        .setPlaceOfBirth(cashOrder.getRepresentative().getPlaceOfBirth());
    request.getSrvCreateCashOrderRqMessage().getRepData()
        .setResident(cashOrder.getRepresentative().isResident());
    request.getSrvCreateCashOrderRqMessage().getRepData()
        .setINN(cashOrder.getRepresentative().getInn());
    request.getSrvCreateCashOrderRqMessage().setAdditionalInfo(new AdditionalInfo());
    request.getSrvCreateCashOrderRqMessage().getAdditionalInfo().setComment(cashOrder.getComment());
    request.getSrvCreateCashOrderRqMessage().getAdditionalInfo()
        .setCashSymbols(new AdditionalInfo.CashSymbols());
    for (CashSymbol cashSymbol : cashOrder.getCashSymbols()) {
      request.getSrvCreateCashOrderRqMessage().getAdditionalInfo().getCashSymbols()
          .getCashSymbolItem().add(convert(cashSymbol));
    }
    request.getSrvCreateCashOrderRqMessage().getAdditionalInfo()
        .setSubbranchCode(cashOrder.getOperator().getSubbranch().getSubbranchCode());
    request.getSrvCreateCashOrderRqMessage().getAdditionalInfo()
        .setVSPCode(cashOrder.getOperator().getSubbranch().getVspCode());
    request.getSrvCreateCashOrderRqMessage().getAdditionalInfo()
        .setOSBCode(cashOrder.getOperator().getSubbranch().getOsbCode());
    request.getSrvCreateCashOrderRqMessage().getAdditionalInfo()
        .setGOSBCode(cashOrder.getOperator().getSubbranch().getGosbCode());
    request.getSrvCreateCashOrderRqMessage().getAdditionalInfo()
        .setTBCode(cashOrder.getOperator().getSubbranch().getTbCode());
    request.getSrvCreateCashOrderRqMessage().getAdditionalInfo()
        .setAccount20202Num(cashOrder.getAccount20202Num());
    request.getSrvCreateCashOrderRqMessage().getAdditionalInfo()
        .setUserLogin(cashOrder.getUserLogin());
    return request;
  }

  /**
   * Возвращает объект запроса получения кассовых ордеров.
   */
  public static SrvGetCashOrderRq toRequestGet(CashOrderRequest cashOrderRequest) {
    SrvGetCashOrderRq request = new SrvGetCashOrderRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvGetCashOrderRqMessage(new SrvGetCashOrderRqMessage());
    request.getSrvGetCashOrderRqMessage()
        .setCreatedFrom(xmlCalendar(cashOrderRequest.getCreatedFrom()));
    request.getSrvGetCashOrderRqMessage()
        .setCreatedTo(xmlCalendar(cashOrderRequest.getCreatedTo()));
    return request;
  }

  /**
   * Возвращает объект запроса обновления статуса кассового ордера.
   */
  public static SrvUpdStCashOrderRq toRequestUpdate(CashOrder cashOrder) {
    SrvUpdStCashOrderRq request = new SrvUpdStCashOrderRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvUpdCashOrderRqMessage(new SrvUpdCashOrderRqMessage());
    request.getSrvUpdCashOrderRqMessage()
        .setCashOrderStatus(convert(cashOrder.getCashOrderStatus()));
    request.getSrvUpdCashOrderRqMessage().setCashOrderId(cashOrder.getCashOrderId());
    return request;
  }

  /**
   * Возвращает объект запроса обновления статуса кассового ордера.
   */
  public static SrvCheckOverLimitRq toRequestCheckOverLimit(CashOrder cashOrder) {
    SrvCheckOverLimitRq request = new SrvCheckOverLimitRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvCheckOverLimitRqMessage(new SrvCheckOverLimitRqMessage());
    request.getSrvCheckOverLimitRqMessage().setAmount(cashOrder.getAmount());
    request.getSrvCheckOverLimitRqMessage().setUserLogin(cashOrder.getUserLogin());
    request.getSrvCheckOverLimitRqMessage().setTobeIncreased(true);
    return request;
  }
}
