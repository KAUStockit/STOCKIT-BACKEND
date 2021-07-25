package Stockit.stock.controller;

import Stockit.stock.dto.OrderDto;
import Stockit.stock.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("stocks/{stockCode}")
    public ResponseEntity<OrderDto> buyStock(@PathVariable String stockCode, @RequestBody Long memberIdx, @RequestBody int count, @RequestBody int price) {
        OrderDto orderDto = orderService.order(memberIdx, stockCode, count);
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }
}
