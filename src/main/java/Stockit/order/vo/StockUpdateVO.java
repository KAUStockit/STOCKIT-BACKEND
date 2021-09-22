package Stockit.order.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StockUpdateVO {
    private int price;
    private boolean active;
}
