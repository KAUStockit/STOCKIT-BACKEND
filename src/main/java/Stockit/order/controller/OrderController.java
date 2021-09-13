package Stockit.order.controller;

import Stockit.order.dto.OrderDto;
import Stockit.order.service.OrderService;
import Stockit.response.BasicResponse;
import Stockit.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    //주문 생성
    @PostMapping(value = "/{memberIdx}/{stockCode}/new")
    public ResponseEntity<BasicResponse> createOrder(@PathVariable Long memberIdx, @PathVariable Long stockCode, @RequestBody OrderDto orderDto) {
        Long orderIdx = orderService.createOrder(memberIdx, stockCode, orderDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse<>(HttpStatus.OK.value(), "주문 생성", orderIdx));
    }
}
