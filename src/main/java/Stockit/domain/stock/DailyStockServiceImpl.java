package Stockit.domain.stock;

import Stockit.infrastructure.stock.DailyStockRepository;
import Stockit.infrastructure.stock.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@EnableScheduling
@RequiredArgsConstructor
@Service
public class DailyStockServiceImpl implements DailyStockService {

    private final DailyStockRepository repository;
    private final StockRepository stockRepository;

    @Override
    @Scheduled(cron = "00 00 18 * * ?")
    public void pushDailyStockList() {
        final List<Stock> stockList = stockRepository.findAll();
        for (Stock stock : stockList) {
            final DailyStock dailyStock = new DailyStock(stock);
            repository.save(dailyStock);
        }
    }
}
