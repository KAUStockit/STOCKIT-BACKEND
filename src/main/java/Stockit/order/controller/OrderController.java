package Stockit.order.controller;

import Stockit.order.dto.OrderRequest;
import Stockit.order.service.OrderService;
import Stockit.response.ApiResponse;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    //주문 생성
    @PostMapping(value = "/{memberIdx}/{stockCode}/new")
    public ApiResponse<Long> createOrder(@PathVariable Long memberIdx, @PathVariable Long stockCode, @RequestBody OrderRequest orderDto) throws NotFoundException {
        return ApiResponse.ok(orderService.createOrder(memberIdx, stockCode, orderDto));
    }
}
