package Stockit.stock.service;

import Stockit.stock.domain.Stock;
import Stockit.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    public String registerStock(Stock stock) {
        return stockRepository.save(stock).getCode();
    }
}
