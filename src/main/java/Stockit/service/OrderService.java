package Stockit.service;

import Stockit.domain.Order;
import Stockit.domain.Stock;
import Stockit.domain.Member;
import Stockit.repository.StockRepository;
import Stockit.repository.MemberRepository;
import Stockit.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;

    @Transactional
    public Long order(Long memberId, String stockCode, Long count) {
        Member member = memberRepository.findOne(memberId);
        Stock stock = stockRepository.findOne(stockCode);
        Order order = Order.createOrder(member, stock, count);

        orderRepository.save(order);
        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId, Long cancelCount) {
        Order order = orderRepository.findOne(orderId);
        order.cancel(cancelCount);
    }
}
