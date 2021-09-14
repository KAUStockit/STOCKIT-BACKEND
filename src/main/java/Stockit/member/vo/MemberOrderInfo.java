package Stockit.member.vo;

import Stockit.order.domain.Order;
import Stockit.order.domain.Stock;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MemberOrderInfo {

    private final Long orderIdx;
    private final int stockOrderPrice; //주문 가격
    private final int stockOrderCount; //주문 수량
    private final int completedCount; //체결 수량
    private final double completedPrice; //체결 가격 평균
    private final LocalDateTime stockOrderedDate; //주문 시각
    private final String status; //미체결중인 것이 있는지 여부
    private final String type; //Buy, Sell
    private final Stock stock;

    public MemberOrderInfo(Order order) {
        this(order.getOrderIdx(), order.getStockOrderPrice(), order.getStockOrderCount(), order.getCompletedCount(),
        order.getCompletedPrice(), order.getStockOrderedDate(), order.getStatus(), order.getType(), order.getStock());
    }
}
