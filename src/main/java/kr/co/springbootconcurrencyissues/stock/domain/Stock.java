package kr.co.springbootconcurrencyissues.stock.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private long quantity;

    @Version
    private long version;

    private Stock(long quantity) {
        this.quantity = quantity;
    }

    public static Stock create(long quantity) {
        return new Stock(quantity);
    }

    public void decrease(long quantity) {
        if (this.quantity - quantity < 0) {
            throw new IllegalArgumentException("쿠키는 0개 미만이 될 수 없습니다.");
        }

        this.quantity -= quantity;
    }
}
