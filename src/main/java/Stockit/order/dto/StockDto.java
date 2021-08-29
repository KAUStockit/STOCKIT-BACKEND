package Stockit.order.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class StockDto {
    private String stockName;
    private int price;
    private String description;
    private String category;
}
