package Stockit.stock.controller;

import Stockit.response.ApiResponse;
import Stockit.stock.dto.StockInfo;
import Stockit.stock.dto.StockUpdateRequest;
import Stockit.stock.service.StockService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    //주식회사 생성
    @PostMapping
    public ApiResponse<Long> create(@RequestBody StockInfo form) throws NotFoundException {
        return ApiResponse.ok(stockService.createNewStock(form));
    }

    //모든 주식 조회
    @GetMapping
    public ApiResponse<List<StockInfo>> stockList() {
        return ApiResponse.ok(stockService.findAllStocks());
    }

    //주식 하나 조회
    @GetMapping(value = "/{stockCode}")
    public ApiResponse<StockInfo> getStockInfo(@PathVariable Long stockCode) throws NotFoundException {
        return ApiResponse.ok(stockService.findStock(stockCode));
    }
    
    //주식 가격/상태 업데이트
    @PutMapping(value = "/{stockCode}")
    public ApiResponse<Long> updateStock(@RequestBody StockUpdateRequest stockUpdateRequest, @PathVariable Long stockCode) {
        stockService.updateStock(stockUpdateRequest, stockCode);
        return ApiResponse.ok(stockCode);
    }
}
