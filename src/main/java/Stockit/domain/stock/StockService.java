package Stockit.domain.stock;

import Stockit.infrastructure.stock.DailyStockRepository;
import Stockit.infrastructure.stock.StockRepository;
import Stockit.interfaces.stock.dto.DailyStockInfo;
import Stockit.interfaces.stock.dto.StockInfo;
import Stockit.interfaces.stock.dto.StockUpdateRequest;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final DailyStockRepository dailyStockRepository;

    // 주식 종목 생성
    @Transactional
    public Long createNewStock(StockInfo form) {
        Stock stock = new Stock(form);
        stockRepository.save(stock);
        return stock.getId();
    }

    // 모든 주식 찾기
    public List<StockInfo> findAllStocks() {
        return stockRepository.findAll().stream().map(StockInfo::new).collect(Collectors.toList());
    }

    // price원 이하 모든 주식 찾기
    public List<StockInfo> findAllStocksUnderPrice(int price) {
        return stockRepository.findAllByPriceLessThanEqualOrderById(price)
                .stream().map(StockInfo::new).collect(Collectors.toList());
    }

    public List<StockInfo> findAllStocksOrderByTotalOrder() {
        return stockRepository.findAllStocksOrderByTotalOrder()
                .stream().map(StockInfo::new).collect(Collectors.toList());
    }

    public List<StockInfo> findAllStocksOrderByPrice() {
        return stockRepository.findAllByOrderByPriceDesc()
                .stream().map(StockInfo::new).collect(Collectors.toList());
    }

    public List<StockInfo> findAllStocksOrderByVariation() {
        Calendar cal = Calendar.getInstance();
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        cal.add(Calendar.DATE, -1); //날짜를 하루 뺀다.
        return stockRepository.findAllStocksOrderByVariation(cal.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .stream().map(StockInfo::new).collect(Collectors.toList());
    }

    public List<DailyStockInfo> findAllStocksWithPercent() {
        List<Stock> stockList = stockRepository.findAll();
        final List<DailyStock> yesterdayStockPriceList = dailyStockRepository.findAllByIdDateOrderByIdStockCode(LocalDate.now().minusDays(1));
        List<DailyStockInfo> stockInfoList = new ArrayList<>();
        for (int i = 0; i < stockList.size(); i++) {
            Stock stock = stockList.get(i);
            int yesterdayPrice = yesterdayStockPriceList.get(i).getPrice();
            double percent = (double) Math.round((double) (stock.getPrice() - yesterdayPrice) / yesterdayPrice * 10000) / 100;
            stockInfoList.add(new DailyStockInfo(stock.getId(), stock.getStockName(), stock.getPrice(),
                    stock.getDescription(), stock.getCategory(), stock.isActive(), percent));
        }
        return stockInfoList;
    }

    // 주식 하나 찾기
    public StockInfo findStock(Long stockCode) throws NotFoundException {
        return new StockInfo(stockRepository.findById(stockCode).orElseThrow(() -> new NotFoundException("해당 주식을 찾을 수 없습니다.")));
    }

    // 주식 가격 업데이트
    @Transactional
    public void updateStock(StockUpdateRequest stockUpdateRequest, Long stockCode) {
        Stock stock = stockRepository.getOne(stockCode);
        stock.updateStock(stockUpdateRequest.getPrice(), stockUpdateRequest.isActive());
        stockRepository.save(stock);
    }

    public List<StockInfo> findStockList(List<Long> stockCodeList) {
        List<StockInfo> stockInfoList = new ArrayList<>();
        for (Long stockCode : stockCodeList) {
            stockInfoList.add(new StockInfo(stockRepository.getOne(stockCode)));
        }
        return stockInfoList;
    }
}
