package Stockit.stock.controller;

import Stockit.order.vo.StockUpdateVO;
import Stockit.response.BasicResponse;
import Stockit.response.SuccessResponse;
import Stockit.stock.domain.Stock;
import Stockit.stock.dto.StockDto;
import Stockit.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        final StockDto stockDto = stockService.findStock(stockCode);
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse<>(HttpStatus.OK.value(), "주식회사 생성", stockDto));
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
        final StockDto stock = stockService.findStock(stockCode);
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse<>(HttpStatus.OK.value(), "주식 하나 조회", stock));
    }
    
    //주식 가격/상태 업데이트
    @PutMapping(value = "/update/{stockCode}")
    public ResponseEntity<BasicResponse> updateStock(@RequestBody StockUpdateVO stockUpdateVO, @PathVariable Long stockCode) {
        stockService.updateStock(stockUpdateVO, stockCode);
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse<>(HttpStatus.OK.value(),"종목 가격/상태 업데이트 완료", stockCode));
    }
}
