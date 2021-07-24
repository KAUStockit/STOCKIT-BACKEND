package Stockit.member.service;

import Stockit.member.domain.Member;
import Stockit.stock.domain.Order;
import Stockit.stock.domain.Stock;
import Stockit.stock.repository.OrderRepository;
import Stockit.stock.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() {
        //given
        Member member = createMember();
        Stock stock = createStock();

        long orderCnt = 5;

        //when
        Long orderId = orderService.order(member.getId(), stock.getCode(), orderCnt);


        //then
        Order getOrder = orderRepository.findOne(orderId);
    }

    private Member createMember() {
        Member member = new Member("회원1", "주린이1", "stockit@stockit.com", "abcdefg");
        em.persist(member);
        return member;
    }

    private Stock createStock() {
        Stock stock = new Stock();
        stock.setCode("A0");
        stock.setPrice(1000L);
        stock.setName("삼숭");
        stock.set_active(true);
        em.persist(stock);
        return stock;
    }

}