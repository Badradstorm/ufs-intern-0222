package ru.philit.ufs.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.philit.ufs.model.entity.account.Representative;
import ru.philit.ufs.model.entity.oper.Operation;
import ru.philit.ufs.model.entity.oper.OperationPackage;
import ru.philit.ufs.model.entity.oper.OperationTask;
import ru.philit.ufs.model.entity.oper.OperationTaskCardDeposit;
import ru.philit.ufs.model.entity.order.CashOrder;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.model.entity.user.Operator;
import ru.philit.ufs.model.entity.user.User;
import ru.philit.ufs.web.dto.CashOrderBookDto;
import ru.philit.ufs.web.dto.OperationJournalDto;
import ru.philit.ufs.web.mapping.CashOrderBookMapper;
import ru.philit.ufs.web.mapping.OperationJournalMapper;
import ru.philit.ufs.web.provider.CashOrderProvider;
import ru.philit.ufs.web.provider.ReportProvider;
import ru.philit.ufs.web.provider.RepresentativeProvider;
import ru.philit.ufs.web.view.GetCashOrderBookReq;
import ru.philit.ufs.web.view.GetCashOrderBookResp;
import ru.philit.ufs.web.view.GetOperationJournalReq;
import ru.philit.ufs.web.view.GetOperationJournalResp;

/**
 * Контроллер запроса данных отчётов.
 */
@RestController
@RequestMapping("/report")
public class ReportController {

  private final ReportProvider reportProvider;
  private final RepresentativeProvider representativeProvider;
  private final CashOrderProvider cashOrderProvider;
  private final OperationJournalMapper operationJournalMapper;
  private final CashOrderBookMapper cashOrderBookMapper;

  /**
   * Конструктор бина.
   */
  @Autowired
  public ReportController(
      ReportProvider reportProvider,
      RepresentativeProvider representativeProvider,
      CashOrderProvider cashOrderProvider,
      OperationJournalMapper operationJournalMapper,
      CashOrderBookMapper cashOrderBookMapper
  ) {
    this.reportProvider = reportProvider;
    this.representativeProvider = representativeProvider;
    this.cashOrderProvider = cashOrderProvider;
    this.operationJournalMapper = operationJournalMapper;
    this.cashOrderBookMapper = cashOrderBookMapper;
  }

  /**
   * Получение списка записей журнала операций.
   *
   * @param request    параметры запроса списка
   * @param clientInfo информация о клиенте
   * @return список записей
   */
  @RequestMapping(value = "/operationJournal", method = RequestMethod.POST)
  public GetOperationJournalResp getOperationJournal(
      @RequestBody GetOperationJournalReq request, ClientInfo clientInfo
  ) {
    List<OperationJournalDto> items = new ArrayList<>();

    for (OperationPackage opPackage : reportProvider.getOperationPackages(
        operationJournalMapper.asEntity(request.getFromDate()),
        operationJournalMapper.asEntity(request.getToDate()),
        clientInfo
    )) {
      for (OperationTask task : opPackage.getToCardDeposits()) {
        OperationTaskCardDeposit taskCardDeposit = (OperationTaskCardDeposit) task;

        Operation operation = reportProvider.getOperation(task);
        if (operation == null) {
          continue; // задачи без операции не попадают в журнал
        }

        Operator operator = reportProvider.getOperator(opPackage.getUserLogin(), clientInfo);
        User taskUser = reportProvider.getUser(opPackage.getUserLogin());
        BigDecimal commissionAmount = reportProvider.getCommission(
            taskCardDeposit.getAccountId(), taskCardDeposit.getAmount(), operation, clientInfo
        );
        Representative representative = representativeProvider.getRepresentativeById(
            taskCardDeposit.getRepresentativeId(), clientInfo
        );

        items.add(new OperationJournalDto()
            .withOperator(operationJournalMapper.asDto(operator))
            .withUser(operationJournalMapper.asDto(taskUser))
            .withRepresentative(operationJournalMapper.asDto(representative))
            .withOperation(operationJournalMapper.asDto(operation))
            .withDeposit(operationJournalMapper.asDto(taskCardDeposit))
            .withCommission(operationJournalMapper.asDto(commissionAmount))
        );
      }
    }

    return new GetOperationJournalResp().withSuccess(items);
  }

  /**
   * Получение кассовой книги.
   *
   * @param request    параметры запроса списка
   * @param clientInfo информация о клиенте
   * @return кассовая книга
   */
  @RequestMapping(value = "/cashOrderBook", method = RequestMethod.POST)
  public GetCashOrderBookResp getCashOrderBook(
      @RequestBody GetCashOrderBookReq request, ClientInfo clientInfo
  ) {
    List<CashOrderBookDto> items = new ArrayList<>();

    for (CashOrder cashOrder : cashOrderProvider.getCashOrders(
        cashOrderBookMapper.asEntity(request),
        clientInfo
    )) {
      items.add(cashOrderBookMapper.asDto(cashOrder));
    }

    return new GetCashOrderBookResp().withSuccess(items);
  }
}
