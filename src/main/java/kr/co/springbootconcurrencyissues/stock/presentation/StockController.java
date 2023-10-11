package kr.co.springbootconcurrencyissues.stock.presentation;

import kr.co.springbootconcurrencyissues.stock.application.PessimisticLockStockService;
import kr.co.springbootconcurrencyissues.stock.application.facade.OptimisticLockStockFacade;
import kr.co.springbootconcurrencyissues.stock.presentation.request.DecreaseReq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stocks")
public class StockController {

    private final OptimisticLockStockFacade optimisticLockStockFacade;
    private final PessimisticLockStockService pessimisticLockStockService;

    @PostMapping("/pessimistic")
    @ResponseStatus(HttpStatus.OK)
    public long decreaseWithPessimisticLock(@RequestBody DecreaseReq decreaseReq) {
        return pessimisticLockStockService.decrease(decreaseReq);
    }

    @PostMapping("/optimistic")
    @ResponseStatus(HttpStatus.OK)
    public long decreaseWithOptimisticLock(@RequestBody DecreaseReq decreaseReq) throws InterruptedException {
        return optimisticLockStockFacade.decrease(decreaseReq);
    }
}
