package Stockit.stock.domain;

import Stockit.stock.domain.Order;
import Stockit.stock.domain.Stock;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "accepted_item")
@Getter @Setter
public class AcceptedItem {
    //하나의 Order에서 여러 번 나눠서 체결이 될 수 있으므로 OrderItem을 만듦

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Stock stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int acceptedPrice;
    private int acceptedCount;


    //==비즈니스 로직==//
    public void acceptedStock(int quantity) {
        if(quantity > this.order.getStockOrderCount()) {
            throw new IllegalStateException("주문수량보다 더 많은 주식이 체결될 수 없습니다.");
        }
        this.order.setStockOrderCount(this.order.getStockOrderCount() - quantity);
        this.setAcceptedCount(quantity);
        //JPA로 수량 변경 저장하기
    }
}
