package Stockit.infrastructure.stock;

import Stockit.domain.stock.DailyStock;
import Stockit.domain.stock.DailyStockId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyStockRepository extends JpaRepository<DailyStock, DailyStockId> {

    List<DailyStock> findAllByIdDateOrderByIdStockCode(LocalDate id_date);
}
