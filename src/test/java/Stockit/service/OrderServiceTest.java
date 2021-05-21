package Stockit.service;

import Stockit.domain.Member;
import Stockit.domain.Order;
import Stockit.domain.Stock;
import Stockit.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired OrderService orderService;
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
        Member member = new Member();
        member.setName("회원1");
        member.setNickname("주린이1");
        member.setEmail("stockit@stockit.com");
        member.setPassword("abcdefg");
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