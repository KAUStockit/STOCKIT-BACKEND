package Stockit.stock.repository;

import Stockit.stock.domain.DailyStockInfo;
import Stockit.stock.domain.DailyStockInfoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyStockInfoRepository extends JpaRepository<DailyStockInfo, DailyStockInfoId> {
}
