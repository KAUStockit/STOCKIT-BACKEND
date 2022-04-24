package Stockit.interfaces.order;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OrderRequest {
    private int stockOrderPrice;
    private int stockOrderCount;
    private String orderType;
}
