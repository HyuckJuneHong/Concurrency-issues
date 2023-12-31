package kr.co.springbootconcurrencyissues.stock.application.facade;

import kr.co.springbootconcurrencyissues.stock.domain.Stock;
import kr.co.springbootconcurrencyissues.stock.domain.StockRepository;
import kr.co.springbootconcurrencyissues.stock.presentation.request.DecreaseReq;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LettuceLockStockFacadeTest {

    @Autowired
    private LettuceLockStockFacade stockFacade;

    @Autowired
    private StockRepository stockRepository;

    private DecreaseReq decreaseReq;

    @BeforeEach
    public void before() {
        stockRepository.save(Stock.create(100));
        decreaseReq = new DecreaseReq(1L, 1);
    }

    @AfterEach
    public void after() {
        stockRepository.deleteAll();
    }

    @DisplayName("decrease - 100개의 요청에 대한 재고 감소가 성공적으로 이뤄지는 지 검증")
    @Test
    void decrease_100_request_quantity() throws InterruptedException {
        // Given
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        // When
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    stockFacade.decrease(decreaseReq);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();
        Stock actual = stockRepository.findById(1L)
                .orElseThrow(IllegalArgumentException::new);

        // Then
        assertThat(actual.getQuantity()).isZero();
    }
}
