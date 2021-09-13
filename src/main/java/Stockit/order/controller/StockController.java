package Stockit.order.controller;

import Stockit.order.domain.Stock;
import Stockit.order.dto.StockDto;
import Stockit.order.service.StockService;
import Stockit.order.vo.StockUpdateVO;
import Stockit.order.vo.StockVO;
import Stockit.response.BasicResponse;
import Stockit.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    //주식회사 생성
    @PostMapping(value = "/new")
    public ResponseEntity<BasicResponse> create(@RequestBody StockDto form) {
        Stock stock = new Stock(form);
        Long stockCode = stockService.createNewStock(stock);
        final StockVO stockVO = new StockVO(stockCode, stock.getStockName(), stock.getPrice(), stock.getDescription(), stock.getCategory(), true);
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse<>(HttpStatus.OK.value(), "주식회사 생성", stockVO));
    }

    //모든 주식 조회
    @GetMapping(value = "/info/list")
    public ResponseEntity<BasicResponse> stockList() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse<>(HttpStatus.OK.value(), "모든 주식목록 조회", stockService.findAllStocks()));
    }

    //주식 하나 조회
    @GetMapping(value = "/info/{stockCode}")
    public ResponseEntity<BasicResponse> getStockInfo(@PathVariable Long stockCode) {
        final Optional<Stock> stock = stockService.findStock(stockCode);
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse<>(HttpStatus.OK.value(), "주식 하나 조회", stock.orElseThrow(IllegalArgumentException::new)));
    }
    
    //주식 가격/상태 업데이트
    @PutMapping(value = "/update/{stockCode}")
    public ResponseEntity<BasicResponse> updateStock(@RequestBody StockUpdateVO stockUpdateVO, @PathVariable Long stockCode) {
        stockService.updateStock(stockUpdateVO, stockCode);
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse<>(HttpStatus.OK.value(),"종목 가격/상태 업데이트 완료", stockCode));
    }
}
