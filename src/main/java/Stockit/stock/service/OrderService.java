package Stockit.stock.service;

import Stockit.stock.domain.Order;
import Stockit.stock.domain.Stock;
import Stockit.member.domain.Member;
import Stockit.stock.repository.StockRepository;
import Stockit.member.repository.MemberRepository;
import Stockit.stock.repository.OrderRepository;
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
