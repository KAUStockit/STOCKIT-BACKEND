package Stockit.order.repository;

import Stockit.order.domain.Order;
import Stockit.order.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByStockIsAndTypeOrderByStockOrderPriceAscStockOrderedDate(Stock stock, String type);
    List<Order> findAllByStockIsAndTypeOrderByStockOrderPriceDescStockOrderedDate(Stock stock, String type);
    @Modifying
    @Query(value = "UPDATE orders o SET o.completed_count = o.completed_count + :orderCount WHERE o.order_idx = :orderIdx", nativeQuery = true)
    void updateCompletedOrderCount(@Param("orderIdx") long orderIdx, @Param("orderCount") int orderCount);
    @Modifying
    @Query(value = "UPDATE orders o SET o.status = :orderStatus WHERE o.order_idx = :orderIdx", nativeQuery = true)
    void updateOrderStatus(@Param("orderIdx") long orderIdx, @Param("orderStatus") String orderStatus);
}
