package kr.co.springbootconcurrencyissues.stock.application.facade;

import kr.co.springbootconcurrencyissues.stock.application.StockService;
import kr.co.springbootconcurrencyissues.stock.presentation.request.DecreaseReq;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedissonLockStockFacade {

    private final RedissonClient redissonClient;
    private final StockService stockService;

    public long decrease(DecreaseReq decreaseReq) {
        // 락 객체를 가져온다.
        RLock lock = redissonClient.getLock(decreaseReq.id().toString());
        long quantity = 0;

        try {
            // 몇 초 동안 락 획득을 시도할 것인지, 그리고 몇 초 동안 락을 점유할 것인지 설정한다.
            boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);

            if (!available) {
                throw new RuntimeException("락 획득 실패");
            }

            quantity = stockService.decrease(decreaseReq);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }

        return quantity;
    }
}
