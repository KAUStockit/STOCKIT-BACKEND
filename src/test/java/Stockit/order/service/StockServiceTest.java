package Stockit.order.service;

import Stockit.order.domain.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class StockServiceTest {

    @Autowired StockService stockService;
    private Stock stock;

    @BeforeEach
    public void 기본_설정() {
        stock = new Stock("종목1", 100, "종목1 설명", "카테고리1");
    }

    @Test
    public void 종목_생성() {
        //given

        //when
        final Long stockId = stockService.createNewStock(stock);

        //then

    }

}