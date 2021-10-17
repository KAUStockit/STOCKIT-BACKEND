package Stockit.stock.dto;

import Stockit.stock.domain.DailyStockInfoId;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class DailyStockInfoDto {
    private DailyStockInfoId id;
    private String stockName;
    private int price;

    public DailyStockInfoDto(StockDto stock) {
        this.id = new DailyStockInfoId(stock.getStockCode());
        this.stockName = stock.getStockName();
        this.price = stock.getPrice();
    }
}
