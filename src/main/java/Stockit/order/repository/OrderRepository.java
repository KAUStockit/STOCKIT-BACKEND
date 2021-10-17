package Stockit.order.repository;

import Stockit.order.domain.Order;
import Stockit.stock.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // 구매시 판매 후보 리스트
    List<Order> findAllByStockIsAndTypeAndStatusAndStockOrderPriceLessThanEqualOrderByStockOrderPriceAscStockOrderedDate(Stock stock, String type, String status, int stockOrderPrice);
    // 판매시 구매 후보 리스트
    List<Order> findAllByStockIsAndTypeAndStatusAndStockOrderPriceGreaterThanEqualOrderByStockOrderPriceDescStockOrderedDate(Stock stock, String type, String status, int stockOrderPrice);
    Optional<Order> findByOrderIdx(long orderIdx);

    @Modifying
    @Query(value = "UPDATE orders o SET o.completed_count = o.completed_count + :orderCount WHERE o.order_idx = :orderIdx", nativeQuery = true)
    void updateCompletedOrderCount(@Param("orderIdx") long orderIdx, @Param("orderCount") int orderCount);

    @Modifying
    @Query(value = "UPDATE orders o SET o.status = :orderStatus WHERE o.order_idx = :orderIdx", nativeQuery = true)
    void updateOrderStatus(@Param("orderIdx") long orderIdx, @Param("orderStatus") String orderStatus);

    @Modifying
    @Query(value = "UPDATE orders o SET o.completed_price = :completedPrice WHERE o.order_idx = :orderIdx", nativeQuery = true)
    void updateCompletedPrice(@Param("orderIdx") long orderIdx, @Param("completedPrice") double completedPrice);
}
