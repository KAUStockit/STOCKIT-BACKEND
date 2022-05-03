package Stockit.domain.order;

import Stockit.domain.member.Account;
import Stockit.domain.member.AccountStock;
import Stockit.domain.member.Member;
import Stockit.domain.stock.Stock;
import Stockit.infrastructure.member.MemberRepository;
import Stockit.infrastructure.order.OrderRepository;
import Stockit.infrastructure.stock.StockRepository;
import Stockit.interfaces.order.dto.OrderRequest;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final StockRepository stockRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Long createOrder(Long memberIdx, Long stockCode, OrderRequest orderDto) throws NotFoundException {
        Member member = memberRepository.findById(memberIdx).orElseThrow(() -> new IllegalArgumentException("회원정보가 없습니다."));
        Stock stock = stockRepository.findById(stockCode).orElseThrow(() -> new IllegalArgumentException("주식 정보가 없습니다."));

        final Optional<Order> optionalNotAcceptedOrder = member.getOrders().stream().filter(x ->
                Objects.equals(x.getStock().getId(), stockCode)
                        && x.getStatus().equals(OrderStatus.NOT_ACCEPTED.name())).findFirst();

        if (optionalNotAcceptedOrder.isPresent() &&
                !optionalNotAcceptedOrder.get().getType().equals(orderDto.getOrderType()))
            throw new IllegalStateException("이미 반대 종류의 주문이 있습니다. 주문 취소후 다시 시도하세요.");

        if (orderDto.getOrderType().equals(OrderType.Buy.name()) && member.getAccount().getBalance() < orderDto.getStockOrderPrice() * orderDto.getStockOrderCount())
            throw new IllegalStateException("계좌 잔액이 부족합니다.");

        final Order savedOrder = orderRepository.save(Order.createOrder(member, stock, orderDto));
        executeOrder(memberIdx, savedOrder);

        return savedOrder.getId();
    }

    public void executeOrder(Long memberIdx, Order order) {
        final List<Order> sortedCounterOrderList = this.getSortedCounterOrderList(order);
        for (Order counterOrder : sortedCounterOrderList) {
            if (Objects.equals(counterOrder.getMember().getId(), memberIdx)) continue;
            final boolean isFinished = executeTrade(order, counterOrder);
            if (isFinished) break;
        }
    }

    public boolean executeTrade(Order order, Order counterOrder) {
        final Stock stock = order.getStock();
        final Order buyOrder = order.getType().equals("Buy") ? order : counterOrder;
        final Order sellOrder = order.getType().equals("Sell") ? order : counterOrder;

        final Member buyer = buyOrder.getMember();
        final Member seller = sellOrder.getMember();
        final Account buyerAccount = buyer.getAccount();
        final Account sellerAccount = seller.getAccount();

        final int buyOrderRestCount = buyOrder.getStockOrderCount() - buyOrder.getCompletedCount();
        final int sellOrderRestCount = sellOrder.getStockOrderCount() - sellOrder.getCompletedCount();

        final int tradeCount = Math.min(buyOrderRestCount, sellOrderRestCount);
        final int tradePrice = tradeCount * order.getStockOrderPrice();

        //체결 수량 반영
        buyOrder.updateOrder(tradeCount, order.getStockOrderPrice());
        sellOrder.updateOrder(tradeCount, order.getStockOrderPrice());

        //보유 주식 업데이트
        final List<AccountStock> buyerAccountStockList = buyerAccount.getAccountStocks();
        final List<AccountStock> sellerAccountStockList = sellerAccount.getAccountStocks();
        final Optional<AccountStock> buyerAccountStock = buyerAccountStockList.stream().filter(x -> Objects.equals(x.getStock().getId(), buyOrder.getStock().getId())).findFirst();
        final Optional<AccountStock> sellerAccountStock = sellerAccountStockList.stream().filter(x -> Objects.equals(x.getStock().getId(), sellOrder.getStock().getId())).findFirst();

        buyerAccountStock.ifPresentOrElse(x -> x.updateAmount(tradeCount),
                () -> buyerAccountStockList.add(new AccountStock(buyerAccount, stock, tradeCount)));
        sellerAccountStock.ifPresentOrElse(x -> x.updateAmount(-tradeCount),
                () -> sellerAccountStockList.add(new AccountStock(sellerAccount, stock, -tradeCount)));

        //잔고 업데이트
        buyerAccount.updateBalance(-tradePrice);
        sellerAccount.updateBalance(tradePrice);

        //주식 가격 업데이트
        order.getStock().updateStock(order.getStockOrderPrice(), order.getStock().isActive());

        return order.getStatus().equals(OrderStatus.ACCEPTED.name());
    }

    private List<Order> getSortedCounterOrderList(Order order) {
        final Stock stock = order.getStock();
        final String type = order.getType();
        final int stockOrderPrice = order.getStockOrderPrice();
        if (type.equals(OrderType.Buy.name()))
            return orderRepository.findAllByStockIsAndTypeAndStatusAndStockOrderPriceLessThanEqualOrderByStockOrderPriceAscStockOrderedDate(stock, OrderType.Sell.name(), OrderStatus.NOT_ACCEPTED.name(), stockOrderPrice);
        else
            return orderRepository.findAllByStockIsAndTypeAndStatusAndStockOrderPriceGreaterThanEqualOrderByStockOrderPriceDescStockOrderedDate(stock, OrderType.Buy.name(), OrderStatus.NOT_ACCEPTED.name(), stockOrderPrice);
    }
}
