package Stockit.order.service;

import Stockit.member.domain.Member;
import Stockit.member.repository.MemberRepository;
import Stockit.order.domain.Order;
import Stockit.order.domain.OrderStatus;
import Stockit.order.domain.OrderType;
import Stockit.order.domain.Stock;
import Stockit.order.dto.OrderDto;
import Stockit.order.repository.OrderRepository;
import Stockit.order.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final StockRepository stockRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Order createOrder(Long memberIdx, Long stockCode, OrderDto orderDto) {
        Member member = memberRepository.findById(memberIdx).orElseThrow(() -> new IllegalArgumentException("회원정보가 없습니다."));
        Stock stock = stockRepository.findById(stockCode).orElseThrow(() -> new IllegalArgumentException("주식 정보가 없습니다."));

        if (orderDto.getOrderType().equals(OrderType.Buy.name()) && member.getBalance() < orderDto.getStockOrderPrice() * orderDto.getStockOrderCount())
            throw new IllegalStateException("계좌 잔액이 부족합니다.");

        return orderRepository.save(Order.createOrder(member, stock, orderDto));
    }

    @Transactional
    public void executeOrder(Order order) {
        final Stock stock = order.getStock();
        final List<Order> sortedOrderList = this.getSortedOrderList(stock, order.getType());
        for (Order counterOrder: sortedOrderList) {
            // 미체결, 반대 타입
            final boolean checkOrder = counterOrder != order &&
                    counterOrder.getStatus().equals(OrderStatus.NOT_ACCEPTED.name()) &&
                    !counterOrder.getType().equals(order.getType());

            if (checkOrder) {
                final boolean isFinishedTrade = executeTrade(order, counterOrder);
                if (isFinishedTrade) break;
            }
        }
    }

    private boolean executeTrade(Order order, Order counterOrder) {
        int restCounterOrderCount = counterOrder.getStockOrderCount() - counterOrder.getCompletedCount();
        // 이번에 잔여 거래 모두 체결 가능
        if (order.getStockOrderCount() <= restCounterOrderCount) {
            //체결 수량 반영
            orderRepository.updateCompletedOrderCount(counterOrder.getOrderIdx(), order.getStockOrderCount());
            orderRepository.updateCompletedOrderCount(order.getOrderIdx(), order.getStockOrderCount());

            //잔고 업데이트
            int price = order.getStockOrderPrice() * order.getStockOrderCount();
            if (order.getType().equals(OrderType.Sell.name())) price = price * -1;
            memberRepository.updateBalance(order.getMember().getIdx(), -price);
            memberRepository.updateBalance(counterOrder.getMember().getIdx(), price);

            //전체 체결/미체결 반영
            orderRepository.updateOrderStatus(order.getOrderIdx(), OrderStatus.Accepted.name());
            if (order.getStockOrderCount() == restCounterOrderCount) orderRepository.updateOrderStatus(counterOrder.getOrderIdx(), OrderStatus.Accepted.name());
            return true;
        } else {
            //체결 수량 반영
            orderRepository.updateCompletedOrderCount(counterOrder.getOrderIdx(), counterOrder.getStockOrderCount());
            orderRepository.updateCompletedOrderCount(order.getOrderIdx(), counterOrder.getStockOrderCount());

            //잔고 업데이트
            int price = counterOrder.getStockOrderPrice() * counterOrder.getStockOrderCount();
            if (order.getType().equals(OrderType.Sell.name())) price = price * -1;
            memberRepository.updateBalance(order.getMember().getIdx(), -price);
            memberRepository.updateBalance(counterOrder.getMember().getIdx(), price);

            //전체 체결/미체결 반영
            orderRepository.updateOrderStatus(counterOrder.getOrderIdx(), OrderStatus.Accepted.name());
            if (order.getStockOrderCount() == restCounterOrderCount) orderRepository.updateOrderStatus(order.getOrderIdx(), OrderStatus.Accepted.name());
            return false;
        }
    }

    private List<Order> getSortedOrderList(Stock stock, String type) {
        if (type.equals(OrderType.Buy.name())) return orderRepository.findAllByStockIsAndTypeOrderByStockOrderPriceAscStockOrderedDate(stock, OrderType.Sell.name());
        else return orderRepository.findAllByStockIsAndTypeOrderByStockOrderPriceDescStockOrderedDate(stock, OrderType.Buy.name());
    }
}
