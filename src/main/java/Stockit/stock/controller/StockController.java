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
    /*
    * type
    * 0: 일반조회
    * 1: 인기순
    * 2: 시총순
    * 3: 수익률순(전날 대비)
     */
    @GetMapping
    public ApiResponse<List<StockInfo>> stockList(@RequestParam(defaultValue = "-1") Integer price, @RequestParam(defaultValue = "0") Integer type) {
        if (price != -1) return ApiResponse.ok(stockService.findAllStocksUnderPrice(price));
        else if (type == 1) return ApiResponse.ok(stockService.findAllStocksOrderByTotalOrder());
        else if (type == 2) return ApiResponse.ok(stockService.findAllStocksOrderByPrice());
        else if (type == 3) return ApiResponse.ok(stockService.findAllStocksOrderByVariation());
        else return ApiResponse.ok(stockService.findAllStocks());
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
