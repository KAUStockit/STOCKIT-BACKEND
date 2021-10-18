package Stockit.stock.controller;

import Stockit.response.BasicResponse;
import Stockit.response.SuccessResponse;
import Stockit.stock.dto.StockInfo;
import Stockit.stock.dto.StockUpdateRequest;
import Stockit.stock.service.StockService;
import javassist.NotFoundException;
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
    public ResponseEntity<BasicResponse> create(@RequestBody StockInfo form) throws NotFoundException {
        Long stockCode = stockService.createNewStock(form);
        final StockInfo stockDto = stockService.findStock(stockCode);
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
    public ResponseEntity<BasicResponse> getStockInfo(@PathVariable Long stockCode) throws NotFoundException {
        final StockInfo stockInfo = stockService.findStock(stockCode);
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse<>(HttpStatus.OK.value(), "주식 하나 조회", stockInfo));
    }
    
    //주식 가격/상태 업데이트
    @PutMapping(value = "/update/{stockCode}")
    public ResponseEntity<BasicResponse> updateStock(@RequestBody StockUpdateRequest stockUpdateRequest, @PathVariable Long stockCode) {
        stockService.updateStock(stockUpdateRequest, stockCode);
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse<>(HttpStatus.OK.value(),"종목 가격/상태 업데이트 완료", stockCode));
    }
}
