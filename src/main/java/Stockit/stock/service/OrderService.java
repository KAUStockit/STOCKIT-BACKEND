package Stockit.stock.service;

import Stockit.stock.domain.Order;
import Stockit.stock.domain.Stock;
import Stockit.member.domain.Member;
import Stockit.stock.dto.OrderDto;
import Stockit.stock.repository.StockRepository;
import Stockit.member.repository.MemberRepository;
import Stockit.stock.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;

    public OrderDto order(Long memberIdx, String stockCode, int count) {
        Member member = memberRepository.findById(memberIdx).orElseThrow(() -> new IllegalArgumentException("해당 멤버가 없습니다. idx = " + memberIdx));
        Stock stock = stockRepository.findById(stockCode).orElseThrow(() -> new IllegalStateException("해당 주식이 없습니다. stockCode = " + stockCode));
        if (member.getBalance() < count * stock.getPrice()) throw new IllegalStateException("보유 잔액이 부족합니다.");

        Order order = new Order(member, stock, count, stock.getPrice());
        orderRepository.save(order);
        member.setBalance(member.getBalance() - count * stock.getPrice());
        return new OrderDto(order.getId(), member, order.getStockOrderPrice(), order.getStockOrderCount(),
                order.getOrderDate(), order.getStock().getCode());
    }

    public void cancelOrder(Long orderId, int cancelCount) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalStateException("해당 주문이 없습니다."));
        order.cancel(order.getStockOrderCount());
    }

    public Order findOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new IllegalStateException("해당 주문이 없습니다."));
    }
}
