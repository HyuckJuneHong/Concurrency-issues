package kr.co.springbootconcurrencyissues.stock.application.facade;

import kr.co.springbootconcurrencyissues.stock.application.OptimisticLockStockService;
import kr.co.springbootconcurrencyissues.stock.presentation.request.DecreaseReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OptimisticLockStockFacade {

    private final OptimisticLockStockService stockService;

    public long decrease(DecreaseReq decreaseReq) throws InterruptedException {
        while (true) {
            try {
                return stockService.decrease(decreaseReq);
            } catch (Exception e) {
                Thread.sleep(50);
            }
        }
    }
}
