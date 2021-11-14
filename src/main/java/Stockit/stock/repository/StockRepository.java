package Stockit.stock.repository;

import Stockit.stock.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findAllByPriceLessThanEqualOrderById(int price);
    List<Stock> findAllByOrderByPriceDesc();
    @Query(nativeQuery = true, value = "select * from stock s left join orders o on s.stock_code = o.stock_code group by s.stock_code order by count(s.stock_code) desc, s.stock_code")
    List<Stock> findAllStocksOrderByTotalOrder();
    @Query(nativeQuery = true, value = "select * from stock s left join (select * from daily_stock ds where ds.date = :yesterday) as tds on s.stock_code = tds.stock_code group by s.stock_code order by (s.price - tds.price) desc, s.stock_code")
    List<Stock> findAllStocksOrderByVariation(@Param("yesterday") LocalDate yesterday);
}
