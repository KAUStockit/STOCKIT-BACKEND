package Stockit.interfaces.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DailyStockInfo {

    private Long stockCode;
    private String stockName;
    private int price;
    private String description;
    private String category;
    private boolean isActive;
    private double percentage;
}
