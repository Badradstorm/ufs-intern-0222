package ru.philit.ufs.web.view;

import java.util.List;
import ru.philit.ufs.model.entity.order.CashOrder;
import ru.philit.ufs.web.dto.BaseResponse;
import ru.philit.ufs.web.dto.CashOrderBookDto;
import ru.philit.ufs.web.dto.OperationJournalDto;

/**
 * Ответ для операции {@link ru.philit.ufs.web.controller.ReportController#getCashOrderBook}
 */
@SuppressWarnings("serial")
public class GetCashOrderBookResp extends BaseResponse<List<CashOrderBookDto>> {

}
