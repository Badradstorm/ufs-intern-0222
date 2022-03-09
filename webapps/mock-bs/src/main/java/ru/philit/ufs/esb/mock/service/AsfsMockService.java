package ru.philit.ufs.esb.mock.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.philit.ufs.esb.MessageProcessor;
import ru.philit.ufs.esb.mock.client.EsbClient;
import ru.philit.ufs.model.cache.MockCache;
import ru.philit.ufs.model.converter.esb.JaxbConverter;
import ru.philit.ufs.model.converter.esb.asfs.CashOrderAdapter;
import ru.philit.ufs.model.entity.common.ExternalEntityList;
import ru.philit.ufs.model.entity.esb.asfs.HeaderInfoType;
import ru.philit.ufs.model.entity.esb.asfs.LimitStatusType;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRs.SrvCheckOverLimitRsMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetCashOrderRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetCashOrderRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRs.SrvGetWorkPlaceInfoRsMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRs.SrvGetWorkPlaceInfoRsMessage.WorkPlaceOperationTypeLimit;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRs.SrvGetWorkPlaceInfoRsMessage.WorkPlaceOperationTypeLimit.OperationTypeLimitItem;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRs;
import ru.philit.ufs.model.entity.order.CashOrder;

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
    CashOrder cashOrder = CashOrderAdapter.convert(request);
    cashOrder.setCreatedDttm(cashOrder.getReceiveDate());
    mockCache.saveCashOrder(cashOrder);
    return CashOrderAdapter.toResponseCreate(cashOrder);
  }

  private SrvUpdStCashOrderRs getResponse(SrvUpdStCashOrderRq request) {
    CashOrder updatedCashOrder = mockCache.updateCashOrder(
        request.getSrvUpdCashOrderRqMessage().getCashOrderId(),
        request.getSrvUpdCashOrderRqMessage().getCashOrderStatus());
    return CashOrderAdapter.toResponseUpdate(updatedCashOrder);
  }

  private SrvGetCashOrderRs getResponse(SrvGetCashOrderRq request) {
    ExternalEntityList<CashOrder> cashOrders = new ExternalEntityList<>();
    cashOrders.setItems(mockCache.getCashOrders(
        date(request.getSrvGetCashOrderRqMessage().getCreatedFrom()),
        date(request.getSrvGetCashOrderRqMessage().getCreatedTo())));
    return CashOrderAdapter.toResponseGet(cashOrders);
  }

  private SrvCheckOverLimitRs getResponse(SrvCheckOverLimitRq request) {
    SrvCheckOverLimitRs response = new SrvCheckOverLimitRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvCheckOverLimitRsMessage(new SrvCheckOverLimitRsMessage());

    Boolean limitStatus = mockCache.checkOverLimit(
        request.getSrvCheckOverLimitRqMessage().getUserLogin(),
        request.getSrvCheckOverLimitRqMessage().getAmount(),
        date(request.getHeaderInfo().getRqTm()));
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
