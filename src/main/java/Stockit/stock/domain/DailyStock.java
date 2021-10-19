package Stockit.stock.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
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
