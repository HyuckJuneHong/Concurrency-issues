package kr.co.springbootconcurrencyissues.stock.application.facade;

import kr.co.springbootconcurrencyissues.stock.application.StockService;
import kr.co.springbootconcurrencyissues.stock.domain.RedisLockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LettuceLockStockFacade {

    private final RedisLockRepository redisLockRepository;
    private final StockService stockService;

    public void decrease(Long id, long quantity) throws InterruptedException {
        while (!redisLockRepository.lock(id)) {
            // 만약 락 획득 실패 시, Thread.sleep을 활용해서 100텀을 두고 재시동 하도록 해야 Redis에 가는 부하를 줄여 줄 수 있다.
            Thread.sleep(100);
        }

        try {
            stockService.decrease(id, quantity);
        } finally {
            redisLockRepository.unlock(id);
        }
    }
}
