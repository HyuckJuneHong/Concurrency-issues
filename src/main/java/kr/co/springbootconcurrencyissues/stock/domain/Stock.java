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
    private long productId;

    @Column
    private long quantity;

    private Stock(long productId, long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public static Stock create(long productId, long quantity) {
        return new Stock(productId, quantity);
    }
}
