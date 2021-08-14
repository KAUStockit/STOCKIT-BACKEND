package Stockit.order.domain;

import Stockit.order.dto.StockDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Stock {
    @Id @GeneratedValue
    @Column(name = "stock_code")
    private Long stockCode;

    @Column(unique = true)
    private String stockName;

    private int price;

    private boolean is_active; //거래 or 거래중지

    @CreatedDate
    private LocalDateTime stockCreatedDate;

    ///////////////////////////////////////
    public Stock(String stockName, int price) {
        this.stockName = stockName;
        this.price = price;

        this.is_active = true;
    }
    public Stock(StockDto stockDto) {
        this(stockDto.getStockName(), stockDto.getPrice());
    }
}
