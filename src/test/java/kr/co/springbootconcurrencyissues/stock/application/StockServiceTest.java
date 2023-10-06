package kr.co.springbootconcurrencyissues.stock.application;

import kr.co.springbootconcurrencyissues.stock.domain.Stock;
import kr.co.springbootconcurrencyissues.stock.domain.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StockServiceTest {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    @BeforeEach
    public void before() {
        stockRepository.save(Stock.create(1, 100));
    }

    @AfterEach
    public void after() {
        stockRepository.deleteAll();
    }

    @DisplayName("decrease - 1개의 요청에 대한 재고 감소가 성공적으로 이뤄지는 지 검증")
    @Test
    void decrease_1_request_quantity() {
        // Given
        stockService.decrease(1L, 1);

        // When
        Stock actual = stockRepository.findById(1L)
                .orElseThrow(IllegalArgumentException::new);

        // Then
        assertThat(actual.getQuantity()).isEqualTo(99);
    }
}
