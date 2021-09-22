package Stockit.order.vo;

import Stockit.order.domain.Stock;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StockVO {
    private final long stockCode;
    private final String stockName;
    private final int price;
    private final String description;
    private final String category;
    private final boolean isActive;

    public StockVO(Stock stock) {
        this.stockCode = stock.getStockCode();
        this.stockName = stock.getStockName();
        this.price = stock.getPrice();
        this.description = stock.getDescription();
        this.category = stock.getCategory();
        this.isActive = stock.isActive();
    }
}
