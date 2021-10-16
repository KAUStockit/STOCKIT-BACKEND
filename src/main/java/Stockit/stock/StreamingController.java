package Stockit.stock;

import Stockit.order.domain.Stock;
import Stockit.order.service.StockService;
import Stockit.order.vo.StockVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@EnableScheduling
public class StreamingController {
    private static final Map<String, SseEmitter> CLIENTS = new ConcurrentHashMap<>();
    private final StockService stockService;

    @GetMapping("/api/stockList/subscribe")
    public SseEmitter subscribeStockLIst(@RequestParam String userId) {
        SseEmitter emitter = new SseEmitter();
        CLIENTS.put(userId, emitter);

        emitter.onTimeout(() -> CLIENTS.remove(userId));
        emitter.onCompletion(() -> CLIENTS.remove(userId));
        return emitter;
    }

    @GetMapping("/api/stockList/publish")
    @Scheduled(fixedRate = 1000)
    public void publishStockList() {
        Set<String> deadIds = new HashSet<>();
        final List<Stock> stockList = stockService.findAllStocks();
        final List<StockVO> stockVOList = stockList.stream().map(StockVO::new).collect(Collectors.toList());
        CLIENTS.forEach((userId, emitter) -> {
            try {
                emitter.send(stockVOList, MediaType.APPLICATION_JSON);
            } catch (Exception e) {
                deadIds.add(userId);
                log.warn("disconnected id : {}", userId);
            }
        });

        deadIds.forEach(CLIENTS::remove);
    }

    @GetMapping("/api/stock/{stockId}/subscribe")
    public SseEmitter subscribeStock(@PathVariable String stockId, @RequestParam  String userId) {
        SseEmitter emitter = new SseEmitter();
        CLIENTS.put(userId, emitter);

        emitter.onTimeout(() -> CLIENTS.remove(userId));
        emitter.onCompletion(() -> CLIENTS.remove(userId));
        return emitter;
    }

    @GetMapping("/api/stock/{stockId}/publish")
    @Scheduled(fixedRate = 1000)
    public void publishStock(@PathVariable String stockId) {
        Set<String> deadIds = new HashSet<>();
        final Stock stock = stockService.findStock(Long.parseLong(stockId)).get();
        final StockVO stockVO = new StockVO(stock);
        CLIENTS.forEach((userId, emitter) -> {
            try {
                emitter.send(stockVO, MediaType.APPLICATION_JSON);
            } catch (Exception e) {
                deadIds.add(userId);
                log.warn("disconnected id : {}", userId);
            }
        });

        deadIds.forEach(CLIENTS::remove);
    }
}