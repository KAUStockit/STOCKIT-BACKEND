package Stockit.stock.domain;

import Stockit.member.domain.Member;
import Stockit.stock.dto.OrderDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //N쪽이므로 owner객체
    @JoinColumn(name = "member_id")
    private Member member;              //주문 회원

    private int stockOrderPrice;            //주문 가격

    @Setter
    private int stockOrderCount;            //주문 수량

    @CreatedDate
    private LocalDateTime orderDate;    //주문시간

    @LastModifiedDate
    private LocalDateTime modifiedDate; //주문 취소한 경우 업데이트

    @OneToOne
    private Stock stock;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<AcceptedItem> acceptedItems = new ArrayList<>(); //주문이 나눠서 체결될 경우 담음

    @Enumerated(EnumType.STRING)
    @Setter
    private OrderStatus status = OrderStatus.NOT_ACCEPTED;         //주문상태 (체결, 미체결) -> 수량이 모두 체결될 경우 체결로 바꿈


    //==생성자==//
    public Order(Member member, Stock stock, int stockOrderPrice, int stockOrderCount) {
        this();
        this.member = member;
        this.stock = stock;
        this.stockOrderPrice = stockOrderPrice;
        this.stockOrderCount = stockOrderCount;
    }
    /////////////////////////////////////////////////////////////////////////////////

    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    //==비즈니스 로직==//
    public void cancel(int cancelCount) {
        if(this.status == OrderStatus.ACCEPTED) { //체결된 상태
            throw new IllegalStateException("이미 체결된 주문은 취소가 불가능합니다.");
        } else if(this.getStockOrderCount() < cancelCount) {
            throw new IllegalStateException("미체결 주문 수보다 더 많은 주문취소는 불가능합니다.");
        }
        this.setStockOrderCount(this.getStockOrderCount() - cancelCount);
        this.member.setBalance(this.member.getBalance() + cancelCount * this.stockOrderPrice);
        if(this.getStockOrderCount() == 0) this.setStatus(OrderStatus.ACCEPTED);
    }

    //==조회 로직==//
    public List<AcceptedItem> getCurrentOrderStatus() {
        return this.getAcceptedItems();
    }
}