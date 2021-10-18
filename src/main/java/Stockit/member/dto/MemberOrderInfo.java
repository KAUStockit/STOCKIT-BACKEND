package Stockit.member.dto;

import Stockit.order.dto.OrderInfo;
import Stockit.stock.dto.StockInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MemberOrderInfo {

    private final Long orderId;
    private final int stockOrderPrice; //주문 가격
    private final int stockOrderCount; //주문 수량
    private final int completedCount; //체결 수량
    private final double completedPrice; //체결 가격 평균
    private final LocalDateTime stockOrderedDate; //주문 시각
    private final String status; //미체결중인 것이 있는지 여부
    private final String type; //Buy, Sell
    private final StockInfo stock;

    public MemberOrderInfo(OrderInfo orderInfo) {
        this(orderInfo.getId(), orderInfo.getStockOrderPrice(), orderInfo.getStockOrderCount(),
                orderInfo.getCompletedCount(), orderInfo.getCompletedPrice(), orderInfo.getStockOrderedDate(),
                orderInfo.getStatus(), orderInfo.getType(), orderInfo.getStockInfo());
    }
}
