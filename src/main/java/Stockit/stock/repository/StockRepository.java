package Stockit.stock.repository;

import Stockit.stock.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findAllByPriceLessThanEqualOrderById(int price);
    List<Stock> findAllByOrderByPriceDesc();
    @Query(nativeQuery = true, value = "select * from stock s left join orders o on s.stock_code = o.stock_code group by s.stock_code order by count(s.stock_code) desc, s.stock_code")
    List<Stock> findAllStocksOrderByTotalOrder();
}
