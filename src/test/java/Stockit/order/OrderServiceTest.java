package Stockit.order;

import Stockit.member.domain.Member;
import Stockit.member.service.MemberService;
import Stockit.stock.domain.Order;
import Stockit.stock.domain.Stock;
import Stockit.stock.dto.OrderDto;
import Stockit.stock.repository.OrderRepository;
import Stockit.stock.service.OrderService;
import Stockit.stock.service.StockService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired private OrderService orderService;
    @Autowired private MemberService memberService;
    @Autowired private StockService stockService;

    private Member member;
    private Stock stock;

    @BeforeEach
    public void 기본_설정() {
        member = new Member("회원1", "주린이1", "stockit@stockit.com", "abcdefg");
        stock = new Stock("A1", "주식1", 10000, false);
        memberService.join(member);
        stockService.registerStock(stock);
    }

    @Test
    public void 상품주문() {
        //given
        int orderCnt = 5;

        //when
        int memberBalance = member.getBalance();
        OrderDto orderDto = orderService.order(member.getIdx(), stock.getCode(), orderCnt);
        int afterMemberBalance = member.getBalance();

        //then
        Order order = orderService.findOrder(orderDto.getOrderId());
        Assertions.assertThat(afterMemberBalance).isEqualTo(memberBalance - orderCnt * stock.getPrice());
    }

}