package Stockit.order.controller;

import Stockit.order.domain.Stock;
import Stockit.order.dto.StockDto;
import Stockit.order.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @PostMapping(value = "/new")
    public ResponseEntity<Long> create(@RequestBody StockDto form) {
        Stock stock = new Stock(form);
        stockService.createNewStock(stock);
        return ResponseEntity.status(HttpStatus.OK).body(stock.getStockCode());
    }
}
