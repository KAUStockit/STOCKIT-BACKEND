package Stockit.order.service;

import Stockit.member.domain.Member;
import Stockit.member.repository.MemberRepository;
import Stockit.order.domain.Order;
import Stockit.order.domain.Stock;
import Stockit.order.dto.OrderDto;
import Stockit.order.repository.OrderRepository;
import Stockit.order.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final StockRepository stockRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Long createOrder(Long memberIdx, Long stockCode, OrderDto orderDto) {
        Member member = memberRepository.findById(memberIdx).orElseThrow(() -> new IllegalArgumentException("회원정보가 없습니다."));
        Stock stock = stockRepository.findById(stockCode).orElseThrow(() -> new IllegalArgumentException("주식 정보가 없습니다."));
        Order savedOrder = orderRepository.save(Order.createOrder(member, stock, orderDto));
        return savedOrder.getOrderIdx();
    }
}
