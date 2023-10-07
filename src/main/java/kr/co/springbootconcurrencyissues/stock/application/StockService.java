package kr.co.springbootconcurrencyissues.stock.application;

import kr.co.springbootconcurrencyissues.stock.domain.Stock;
import kr.co.springbootconcurrencyissues.stock.domain.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    public synchronized void decrease(Long id, long quantity) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found stock by id"));
        stock.decrease(quantity);
        stockRepository.saveAndFlush(stock);
    }
}
