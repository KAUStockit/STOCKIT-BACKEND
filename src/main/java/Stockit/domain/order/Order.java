package Stockit.domain.order;

import Stockit.domain.member.Member;
import Stockit.domain.stock.Stock;
import Stockit.interfaces.order.OrderRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long id;

    private int stockOrderPrice; //주문 가격
    private int stockOrderCount; //주문 수량
    private int completedCount; //체결 수량
    private double completedPrice; //체결 가격 평균

    @CreatedDate
    private LocalDateTime stockOrderedDate; //주문 시각

    private String status; //미체결중인 것이 있는지 여부
    private String type; //Buy, Sell

    @ManyToOne //N쪽이므로 owner객체, 양방향 N:1
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne //단방향 N:1
    @JoinColumn(name = "STOCK_CODE")
    private Stock stock;

    ///////////////////////////////////////
    public Order(Member member, Stock stock, OrderRequest orderDto) {
        this.member = member;
        this.stock = stock;
        this.stockOrderPrice = orderDto.getStockOrderPrice();
        this.stockOrderCount = orderDto.getStockOrderCount();
        this.completedCount = 0;
        this.completedPrice = 0.0;
        this.status = OrderStatus.NOT_ACCEPTED.name();
        this.type = orderDto.getOrderType();
    }

    //////////////////////////////////////
    public static Order createOrder(Member member, Stock stock, OrderRequest orderDto) {
        return new Order(member, stock, orderDto);
    }

    public void updateOrder(int tradeCount, int orderPrice) {
        double beforeTotalPrice = this.completedPrice * this.completedCount;
        this.completedCount += tradeCount; //체결 수량 업데이트
        this.completedPrice = (beforeTotalPrice + tradeCount * orderPrice) / this.completedCount;//체결 평균 가격 업데이트
        if (this.completedCount == this.stockOrderCount) this.status = OrderStatus.ACCEPTED.name();
    }

    public void sumSameOrder(int orderCount) {
        this.stockOrderCount += orderCount;
    }

}
