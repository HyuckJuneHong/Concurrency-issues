package kr.co.springbootconcurrencyissues.stock.application;

import kr.co.springbootconcurrencyissues.stock.domain.Stock;
import kr.co.springbootconcurrencyissues.stock.domain.StockRepository;
import kr.co.springbootconcurrencyissues.stock.presentation.request.DecreaseReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public long decrease(DecreaseReq decreaseReq) {
        Stock stock = stockRepository.findById(decreaseReq.id())
                .orElseThrow(() -> new IllegalArgumentException("not found stock by id"));
        stock.decrease(decreaseReq.quantity());

        return stock.getQuantity();
    }
}
