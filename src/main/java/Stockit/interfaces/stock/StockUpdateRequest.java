package Stockit.interfaces.stock;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StockUpdateRequest {
    private int price;
    private boolean active;
}
