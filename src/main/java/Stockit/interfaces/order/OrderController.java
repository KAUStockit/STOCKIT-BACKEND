package Stockit.interfaces.order;

import Stockit.common.response.CommonResponse;
import Stockit.domain.order.OrderService;
import Stockit.interfaces.order.dto.OrderRequest;
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
    public CommonResponse<Long> createOrder(@PathVariable Long memberIdx, @PathVariable Long stockCode, @RequestBody OrderRequest orderDto) throws NotFoundException {
        return CommonResponse.success(orderService.createOrder(memberIdx, stockCode, orderDto));
    }
}
