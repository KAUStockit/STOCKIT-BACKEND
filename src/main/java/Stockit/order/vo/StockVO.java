package Stockit.order.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StockVO {
    private long stockCode;
    private String stockName;
    private int price;
    private String description;
    private String category;
    private boolean isActive;
}
