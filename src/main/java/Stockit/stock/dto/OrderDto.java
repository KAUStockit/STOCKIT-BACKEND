package Stockit.stock.dto;

import Stockit.member.domain.Member;
import Stockit.stock.domain.Stock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class OrderDto {

    private Long orderId;
    private Member member;
    private int stockOrderPrice;            //주문 가격
    private int stockOrderCount;            //주문 수량
    private LocalDateTime orderDate;    //주문시간
    private String stockCode;
}
