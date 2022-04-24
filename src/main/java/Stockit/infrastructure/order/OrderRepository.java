package Stockit.infrastructure.order;

import Stockit.domain.order.Order;
import Stockit.domain.stock.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // 구매시 판매 후보 리스트
    List<Order> findAllByStockIsAndTypeAndStatusAndStockOrderPriceLessThanEqualOrderByStockOrderPriceAscStockOrderedDate(Stock stock, String type, String status, int stockOrderPrice);

    // 판매시 구매 후보 리스트
    List<Order> findAllByStockIsAndTypeAndStatusAndStockOrderPriceGreaterThanEqualOrderByStockOrderPriceDescStockOrderedDate(Stock stock, String type, String status, int stockOrderPrice);

    Optional<Order> findById(long orderIdx);

//    @Modifying
//    @Query(value = "UPDATE orders o SET o.completed_count = o.completed_count + :orderCount WHERE o.order_id = :orderId", nativeQuery = true)
//    void updateCompletedOrderCount(@Param("orderId") long orderId, @Param("orderCount") int orderCount);

//    @Modifying
//    @Query(value = "UPDATE orders o SET o.status = :orderStatus WHERE o.order_id = :orderId", nativeQuery = true)
//    void updateOrderStatus(@Param("orderId") long orderId, @Param("orderStatus") String orderStatus);

//    @Modifying
//    @Query(value = "UPDATE orders o SET o.completed_price = :completedPrice WHERE o.order_id = :orderId", nativeQuery = true)
//    void updateCompletedPrice(@Param("orderId") long orderId, @Param("completedPrice") double completedPrice);
}
