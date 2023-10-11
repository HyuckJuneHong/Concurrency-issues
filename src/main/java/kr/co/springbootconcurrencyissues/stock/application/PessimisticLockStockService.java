package kr.co.springbootconcurrencyissues.stock.application;

import kr.co.springbootconcurrencyissues.stock.domain.Stock;
import kr.co.springbootconcurrencyissues.stock.domain.StockRepository;
import kr.co.springbootconcurrencyissues.stock.presentation.request.DecreaseReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PessimisticLockStockService {

    private final StockRepository stockRepository;

    @Transactional
    public long decrease(DecreaseReq decreaseReq) {
        Stock stock = stockRepository.findByIdWithPessimisticLock(decreaseReq.id())
                .orElseThrow(() -> new IllegalArgumentException("not found stock by id"));
        stock.decrease(decreaseReq.quantity());

        return stock.getQuantity();
    }
}
