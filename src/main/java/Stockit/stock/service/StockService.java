package Stockit.stock.service;

import Stockit.order.vo.StockUpdateVO;
import Stockit.stock.domain.Stock;
import Stockit.stock.dto.StockDto;
import Stockit.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

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
