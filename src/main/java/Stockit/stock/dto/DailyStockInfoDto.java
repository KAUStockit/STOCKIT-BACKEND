package Stockit.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DailyStockInfoDto {

    private Long stockCode;
    private String stockName;
    private int price;
    private String description;
    private String category;
    private boolean isActive;
    private double percentage;
}
