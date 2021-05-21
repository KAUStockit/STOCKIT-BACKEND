package Stockit.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //N쪽이므로 owner객체
    @JoinColumn(name = "member_id")
    private Member member;              //주문 회원

    private Long stockOrderPrice;            //주문 가격
    private Long stockOrderCount;            //주문 수량
    private LocalDateTime orderDate;    //주문시간

    @OneToOne
    private Stock stock;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<AcceptedItem> acceptedItems = new ArrayList<>(); //주문이 나눠서 체결될 경우 담음

    @Enumerated(EnumType.STRING)
    private OrderStatus status;         //주문상태 (체결, 미체결) -> 수량이 모두 체결될 경우 체결로 바꿈


    /////////////////////////////////////////////////////////////////////////////////

    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    //==생성 메서드==//
    public static Order createOrder(Member member, Stock stock, Long stockCount) {
        Order order = new Order();
        order.setMember(member);
        order.setStock(stock);
        order.setStockOrderPrice(stock.getPrice());
        order.setStockOrderCount(stockCount);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.NOT_ACCEPTED);
        return order;
    }

    //==비즈니스 로직==//
    public void cancel(Long cancelCount) {
        if(this.status == OrderStatus.ACCEPTED) { //체결된 상태
            throw new IllegalStateException("이미 체결된 주문은 취소가 불가능합니다.");
        } else if(this.getStockOrderCount() < cancelCount) {
            throw new IllegalStateException("미체결 주문 수보다 더 많은 주문취소는 불가능합니다.");
        }
        this.setStockOrderCount(this.getStockOrderCount() - cancelCount);
        if(this.getStockOrderCount() == 0) this.setStatus(OrderStatus.ACCEPTED);
    }

    //==조회 로직==//
    public List<AcceptedItem> getCurrentOrderStatus() {
        return this.getAcceptedItems();
    }
}