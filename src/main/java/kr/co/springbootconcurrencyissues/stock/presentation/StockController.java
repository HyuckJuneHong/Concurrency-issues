package kr.co.springbootconcurrencyissues.stock.presentation;

import kr.co.springbootconcurrencyissues.stock.application.PessimisticLockStockService;
import kr.co.springbootconcurrencyissues.stock.application.StockService;
import kr.co.springbootconcurrencyissues.stock.application.facade.LettuceLockStockFacade;
import kr.co.springbootconcurrencyissues.stock.application.facade.NamedLockStockFacade;
import kr.co.springbootconcurrencyissues.stock.application.facade.OptimisticLockStockFacade;
import kr.co.springbootconcurrencyissues.stock.application.facade.RedissonLockStockFacade;
import kr.co.springbootconcurrencyissues.stock.presentation.request.DecreaseReq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stocks")
public class StockController {

    private final StockService stockService;
    private final NamedLockStockFacade namedLockStockFacade;
    private final LettuceLockStockFacade lettuceLockStockFacade;
    private final RedissonLockStockFacade redissonLockStockFacade;
    private final OptimisticLockStockFacade optimisticLockStockFacade;
    private final PessimisticLockStockService pessimisticLockStockService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public long decrease(@RequestBody DecreaseReq decreaseReq) throws InterruptedException {
        return stockService.decrease(decreaseReq);
    }

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

    @PostMapping("/named")
    @ResponseStatus(HttpStatus.OK)
    public long decreaseWithNamedLock(@RequestBody DecreaseReq decreaseReq) {
        return namedLockStockFacade.decrease(decreaseReq);
    }

    @PostMapping("/lettuce")
    @ResponseStatus(HttpStatus.OK)
    public long decreaseWithLettuce(@RequestBody DecreaseReq decreaseReq) throws InterruptedException {
        return lettuceLockStockFacade.decrease(decreaseReq);
    }

    @PostMapping("/redisson")
    @ResponseStatus(HttpStatus.OK)
    public long decreaseWithRedissonLock(@RequestBody DecreaseReq decreaseReq) {
        return redissonLockStockFacade.decrease(decreaseReq);
    }
}
