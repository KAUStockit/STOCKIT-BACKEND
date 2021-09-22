package Stockit.order.service;

import Stockit.order.domain.Stock;
import Stockit.order.repository.StockRepository;
import Stockit.order.vo.StockUpdateVO;
import Stockit.order.vo.StockVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
class StockServiceTest {

    @Autowired StockService stockService;
    @Autowired StockRepository stockRepository;
    private Stock stock;
    private Stock stock2;

    @BeforeEach
    public void 기본_설정() {
        stockRepository.deleteAll();
        stock = new Stock("종목1", 100, "종목1 설명", "카테고리1");
        stock2 = new Stock("종목2", 150, "종목2 설명", "카테고리2");
        stockService.createNewStock(stock);
    }

    @Test
    public void 종목_생성() {
        //given

        //when
        final Long stockId = stockService.createNewStock(stock2);
        final Optional<Stock> optionalStock = stockService.findStock(stockId);

        //then
        Assertions.assertThat(optionalStock.isPresent()).isEqualTo(true);
    }

    @Test
    public void 모든_종목_조회() {
        //given

        //when
        final int firstSize = stockService.findAllStocks().size();
        stockService.createNewStock(stock2);
        final int secondSize = stockService.findAllStocks().size();

        //then
        Assertions.assertThat(firstSize).isEqualTo(1);
        Assertions.assertThat(secondSize).isEqualTo(2);
    }

    @Test
    public void 한_종목_조회() {
        //given

        //when
        final Optional<Stock> optionalStock = stockService.findStock(this.stock.getStockCode());

        //then
        Assertions.assertThat(optionalStock.isPresent()).isEqualTo(true);
        assertThat(new StockVO(optionalStock.orElseThrow(IllegalArgumentException::new)), is(samePropertyValuesAs(new StockVO(stock))));
    }

    @Test
    public void 주식_종목_하나_업데이트() {
        //given
        final StockUpdateVO stockUpdateVO = new StockUpdateVO(3000, true);

        //when
        stockService.updateStock(stockUpdateVO, stock.getStockCode());
        final Stock foundStock = stockService.findStock(stock.getStockCode()).orElseThrow(IllegalArgumentException::new);

        //then
        assertThat(foundStock.getPrice(), is(stockUpdateVO.getPrice()));
        assertThat(foundStock.isActive(), is(stockUpdateVO.isActive()));
    }

}