package Stockit.interfaces.order.dto;

import Stockit.domain.order.Order;
import Stockit.interfaces.stock.dto.StockInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class OrderInfo {
    private Long id;
    private int stockOrderPrice; //주문 가격
    private int stockOrderCount; //주문 수량
    private int completedCount; //체결 수량
    private double completedPrice; //체결 가격 평균
    private LocalDateTime stockOrderedDate; //주문 시각
    private String status; //미체결중인 것이 있는지 여부
    private String type; //Buy, Sell
    private StockInfo stockInfo;

    public OrderInfo(Order order) {
        this(order.getId(), order.getStockOrderPrice(), order.getStockOrderCount(), order.getCompletedCount(),
                order.getCompletedPrice(), order.getStockOrderedDate(), order.getStatus(), order.getType(),
                new StockInfo(order.getStock())
        );
    }
}
