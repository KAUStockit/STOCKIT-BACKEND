package Stockit.service;

import Stockit.domain.Stock;
import Stockit.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final StockRepository stockRepository;

    @Transactional
    public void saveItem(Stock stock) {
        stockRepository.save(stock);
    }

    public List<Stock> findItems() {
        return stockRepository.findAll();
    }

    public Stock findOne(String stockCode) {
        return stockRepository.findOne(stockCode);
    }
}
