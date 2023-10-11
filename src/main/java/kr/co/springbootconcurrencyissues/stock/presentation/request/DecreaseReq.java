package kr.co.springbootconcurrencyissues.stock.presentation.request;

public record DecreaseReq(
        Long id,
        long quantity
) {
}
