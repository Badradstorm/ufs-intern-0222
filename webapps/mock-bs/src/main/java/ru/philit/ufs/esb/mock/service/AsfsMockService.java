package ru.philit.ufs.esb.mock.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.philit.ufs.esb.MessageProcessor;
import ru.philit.ufs.esb.mock.client.EsbClient;
import ru.philit.ufs.model.cache.MockCache;
import ru.philit.ufs.model.cache.hazelcast.HazelcastMockCacheImpl;
import ru.philit.ufs.model.converter.esb.JaxbConverter;
import ru.philit.ufs.model.entity.esb.asfs.CashOrderStatusType;
import ru.philit.ufs.model.entity.esb.asfs.CashOrderType;
import ru.philit.ufs.model.entity.esb.asfs.HeaderInfoType;
import ru.philit.ufs.model.entity.esb.asfs.LimitStatusType;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRs.SrvCheckOverLimitRsMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage.AdditionalInfo;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage.AdditionalInfo.CashSymbols.CashSymbolItem;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRq.SrvCreateCashOrderRqMessage.RepData;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage.CashSymbols;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetCashOrderRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetCashOrderRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetCashOrderRs.SrvGetCashOrderRsMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetCashOrderRs.SrvGetCashOrderRsMessage.CashOrderItem;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRs.SrvGetWorkPlaceInfoRsMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRs.SrvGetWorkPlaceInfoRsMessage.WorkPlaceOperationTypeLimit;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRs.SrvGetWorkPlaceInfoRsMessage.WorkPlaceOperationTypeLimit.OperationTypeLimitItem;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRs.SrvUpdCashOrderRsMessage;

@Service
public class AsfsMockService extends CommonMockService implements MessageProcessor {

  private static final String CONTEXT_PATH = "ru.philit.ufs.model.entity.esb.asfs";

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final EsbClient esbClient;
  private final MockCache mockCache;

  private final JaxbConverter jaxbConverter = new JaxbConverter(CONTEXT_PATH);

  @Autowired
  public AsfsMockService(EsbClient esbClient, MockCache mockCache) {
    this.esbClient = esbClient;
    this.mockCache = mockCache;
  }

  @PostConstruct
  public void init() {
    esbClient.addMessageProcessor(this);
    logger.info("{} started", this.getClass().getSimpleName());
  }

  @Override
  public boolean processMessage(String requestMessage) {
    try {
      Object request = jaxbConverter.getObject(requestMessage);
      logger.debug("Received message: {}", request);
      if (request != null) {
        if (request instanceof SrvCreateCashOrderRq) {
          sendResponse(getResponse((SrvCreateCashOrderRq) request));

        } else if (request instanceof SrvUpdStCashOrderRq) {
          sendResponse(getResponse((SrvUpdStCashOrderRq) request));

        } else if (request instanceof SrvGetCashOrderRq) {
          sendResponse(getResponse((SrvGetCashOrderRq) request));

        } else if (request instanceof SrvCheckOverLimitRq) {
          sendResponse(getResponse((SrvCheckOverLimitRq) request));

        } else if (request instanceof SrvGetWorkPlaceInfoRq) {
          sendResponse(getResponse((SrvGetWorkPlaceInfoRq) request));

        }
        return true;
      }
    } catch (JAXBException e) {
      // this message can not be processed this processor
      logger.debug("this message can not be processed this processor", e);
    }
    return false;
  }

  private void sendResponse(Object responseObject) throws JAXBException {
    String responseMessage = jaxbConverter.getXml(responseObject);
    esbClient.sendMessage(responseMessage);
  }

  private SrvCreateCashOrderRs getResponse(SrvCreateCashOrderRq request) {
    SrvCreateCashOrderRs response = new SrvCreateCashOrderRs();
    SrvCreateCashOrderRqMessage message = request.getSrvCreateCashOrderRqMessage();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvCreateCashOrderRsMessage(new SrvCreateCashOrderRsMessage());
    response.getSrvCreateCashOrderRsMessage().setCashOrderId(message.getCashOrderId());
    response.getSrvCreateCashOrderRsMessage().setCreatedDttm(xmlCalendar(new Date()));
    response.getSrvCreateCashOrderRsMessage().setResponseCode("200");
    response.getSrvCreateCashOrderRsMessage().setResponseMsg("ok");
    response.getSrvCreateCashOrderRsMessage().setCashOrderINum(message.getCashOrderINum());
    response.getSrvCreateCashOrderRsMessage().setCashOrderStatus(CashOrderStatusType.COMMITTED);
    response.getSrvCreateCashOrderRsMessage().setCashOrderType(CashOrderType.KO_1);
    response.getSrvCreateCashOrderRsMessage().setOperationId("111");
    if (message.getRepData() == null) {
      message.setRepData(new RepData());
    }
    response.getSrvCreateCashOrderRsMessage().setRepFIO(message.getRepData().getRepFIO());
    response.getSrvCreateCashOrderRsMessage().setLegalEntityShortName("ООО \"ДНК\"");
    response.getSrvCreateCashOrderRsMessage().setINN(message.getRepData().getINN());
    response.getSrvCreateCashOrderRsMessage().setAmount(message.getAmount());
    response.getSrvCreateCashOrderRsMessage().setAccountId(message.getAccountId());
    response.getSrvCreateCashOrderRsMessage().setCashSymbols(new CashSymbols());
    if (message.getAdditionalInfo() == null) {
      message.setAdditionalInfo(new AdditionalInfo());
    }
    if (message.getAdditionalInfo().getCashSymbols() == null) {
      message.getAdditionalInfo().setCashSymbols(new AdditionalInfo.CashSymbols());
    }
    for (CashSymbolItem item :
        request.getSrvCreateCashOrderRqMessage().getAdditionalInfo().getCashSymbols()
            .getCashSymbolItem()) {
      CashSymbols.CashSymbolItem responseItem = new CashSymbols.CashSymbolItem();
      responseItem.setCashSymbolAmount(item.getCashSymbolAmount());
      responseItem.setCashSymbol(item.getCashSymbol());
      response.getSrvCreateCashOrderRsMessage().getCashSymbols().getCashSymbolItem()
          .add(responseItem);
    }
    response.getSrvCreateCashOrderRsMessage().setSenderBank("Сбербанк");
    response.getSrvCreateCashOrderRsMessage().setSenderBankBIC("121121");
    response.getSrvCreateCashOrderRsMessage().setRecipientBank("Сбербанк");
    response.getSrvCreateCashOrderRsMessage().setRecipientBankBIC("121121");
    response.getSrvCreateCashOrderRsMessage().setClientTypeFK(true);
    response.getSrvCreateCashOrderRsMessage().setFDestLEName("name");
    response.getSrvCreateCashOrderRsMessage().setSubbranchCode("1545");
    response.getSrvCreateCashOrderRsMessage().setUserLogin("Ann");
    response.getSrvCreateCashOrderRsMessage().setUserFullName("Ann Petrovna");
    response.getSrvCreateCashOrderRsMessage().setUserPosition("operator");
    mockCache.saveCashOrder(response);
    return response;
  }

  private SrvUpdStCashOrderRs getResponse(SrvUpdStCashOrderRq request) {
    SrvUpdStCashOrderRs response = new SrvUpdStCashOrderRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvUpdCashOrderRsMessage(new SrvUpdCashOrderRsMessage());
    if (mockCache.updateCashOrder(request) != null) {
      response = mockCache.updateCashOrder(request);
    }
    return response;
  }

  private SrvGetCashOrderRs getResponse(SrvGetCashOrderRq request) {
    SrvGetCashOrderRs response = new SrvGetCashOrderRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvGetCashOrderRsMessage(new SrvGetCashOrderRsMessage());
    List<SrvCreateCashOrderRs> cashOrders = mockCache.getCashOrders(
        request.getSrvGetCashOrderRqMessage().getCreatedFrom(),
        request.getSrvGetCashOrderRqMessage().getCreatedTo());

    for (SrvCreateCashOrderRs cashOrder :
        cashOrders) {
      CashOrderItem item = new CashOrderItem();
      response.getSrvGetCashOrderRsMessage().getCashOrderItem().add(item);
      item.setCreatedDttm(cashOrder.getSrvCreateCashOrderRsMessage().getCreatedDttm());
      item.setResponseCode(cashOrder.getSrvCreateCashOrderRsMessage().getResponseCode());
      item.setResponseMsg(cashOrder.getSrvCreateCashOrderRsMessage().getResponseMsg());
      item.setCashOrderId(cashOrder.getSrvCreateCashOrderRsMessage().getCashOrderId());
      item.setCashOrderINum(cashOrder.getSrvCreateCashOrderRsMessage().getCashOrderINum());
      item.setCashOrderStatus(cashOrder.getSrvCreateCashOrderRsMessage().getCashOrderStatus());
      item.setCashOrderType(cashOrder.getSrvCreateCashOrderRsMessage().getCashOrderType());
      item.setOperationId(cashOrder.getSrvCreateCashOrderRsMessage().getOperationId());
      item.setRepFIO(cashOrder.getSrvCreateCashOrderRsMessage().getRepFIO());
      item.setLegalEntityShortName(
          cashOrder.getSrvCreateCashOrderRsMessage().getLegalEntityShortName());
      item.setINN(cashOrder.getSrvCreateCashOrderRsMessage().getINN());
      item.setAmount(cashOrder.getSrvCreateCashOrderRsMessage().getAmount());
      item.setAccountId(cashOrder.getSrvCreateCashOrderRsMessage().getAccountId());
      item.setCashSymbols(
          new SrvGetCashOrderRs.SrvGetCashOrderRsMessage.CashOrderItem.CashSymbols());
      for (CashSymbols.CashSymbolItem cashSymbolItem :
          cashOrder.getSrvCreateCashOrderRsMessage().getCashSymbols().getCashSymbolItem()) {
        CashOrderItem.CashSymbols.CashSymbolItem responseItem =
            new CashOrderItem.CashSymbols.CashSymbolItem();
        responseItem.setCashSymbolAmount(cashSymbolItem.getCashSymbolAmount());
        responseItem.setCashSymbol(cashSymbolItem.getCashSymbol());
        item.getCashSymbols().getCashSymbolItem().add(responseItem);
      }
      item.setSenderBank(cashOrder.getSrvCreateCashOrderRsMessage().getSenderBank());
      item.setSenderBankBIC(cashOrder.getSrvCreateCashOrderRsMessage().getSenderBankBIC());
      item.setRecipientBank(cashOrder.getSrvCreateCashOrderRsMessage().getRecipientBank());
      item.setRecipientBankBIC(cashOrder.getSrvCreateCashOrderRsMessage().getRecipientBankBIC());
      item.setClientTypeFK(cashOrder.getSrvCreateCashOrderRsMessage().isClientTypeFK());
      item.setFDestLEName(cashOrder.getSrvCreateCashOrderRsMessage().getFDestLEName());
      item.setSubbranchCode(cashOrder.getSrvCreateCashOrderRsMessage().getSubbranchCode());
      item.setUserLogin(cashOrder.getSrvCreateCashOrderRsMessage().getUserLogin());
      item.setUserFullName(cashOrder.getSrvCreateCashOrderRsMessage().getUserFullName());
      item.setUserPosition(cashOrder.getSrvCreateCashOrderRsMessage().getUserPosition());
    }
    return response;
  }

  private SrvCheckOverLimitRs getResponse(SrvCheckOverLimitRq request) {
    SrvCheckOverLimitRs response = new SrvCheckOverLimitRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvCheckOverLimitRsMessage(new SrvCheckOverLimitRsMessage());

    Boolean limitStatus = mockCache.checkOverLimit(
        request.getSrvCheckOverLimitRqMessage().getUserLogin(),
        request.getSrvCheckOverLimitRqMessage().getAmount(),
        request.getHeaderInfo().getRqTm());
    response.getSrvCheckOverLimitRsMessage()
        .setStatus(limitStatus ? LimitStatusType.LIMIT_PASSED : LimitStatusType.LIMIT_ERROR);
    response.getSrvCheckOverLimitRsMessage().setResponseCode("200");
    return response;
  }

  private SrvGetWorkPlaceInfoRs getResponse(SrvGetWorkPlaceInfoRq request) {
    SrvGetWorkPlaceInfoRs response = new SrvGetWorkPlaceInfoRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvGetWorkPlaceInfoRsMessage(new SrvGetWorkPlaceInfoRsMessage());
    response.getSrvGetWorkPlaceInfoRsMessage().setWorkPlaceType(BigInteger.valueOf(2));
    response.getSrvGetWorkPlaceInfoRsMessage().setCashboxOnBoard(true);
    response.getSrvGetWorkPlaceInfoRsMessage().setSubbranchCode("111222");
    response.getSrvGetWorkPlaceInfoRsMessage().setCashboxOnBoardDevice("Пром");
    response.getSrvGetWorkPlaceInfoRsMessage().setCashboxDeviceType("касса");
    response.getSrvGetWorkPlaceInfoRsMessage().setCurrentCurrencyType("rub");
    response.getSrvGetWorkPlaceInfoRsMessage().setAmount(BigDecimal.valueOf(50000));
    response.getSrvGetWorkPlaceInfoRsMessage().setWorkPlaceLimit(BigDecimal.valueOf(1000000));
    response.getSrvGetWorkPlaceInfoRsMessage()
        .setWorkPlaceOperationTypeLimit(new WorkPlaceOperationTypeLimit());
    OperationTypeLimitItem item = new OperationTypeLimitItem();
    item.setOperationLimit(BigDecimal.valueOf(10000));
    item.setOperationCategory(BigInteger.valueOf(1));
    response.getSrvGetWorkPlaceInfoRsMessage().getWorkPlaceOperationTypeLimit()
        .getOperationTypeLimitItem().add(item);
    return response;
  }

  private HeaderInfoType copyHeaderInfo(HeaderInfoType headerInfo0) {
    HeaderInfoType headerInfo = new HeaderInfoType();
    headerInfo.setRqUID(headerInfo0.getRqUID());
    headerInfo.setRqTm(headerInfo0.getRqTm());
    headerInfo.setSpName(headerInfo0.getSystemId());
    headerInfo.setSystemId(headerInfo.getSpName());
    return headerInfo;
  }
}
