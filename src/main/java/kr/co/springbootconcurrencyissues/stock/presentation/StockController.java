package kr.co.springbootconcurrencyissues.stock.presentation;

import kr.co.springbootconcurrencyissues.stock.application.PessimisticLockStockService;
import kr.co.springbootconcurrencyissues.stock.presentation.request.DecreaseReq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stocks")
public class StockController {

    private final PessimisticLockStockService pessimisticLockStockService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public long decreaseWithPessimisticLock(@RequestBody DecreaseReq decreaseReq) {
        return pessimisticLockStockService.decrease(decreaseReq);
    }
}
