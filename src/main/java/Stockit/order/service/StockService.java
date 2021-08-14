package Stockit.order.service;

import Stockit.order.domain.Stock;
import Stockit.order.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    @Transactional
    public Long createNewStock(Stock stock) {
        stockRepository.save(stock);
        return stock.getStockCode();
    }
}
