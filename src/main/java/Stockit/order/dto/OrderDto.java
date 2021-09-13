package Stockit.order.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OrderDto {
    private int stockOrderPrice;
    private int stockOrderCount;
    private String orderType;
}
