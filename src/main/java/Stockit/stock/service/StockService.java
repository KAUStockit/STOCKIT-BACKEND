package Stockit.stock.service;

import Stockit.order.vo.StockUpdateVO;
import Stockit.stock.domain.DailyStockInfo;
import Stockit.stock.domain.Stock;
import Stockit.stock.dto.DailyStockInfoDto;
import Stockit.stock.dto.StockDto;
import Stockit.stock.repository.DailyStockInfoRepository;
import Stockit.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final DailyStockInfoRepository dailyStockInfoRepository;

    // 주식 종목 생성
    @Transactional
    public Long createNewStock(Stock stock) {
        stockRepository.save(stock);
        return stock.getStockCode();
    }

    // 모든 주식 찾기
    public List<Stock> findAllStocks() {
        return stockRepository.findAll();
    }

    public List<DailyStockInfoDto> findAllStocksWithPercent() {
        List<Stock> stockList = stockRepository.findAll();
        final List<DailyStockInfo> yesterdayStockPriceList = dailyStockInfoRepository.findAllByIdDateOrderByIdStockCode(LocalDate.now().minusDays(1));
        List<DailyStockInfoDto> stockInfoList = new ArrayList<>();
        for (int i=0; i<stockList.size(); i++) {
            Stock stock = stockList.get(i);
            int yesterdayPrice = yesterdayStockPriceList.get(i).getPrice();
            double percent = (double)Math.round((double)(stock.getPrice()-yesterdayPrice)/yesterdayPrice * 10000)/100;
            stockInfoList.add(new DailyStockInfoDto(stock.getStockCode(), stock.getStockName(), stock.getPrice(),
                    stock.getDescription(), stock.getCategory(), stock.isActive(), percent));
        }
        return stockInfoList;
    }

    // 주식 하나 찾기
    public StockDto findStock(long stockCode) {
        return new StockDto(stockRepository.findById(stockCode).get());
    }

    // 주식 가격 업데이트
    @Transactional
    public void updateStock(StockUpdateVO stockUpdateVO, Long stockCode) {
        Stock stock = stockRepository.getOne(stockCode);
        stock.setPrice(stockUpdateVO.getPrice());
        stock.setActive(stockUpdateVO.isActive());
        stockRepository.save(stock);
    }
}
