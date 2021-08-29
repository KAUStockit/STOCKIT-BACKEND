package Stockit.order.controller;

import Stockit.order.domain.Stock;
import Stockit.order.dto.StockDto;
import Stockit.order.service.StockService;
import Stockit.order.vo.StockUpdateVO;
import Stockit.order.vo.StockVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    //주식회사 생성
    @PostMapping(value = "/new")
    public ResponseEntity<StockVO> create(@RequestBody StockDto form) {
        Stock stock = new Stock(form);
        Long stockCode = stockService.createNewStock(stock);
        return ResponseEntity.status(HttpStatus.OK).body(new StockVO(stockCode, stock.getStockName(), stock.getPrice(), true));
    }

    //모든 주식 조회
    @GetMapping(value = "/list")
    public ResponseEntity<List<Stock>> stockList() {
        return ResponseEntity.status(HttpStatus.OK).body(stockService.findAllStocks());
    }

    //주식 하나 조회
    @GetMapping(value = "/{stockCode}")
    public ResponseEntity<Stock> getStockInfo(@PathVariable Long stockCode) {
        return ResponseEntity.status(HttpStatus.OK).body(stockService.findStock(stockCode).orElseThrow(() -> new IllegalArgumentException("해당 주식이 없습니다.")));
    }
    
    //주식 가격/상태 업데이트
    @PutMapping(value = "/update/{stockCode}")
    public ResponseEntity<String> updateStock(@RequestBody StockUpdateVO stockUpdateVO, @PathVariable Long stockCode) {
        stockService.updateStock(stockUpdateVO, stockCode);
        return ResponseEntity.status(HttpStatus.OK).body("종목 가격/상태 업데이트 완료");
    }
}
