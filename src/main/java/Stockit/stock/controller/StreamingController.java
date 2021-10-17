package Stockit.stock.controller;

import Stockit.stock.dto.DailyStockInfoDto;
import Stockit.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
@EnableScheduling
@RequestMapping("/api/streaming")
public class StreamingController {

    private static final Map<String, SseEmitter> CLIENTS = new ConcurrentHashMap<>();
    private final StockService stockService;

    @GetMapping("/stockList/subscribe")
    public SseEmitter subscribeStockLIst(@RequestParam String userId) {
        if (!CLIENTS.containsKey(userId)) CLIENTS.put(userId, new SseEmitter());
        SseEmitter emitter = CLIENTS.get(userId);
        emitter.onTimeout(() -> CLIENTS.remove(userId));
        emitter.onCompletion(() -> CLIENTS.remove(userId));
        return emitter;
    }

    @GetMapping("/stockList/publish")
    @Scheduled(fixedRate = 1000)
    public void publishStockList() {
        Set<String> deadIds = new HashSet<>();
        final List<DailyStockInfoDto> allStocksWithPercent = stockService.findAllStocksWithPercent();
        CLIENTS.forEach((userId, emitter) -> {
            try {
                emitter.send(allStocksWithPercent, MediaType.APPLICATION_JSON);
            } catch (Exception e) {
                deadIds.add(userId);
                log.warn("disconnected id : {}", userId);
            }
        });

        deadIds.forEach(CLIENTS::remove);
    }
}