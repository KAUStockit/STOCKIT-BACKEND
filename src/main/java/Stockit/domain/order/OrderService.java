package Stockit.domain.order;

import Stockit.interfaces.order.dto.OrderRequest;
import javassist.NotFoundException;

public interface OrderService {
    Long createOrder(Long memberIdx, Long stockCode, OrderRequest orderDto) throws NotFoundException;

    void executeOrder(Long memberIdx, Order order);

    boolean executeTrade(Order order, Order counterOrder);
}
