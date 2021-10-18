package Stockit.stock.repository;

import Stockit.stock.domain.DailyStock;
import Stockit.stock.domain.DailyStockId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyStockInfoRepository extends JpaRepository<DailyStock, DailyStockId> {

    List<DailyStock> findAllByIdDateOrderByIdStockCode(LocalDate id_date);
}
