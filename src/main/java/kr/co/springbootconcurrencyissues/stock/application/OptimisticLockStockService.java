package kr.co.springbootconcurrencyissues.stock.application;

import kr.co.springbootconcurrencyissues.stock.domain.Stock;
import kr.co.springbootconcurrencyissues.stock.domain.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OptimisticLockStockService {

    private final StockRepository stockRepository;

    @Transactional
    public void decrease(Long id, long quantity) {
        Stock stock = stockRepository.findByIdWithOptimisticLock(id)
                .orElseThrow(() -> new IllegalArgumentException("not found stock by id"));
        stock.decrease(quantity);
    }
}
