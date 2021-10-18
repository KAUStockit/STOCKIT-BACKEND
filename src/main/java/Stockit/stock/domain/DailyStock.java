package Stockit.stock.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class DailyStock {
    @EmbeddedId
    private DailyStockId id;

    @MapsId("stockCode")
    @ManyToOne
    @JoinColumn(name = "STOCK_CODE")
    public Stock stock;

    private String stockName;

    private int price;

    public DailyStock(DailyStockId id, Stock stock, String stockName, int price) {
        this.id = id;
        this.stock = stock;
        this.stockName = stockName;
        this.price = price;
    }
    public DailyStock(Stock stock) {
        this(new DailyStockId(stock.getId()), stock, stock.getStockName(), stock.getPrice());
    }
}
