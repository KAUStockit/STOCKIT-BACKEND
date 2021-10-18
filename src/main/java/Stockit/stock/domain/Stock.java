package Stockit.stock.domain;

import Stockit.stock.dto.StockDto;
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
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STOCK_CODE")
    private Long id;

    @Column(unique = true)
    private String stockName;

    private int price;

    private boolean isActive; //거래 or 거래중지

    private String description; //주식 설명

    private String category; //주식 분류

    @CreatedDate
    private LocalDateTime stockCreatedDate;

    ///////////////////////////////////////
    public Stock(String stockName, int price, String description, String category) {
        this.stockName = stockName;
        this.price = price;
        this.description = description;
        this.category = category;
        this.isActive = true;
    }

    public Stock(StockDto stockDto) {
        this(stockDto.getStockName(), stockDto.getPrice(), stockDto.getDescription(), stockDto.getCategory());
    }

    public void updateStock(int price, boolean isActive) {
        this.price = price;
        this.isActive = isActive;
    }
}
