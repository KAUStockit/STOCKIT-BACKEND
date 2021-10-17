package Stockit.stock.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class DailyStockInfo {
    @EmbeddedId
    private DailyStockInfoId id;

    @MapsId("stockCode")
    @ManyToOne
    @JoinColumn(name = "stock_code")
    public Stock stock;

    @Column(unique = true)
    private String stockName;

    @Setter
    private int price;


    public DailyStockInfo(DailyStockInfoId id, Stock stock, String stockName, int price) {
        this.id = id;
        this.stock = stock;
        this.stockName = stockName;
        this.price = price;
    }
    public DailyStockInfo(Stock stock) {
        this(new DailyStockInfoId(stock.getStockCode()), stock, stock.getStockName(), stock.getPrice());
    }
}
