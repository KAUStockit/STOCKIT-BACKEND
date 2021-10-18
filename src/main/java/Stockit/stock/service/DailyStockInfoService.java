package Stockit.stock.service;

import Stockit.stock.domain.DailyStock;
import Stockit.stock.domain.Stock;
import Stockit.stock.repository.DailyStockInfoRepository;
import Stockit.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@EnableScheduling
@RequiredArgsConstructor
@Service
public class DailyStockInfoService {

    private final DailyStockInfoRepository repository;
    private final StockRepository stockRepository;

    @Scheduled(cron = "0 0 18 * * ?")
    public void pushDailyStockList() {
        final List<Stock> stockList = stockRepository.findAll();
        for (Stock stock: stockList) {
            final DailyStock dailyStock = new DailyStock(stock);
            repository.save(dailyStock);
        }
    }
}
