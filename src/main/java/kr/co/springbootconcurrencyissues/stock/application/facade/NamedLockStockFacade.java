package kr.co.springbootconcurrencyissues.stock.application.facade;

import kr.co.springbootconcurrencyissues.stock.application.StockService;
import kr.co.springbootconcurrencyissues.stock.domain.LockRepository;
import kr.co.springbootconcurrencyissues.stock.presentation.request.DecreaseReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class NamedLockStockFacade {

    private final LockRepository lockRepository;
    private final StockService stockService;

    @Transactional
    public long decrease(DecreaseReq decreaseReq) {
        try {
            lockRepository.getLock(decreaseReq.id().toString());

            return stockService.decrease(decreaseReq);
        } finally {
            lockRepository.releaseLock(decreaseReq.id().toString());
        }
    }
}
