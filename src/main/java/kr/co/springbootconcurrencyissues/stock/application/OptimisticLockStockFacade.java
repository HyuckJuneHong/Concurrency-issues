package kr.co.springbootconcurrencyissues.stock.application;

import kr.co.springbootconcurrencyissues.stock.domain.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class OptimisticLockStockFacade {

    private final OptimisticLockStockService stockService;

    public void decrease(Long id, long quantity) throws InterruptedException {
        while (true) {
            try {
                stockService.decrease(id, quantity);
                break;
            } catch (Exception e) {
                Thread.sleep(50);
            }
        }
    }
}
