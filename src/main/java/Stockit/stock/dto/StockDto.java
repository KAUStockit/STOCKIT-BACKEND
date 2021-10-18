package Stockit.stock.dto;

import Stockit.stock.domain.Stock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StockDto {
    private Long stockCode;
    private String stockName;
    private int price;
    private String description;
    private String category;
    private boolean isActive;

    public StockDto(Stock stock) {
        this.stockCode = stock.getId();
        this.stockName = stock.getStockName();
        this.price = stock.getPrice();
        this.description = stock.getDescription();
        this.category = stock.getCategory();
        this.isActive = stock.isActive();
    }
}
